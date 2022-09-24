package com.example.demo.src.review;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.payment.model.PatchPaymentReq;
import com.example.demo.src.review.model.GetReviewRes;
import com.example.demo.src.review.model.PatchReviewReq;
import com.example.demo.src.review.model.PostReviewReq;
import com.example.demo.src.review.model.PostReviewRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final ReviewProvider reviewProvider;

    @Autowired
    private final ReviewService reviewService;

    @Autowired
    private final JwtService jwtService;

    public ReviewController(ReviewProvider reviewProvider,ReviewService reviewService,JwtService jwtService){
        this.reviewProvider = reviewProvider;
        this.reviewService = reviewService;
        this.jwtService = jwtService;
    }

    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetReviewRes>> getReveiws(){

        try {
            int userIdxJwt = jwtService.getUserIdx();
            List<GetReviewRes> getReviewRes = reviewProvider.getReviews(userIdxJwt);
            return new BaseResponse<>(getReviewRes);
        }catch (BaseException exception){
            System.out.println(exception);
            return new BaseResponse<>(exception.getStatus());
        }
    }

//    @ResponseBody
//    @PostMapping("")
//    public BaseResponse<PostReviewRes> createReview(@RequestBody PostReviewReq postReviewReq){
//        try {
//            int userIdxJwt = jwtService.getUserIdx();
//            PostReviewRes postReviewRes = reviewService.createReview(userIdxJwt, postReviewReq);
//            return new BaseResponse<>(postReviewRes);
//
//        } catch (BaseException exception) {
//            System.out.println(exception);
//            return new BaseResponse<>(exception.getStatus());
//        }
//    }


    @ResponseBody
    @PatchMapping("/{reviewIdx}")
    public BaseResponse<String> modifyReview(@PathVariable("reviewIdx") int reviewIdx, @RequestBody PatchReviewReq patchReviewReq) {

        try {
            int userIdxJwt = jwtService.getUserIdx();
            reviewService.modifyReview(userIdxJwt, reviewIdx, patchReviewReq);
            String result = "수정을 완료하였습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            System.out.println(exception);
            return new BaseResponse<>(exception.getStatus());
        }
    }
    @ResponseBody
    @PatchMapping("/{reviewIdx}/status")
    public BaseResponse<String> deleteReview(@PathVariable("reviewIdx") int reviewIdx) {

        try {
            int userIdxJwt = jwtService.getUserIdx();
            reviewService.deleteReview(userIdxJwt, reviewIdx);
            String result = "삭제를 완료하였습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            System.out.println(exception);
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
