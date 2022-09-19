package com.example.demo.src.user;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/app/users")
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;

    public UserController(UserProvider userProvider, UserService userService){
        this.userProvider = userProvider;
        this.userService = userService;
    }

    /**
     * 회원가입 API
     * [POST] /users
     * @return BaseResponse<PostUserRes>
     */
    @ResponseBody
    @PostMapping("")
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
    @PostMapping("/logIn")
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

}
