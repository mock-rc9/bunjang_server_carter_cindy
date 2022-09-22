package com.example.demo.src.goods;


import com.example.demo.config.BaseException;
import com.example.demo.src.goods.model.PatchGoodsReq;
import com.example.demo.src.goods.model.PostGoodsImgReq;
import com.example.demo.src.goods.model.PostGoodsReq;
import com.example.demo.src.goods.model.PostGoodsRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

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

    /*상품 설명 수정*/
    public void modifyGoods(int userIdx, int goodsIdx, PatchGoodsReq patchGoodsReq) throws BaseException {

        if(goodsProvider.checkUserExits(userIdx)==0){
            throw new BaseException(USERS_EMPTY_USER_ID);
        }
        if(goodsProvider.checkGoodsExits(goodsIdx)==0){
            throw new BaseException(GOODS_EMPTY_GOODS_ID);
        }
        if(goodsProvider.checkUserGoodsExist(userIdx, goodsIdx)==0){
            throw new BaseException(GOODS_EMPTY_USER_GOODS);
        }
        try {
            int result = goodsDao.updateGoods(goodsIdx,patchGoodsReq);
            if(result==0){
                throw new BaseException(MODIFY_FAIL_GOODS);
            }
        }catch (Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
