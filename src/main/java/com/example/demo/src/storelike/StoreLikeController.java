package com.example.demo.src.storelike;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.review.model.PatchReviewReq;
import com.example.demo.src.review.model.PostReviewRes;
import com.example.demo.src.storelike.model.GetStoreLikeRes;
import com.example.demo.src.storelike.model.PatchStoreLikeReq;
import com.example.demo.src.storelike.model.PostStoreLikeReq;
import com.example.demo.src.storelike.model.PostStoreLikeRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/storelikes")
public class StoreLikeController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final StoreLikeProvider storeLikeProvider;
    @Autowired
    private final StoreLikeService storeLikeService;

    @Autowired
    private final JwtService jwtService;


    public StoreLikeController(StoreLikeProvider storeLikeProvider, StoreLikeService storeLikeService, JwtService jwtService) {
        this.storeLikeProvider = storeLikeProvider;
        this.storeLikeService = storeLikeService;
        this.jwtService = jwtService;
    }

    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetStoreLikeRes>> getStoreLikes(){

        try {
            int userIdxJwt = jwtService.getUserIdx();
            List<GetStoreLikeRes> getStoreLikeRes = storeLikeProvider.getStoreLikes(userIdxJwt);
            return new BaseResponse<>(getStoreLikeRes);
        }catch (BaseException exception){
            System.out.println(exception);
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostStoreLikeRes> createStoreLike(@RequestBody PostStoreLikeReq postStoreLikeReq){

        try {
            int userIdxJwt = jwtService.getUserIdx();
            PostStoreLikeRes postStoreLikeRes = storeLikeService.createStoreLike(userIdxJwt,postStoreLikeReq);
            return new BaseResponse<>(postStoreLikeRes);

        } catch (BaseException exception) {
            System.out.println(exception);
            return new BaseResponse<>(exception.getStatus());
        }
    }
    @ResponseBody
    @PatchMapping("")
    public BaseResponse<String> deleteStoreLike(@RequestBody PatchStoreLikeReq patchStoreLikeReq){

        try {
            int userIdxJwt = jwtService.getUserIdx();
            storeLikeService.deleteStoreLike(userIdxJwt,patchStoreLikeReq);
            String result = "삭제를 완료하였습니다.";
            return new BaseResponse<>(result);

        } catch (BaseException exception) {
            System.out.println(exception);
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
