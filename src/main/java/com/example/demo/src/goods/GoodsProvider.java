package com.example.demo.src.goods;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.goods.model.GetGoodsDataRes;
import com.example.demo.src.goods.model.GetGoodsRes;
import com.example.demo.src.goods.model.GetStoreGoodsRes;
import com.example.demo.src.goods.model.GetStoreReviewRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class GoodsProvider {

    private final GoodsDao goodsDao;

    private JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public GoodsProvider(GoodsDao goodsDao){ this.goodsDao = goodsDao;}

    public GetGoodsRes getGoods(int goodsIdx) throws BaseException{
        try {

            GetGoodsDataRes getGoodsDataRes = goodsDao.getGoods(goodsIdx);
            /*판매자의 다른 상품*/
            List<GetStoreGoodsRes> getStoreGoodsRes =goodsDao.getStoreGoods(getGoodsDataRes.getUserIdx());
            /*판매자의 리뷰*/
            List<GetStoreReviewRes> getStoreReviewRes = goodsDao.getStoreReviews(getGoodsDataRes.getUserIdx());
            GetGoodsRes getGoodsRes = new GetGoodsRes(getGoodsDataRes,getStoreGoodsRes,getStoreReviewRes);
            return getGoodsRes;
        }catch (Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}