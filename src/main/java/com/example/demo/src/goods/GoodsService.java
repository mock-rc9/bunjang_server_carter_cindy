package com.example.demo.src.goods;


import com.example.demo.config.BaseException;
import com.example.demo.src.goods.model.PostGoodsImgReq;
import com.example.demo.src.goods.model.PostGoodsReq;
import com.example.demo.src.goods.model.PostGoodsRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class GoodsService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());


    private final GoodsProvider goodsProvider;
    private final GoodsDao goodsDao;

    @Autowired
    public GoodsService(GoodsProvider goodsProvider,GoodsDao goodsDao){
        this.goodsDao=goodsDao;
        this.goodsProvider=goodsProvider;
    }


    public PostGoodsRes createGoods(int userIdx,PostGoodsReq postGoodsReq) throws BaseException {
        try{

            int goodsIdx = goodsDao.createGoods(userIdx,postGoodsReq);
            for(int i=0;i<postGoodsReq.getImgs().size();i++){
                goodsDao.createGoodsImg(goodsIdx,postGoodsReq.getImgs().get(i));
            }
            return new PostGoodsRes(goodsIdx);
        }catch (Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }

    }
}
