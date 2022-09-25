package com.example.demo.src.review;


import com.example.demo.config.BaseException;
import com.example.demo.src.payment.model.GetPaymentRes;
import com.example.demo.src.review.model.GetReviewRes;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class ReviewProvider {

    private final ReviewDao reviewDao;

    private final JwtService jwtService;

    @Autowired
    public ReviewProvider(ReviewDao reviewDao,JwtService jwtService){
        this.jwtService = jwtService;
        this.reviewDao= reviewDao;
    }

    public List<GetReviewRes> getReviews(int userIdx) throws BaseException {

        try {
            List<GetReviewRes> getReviewRes = reviewDao.getReviews(userIdx);
            return getReviewRes;
        }
        catch (Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }

    }
    public int checkUserExits(int userIdx) throws BaseException {
        try {
            return reviewDao.checkUserExist(userIdx);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }

    }

    public int checkBuyerExits(int userIdx) throws BaseException {
        try{
            return reviewDao.checkBuyerExist(userIdx);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public int checkReviewExits(int reviewIdx) throws BaseException {
        try{
            return reviewDao.checkReviewExist(reviewIdx);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }

    }

    public int checkUserOrderExist(int userIdx, int orderIdx) throws BaseException {
        try {
            return reviewDao.checkUserOrderExist(userIdx,orderIdx);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
