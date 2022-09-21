package com.example.demo.src.goods;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.goods.model.GetGoodsRes;
import com.example.demo.src.goods.model.PostGoodsReq;
import com.example.demo.src.goods.model.PostGoodsRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.awt.image.OffScreenImageSource;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/goods")
public class GoodsController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private final GoodsProvider goodsProvider;

    @Autowired
    private final GoodsService goodsService;
    @Autowired
    private final JwtService jwtService;



    public GoodsController(GoodsProvider goodsProvider, GoodsService goodsService,JwtService jwtService){
        this.goodsProvider = goodsProvider;
        this.goodsService = goodsService;
        this.jwtService = jwtService;
    }

    @ResponseBody
    @GetMapping("/{goodsIdx}")
    public BaseResponse<GetGoodsRes> getGoods(@PathVariable("goodsIdx") int goodsIdx){
        try{
            GetGoodsRes getGoodsRes = goodsProvider.getGoods(goodsIdx);
            return new BaseResponse<>(getGoodsRes);
        }
        catch (BaseException exception){
            System.out.println(exception);
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostGoodsRes> createGoods(@RequestBody PostGoodsReq postGoodsReq){
        try {
            /*제품 설명 유효성 검사*/
            if(postGoodsReq.getGoodsContent().length()<10){
                return new BaseResponse<>(POST_GOODS_LACK_CONTENT);
            }

            /*이미지 파일 유효성 검사*/
            if(postGoodsReq.getImgs().size()<1){
                return new BaseResponse<>(POST_GOODS_EMPTY_IMG);
            }
            /*제품 제목 유효성 검사*/
            if(postGoodsReq.getGoodsName().length()<2){
                return new BaseResponse<>(POST_GOODS_LACK_NAME);
            }
            /*제품 카테고리 유효성 검사*/
            if(postGoodsReq.getCategoryOptionIdx()<0){
                return new BaseResponse<>(POST_GOODS_EMPTY_CATEGORY);
            }
            /*제품 가격 유효성 검사*/
            if(postGoodsReq.getGoodsPrice()<0){
                return new BaseResponse<>(POST_GOODS_EMPTY_PRICE);
            }

            int userIdxJwt = jwtService.getUserIdx();
            PostGoodsRes postGoodsRes = goodsService.createGoods(userIdxJwt,postGoodsReq);
            return new BaseResponse<>(postGoodsRes);
        }catch (BaseException exception){
            System.out.println(exception);
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
