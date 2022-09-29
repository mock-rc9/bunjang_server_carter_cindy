package com.example.demo.src.user;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.Message;
import com.example.demo.src.user.model.SmsRes;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;
import static com.example.demo.utils.ValidationRegex.isRegexUserNickName;

@RestController
@RequestMapping("/app")
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final SmsService smsService;

    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService, SmsService smsService){
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
        this.smsService = smsService;
    }

    /**
     * 회원가입 API
     * [POST] /users
     * @return BaseResponse<PostUserRes>
     */
    @ResponseBody
    @PostMapping("/users")
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) {
        // 이메일 유효성 검사
        if(postUserReq.getUserEmail() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }
        if(!isRegexEmail(postUserReq.getUserEmail())){ // 이메일 정규 표현
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }

        // 비밀번호 유효성 검사
        if(postUserReq.getUserPassword() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_PASSWORD);
        }

        // 이름 유효성 검사
        if(postUserReq.getUserNickName() == null || postUserReq.getUserNickName().length() < 2){
            return new BaseResponse<>(POST_USERS_EMPTY_NAME);
        }

        try{
            PostUserRes postUserRes = userService.createUser(postUserReq);
            return new BaseResponse<>(postUserRes);
        } catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 로그인 API
     * [POST] /users/logIn
     * @return BaseResponse<PostLoginRes>
     */
    @ResponseBody
    @PostMapping("/users/logIn")
    public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq){
        try{
            // 이메일 유효성 검사
            if(postLoginReq.getUserEmail() == null){ // null 값인 경우
                return new BaseResponse<>(POST_LOGIN_EMPTY_EMAIL);
            }
            if(!isRegexEmail(postLoginReq.getUserEmail())){ // 이메일 정규 표현
                return new BaseResponse<>(POST_LOGIN_INVALID_EMAIL);
            }

            // 비밀번호 유효성 검사
            if(postLoginReq.getUserPassword() == null){ // null 값인 경우
                return new BaseResponse<>(POST_LOGIN_EMPTY_PASSWORD);
            }

            PostLoginRes postLoginRes = userProvider.logIn(postLoginReq);
            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 마이페이지 API (+검색)
     * [GET] /app/mypages
     * @return BaseResponse<GetMyPageRes>
     */
    @ResponseBody
    @GetMapping("/mypages")
    public BaseResponse<GetMyPageRes> getMyPage(@RequestParam(required = false) String searchName){
        try {
            int userIdx = jwtService.getUserIdx();
            if (searchName == null){
                GetMyPageRes getMyPageRes = userProvider.getMyPage(userIdx);
                return new BaseResponse<>(getMyPageRes);
            }
            GetMyPageRes getMyPageRes = userProvider.getMyPageByName(userIdx, searchName);
            return new BaseResponse<>(getMyPageRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 마이페이지 상점 후기 조회 API
     * [GET] /app/mypages/reviews
     * @return BaseResponse<List<GetReviewsRes>>
     */
    @ResponseBody
    @GetMapping("/mypages/reviews")
    public BaseResponse<List<GetReviewsRes>> getReviews(){
        try {
            int userIdx = jwtService.getUserIdx();

            List<GetReviewsRes> getReviewsRes = userProvider.getReviews(userIdx);
            return new BaseResponse<>(getReviewsRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 상점 소개 편집 API
     * [PATCH] /app/mypages
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/mypages")
    public BaseResponse<String> modifyUserInfo(@RequestPart(value = "multipartFile", required = false) MultipartFile multipartFile, @RequestPart(value = "patchUserReq") PatchUserReq patchUserReq) throws IOException {
        try {
            int userIdx = jwtService.getUserIdx();

            // 상점명 유효성 검사
            if(patchUserReq.getUserNickName() == null){
                return new BaseResponse<>(PATCH_USERINFO_EMPTY_USERNICKNAME);
            }
            if(patchUserReq.getUserNickName().length() < 2 || patchUserReq.getUserNickName().length() > 10){
                return new BaseResponse<>(PATCH_USERINFO_EMPTY_USERNICKNAME);
            }
            if(!isRegexUserNickName(patchUserReq.getUserNickName())){
                return new BaseResponse<>(PATCH_USERINFO_INVALID_USERNICKNAME);
            }

            userService.modifyUserInfo(userIdx, patchUserReq);
            if(multipartFile != null && !multipartFile.isEmpty()){
                userService.modifyUploadFile(userIdx, multipartFile);
            }

            String result = "상점 소개가 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


    /**
     * 탈퇴하기 API
     * [PATCH] /app/users
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/users")
    public BaseResponse<String> deleteUser(){
        try {
            int userIdx = jwtService.getUserIdx();
            userService.deleteUser(userIdx);
            String result = "탈퇴가 완료되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 판매자 프로필 상세 페이지 API
     * [GET] /app/sellerpages/:sellerIdx
     * @return BaseResponse<GetSellerPageRes>
     */
    @ResponseBody
    @GetMapping("/sellerpages/{sellerIdx}")
    public BaseResponse<GetSellerPageRes> getSellerPage(@PathVariable("sellerIdx") int sellerIdx){
        try {
            int userIdx = jwtService.getUserIdx();

            GetSellerPageRes getSellerPageRes = userProvider.getSellerPage(userIdx, sellerIdx);
            return new BaseResponse<>(getSellerPageRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * SMS API
     * [POST] /app/sms/sends
     * @return BaseResponse<SmsRes>
     */
    @ResponseBody
    @PostMapping("/sms/sends")
    public BaseResponse<SmsRes> sendSms(@RequestBody Message message) throws JsonProcessingException, RestClientException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
        try {
            int userIdx = jwtService.getUserIdx();

            if(message.getContent() == null){
                return new BaseResponse<>(POST_SMS_EMPTY_CONTENT);
            }

            if(message.getTo() == null){
                String userPhoneNum = userProvider.getPhoneNum(userIdx);
                if(userPhoneNum == null){
                    return new BaseResponse<>(EMPTY_USERPHONENUM);
                }
                message.setTo(userPhoneNum);
            }

            SmsRes response = smsService.sendSms(message);
            return new BaseResponse<>(response);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

}
