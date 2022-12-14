package com.example.demo.src.goods;


import com.example.demo.config.BaseException;
import com.example.demo.src.goods.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class GoodsProvider {

    private final GoodsDao goodsDao;

    private JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public GoodsProvider(GoodsDao goodsDao,JwtService jwtService){ this.goodsDao = goodsDao;
    this.jwtService=jwtService;}

    /*유저 확인*/
    public int checkUserExits(int userIdx) throws BaseException{
        try {
            return goodsDao.checkUserExist(userIdx);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /*상품 확인*/
    public int checkGoodsExits(int goodsIdx) throws BaseException{
        try{
            return goodsDao.checkGoodsExist(goodsIdx);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    /*상품,유저 확인*/
    public int checkUserGoodsExist(int userIdx,int goodsIdx) throws BaseException{
        try {
            return goodsDao.checkUserGoodsExist(userIdx,goodsIdx);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetGoodsRes getGoods(int goodsIdx) throws BaseException{

        if(checkGoodsExits(goodsIdx)==0){
            throw new BaseException(GOODS_EMPTY_GOODS_ID);
        }

        try {


            GetGoodsDataRes getGoodsDataRes = goodsDao.getGoods(goodsIdx);
            GetStoreDataRes getStoreDataRes =goodsDao.getStoreData(getGoodsDataRes.getUserIdx());
            /*판매자의 다른 상품*/
            List<GetStoreGoodsRes> getStoreGoodsRes =goodsDao.getStoreGoods(getGoodsDataRes.getUserIdx());
            /*판매자의 리뷰*/
            List<GetStoreReviewRes> getStoreReviewRes = goodsDao.getStoreReviews(getGoodsDataRes.getUserIdx());
            GetGoodsRes getGoodsRes = new GetGoodsRes(getGoodsDataRes,getStoreGoodsRes,getStoreReviewRes,getStoreDataRes);
            return getGoodsRes;
        }catch (Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetCategoryRes> getCategorys() throws BaseException {

        try {
        List<GetCategoryRes> getCategoryRes = goodsDao.getCategorys();
        return getCategoryRes;
        }
    catch (Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }

    }

    public List<GetCategoryOptionRes> getCategory(int categoryIdx) throws BaseException {

        if(goodsDao.checkCategoryExits(categoryIdx)==0){
            throw new BaseException(GET_EMPTY_CATEGORY);
        }

        try {
            List<GetCategoryOptionRes> getCategoryOptionRes = goodsDao.getCategory(categoryIdx);
            return getCategoryOptionRes;
        }
        catch (Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }

    }

    public List<GetGoodsSearchRes> getSearchGoods(String searchGoods) throws BaseException {


        try {
            List<GetGoodsSearchRes> getGoodsSearchRes = goodsDao.getSearchGoods(searchGoods);
            return getGoodsSearchRes;
        }
        catch (Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public int checkCategoryExits(int categoryIdx) throws BaseException {
        try{
            return goodsDao.checkCategoryExits(categoryIdx);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }

    }
}
