package com.example.demo.src.storelike;

import com.example.demo.config.BaseException;
import com.example.demo.src.review.model.PostReviewRes;
import com.example.demo.src.storelike.model.PatchStoreLikeReq;
import com.example.demo.src.storelike.model.PostStoreLikeReq;
import com.example.demo.src.storelike.model.PostStoreLikeRes;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;


@Service
public class StoreLikeService {

    private final StoreLikeProvider storeLikeProvider;

    private final StoreLikeDao storeLikeDao;

    public StoreLikeService(StoreLikeProvider storeLikeProvider, StoreLikeDao storeLikeDao) {
        this.storeLikeProvider = storeLikeProvider;
        this.storeLikeDao = storeLikeDao;
    }

    public PostStoreLikeRes createStoreLike(int userIdx, PostStoreLikeReq postStoreLikeReq) throws BaseException {
        try {
            int goodsLikeIdx = storeLikeDao.createStoreLike(userIdx,postStoreLikeReq);
            return new PostStoreLikeRes(goodsLikeIdx);
        }catch (Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }

    }

    public void deleteStoreLike(int userIdxJwt, PatchStoreLikeReq patchStoreLikeReq) throws BaseException {



        try {
            int result = storeLikeDao.deleteStoreLike(patchStoreLikeReq.getGoodsLikeIdx());
            if(result==0){
                throw new BaseException(DELETE_FAIL_STORELIKE);
            }
        }
        catch (Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
