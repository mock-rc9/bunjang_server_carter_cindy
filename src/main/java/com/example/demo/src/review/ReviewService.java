package com.example.demo.src.review;

import com.example.demo.config.BaseException;
import com.example.demo.src.payment.model.PostPaymentRes;
import com.example.demo.src.review.model.PatchReviewReq;
import com.example.demo.src.review.model.PostReviewReq;
import com.example.demo.src.review.model.PostReviewRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;


@Service
public class ReviewService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ReviewProvider reviewProvider;
    private final ReviewDao reviewDao;

    @Autowired
    public ReviewService(ReviewProvider reviewProvider,ReviewDao reviewDao){
        this.reviewDao = reviewDao;
        this.reviewProvider= reviewProvider;
    }

    public void deleteReview(int userIdxJwt, int reviewIdx) {
    }

    public void modifyReview(int userIdx, int reviewIdx, PatchReviewReq patchReviewReq) throws BaseException {
        if(reviewProvider.checkUserExits(userIdx)==0){
            throw new BaseException(USERS_EMPTY_USER_ID);
        }
        if(reviewProvider.checkReviewExits(reviewIdx)==0){
            throw new BaseException(REVIEW_EMPTY_REVIEW_ID);
        }
        try {
            int result = reviewDao.updateReview(reviewIdx,patchReviewReq);
            if(result==0){
                throw new BaseException(MODIFY_FAIL_REVIEW);
            }
        }
        catch (Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }

    }
//    public PostReviewRes createReview(int userIdx, PostReviewReq postReviewReq) throws BaseException {
//
//
//        if(reviewProvider.checkUserExits(userIdx)==0){
//            throw new BaseException(USERS_EMPTY_USER_ID);
//        }
//        if(reviewProvider.checkSellerExits(userIdx)==0){
//            throw new BaseException(REVIEW_EMPTY_SELLER_ID);
//        }
//
//        try {
//            int reviewIdx = reviewDao.createReview(userIdx,postReviewReq);
//            return new PostReviewRes (reviewIdx);
//        }catch (Exception exception){
//            System.out.println(exception);
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }
}
