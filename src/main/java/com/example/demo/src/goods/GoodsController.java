package com.example.demo.src.goods;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.goods.model.*;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;





@RequiredArgsConstructor
@RestController
@RequestMapping("/goods")
public class GoodsController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private final GoodsProvider goodsProvider;

    @Autowired
    private final GoodsService goodsService;
    @Autowired
    private final JwtService jwtService;




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
    public  BaseResponse<String> createGoods(@RequestPart(value = "postGoodsReq") PostGoodsReq postGoodsReq,
                                                   @RequestPart(value = "multipartFile", required = false) MultipartFile multipartFile) {

        /*제품 설명 유효성 검사*/
        if (postGoodsReq.getGoodsContent().length() < 10) {
            return new BaseResponse<>(POST_GOODS_LACK_CONTENT);
        }

        /*이미지 파일 유효성 검사*/
        if (multipartFile.isEmpty()) {
            return new BaseResponse<>(POST_GOODS_EMPTY_IMG);
        }
        /*제품 제목 유효성 검사*/
        if (postGoodsReq.getGoodsName().length() < 2) {
            return new BaseResponse<>(POST_GOODS_LACK_NAME);
        }
        /*제품 카테고리 유효성 검사*/
        if (postGoodsReq.getCategoryOptionIdx() < 0) {
            return new BaseResponse<>(POST_GOODS_EMPTY_CATEGORY);
        }
        /*제품 가격 유효성 검사*/
        if (postGoodsReq.getGoodsPrice() < 0) {
            return new BaseResponse<>(POST_GOODS_EMPTY_PRICE);
        }
        try {

            int userIdxJwt = jwtService.getUserIdx();
//            PostGoodsRes postGoodsRes = goodsService.createGoods(userIdxJwt,postGoodsReq,multipartFile);]
            String result = "업로드에 성공하였습니다";
            goodsService.createGoods(userIdxJwt, postGoodsReq, multipartFile);
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            System.out.println(exception);
            return new BaseResponse<>(exception.getStatus());
        }

    }


    /*제품 수정*/
    @ResponseBody
    @PatchMapping("/{goodsIdx}")
    public BaseResponse<String> modifyGoods(@PathVariable("goodsIdx")int goodsIdx, @RequestBody PatchGoodsReq patchGoodsReq){

        try{
            int userIdxJwt = jwtService.getUserIdx();
            goodsService.modifyGoods(userIdxJwt,goodsIdx,patchGoodsReq);
            String result = "수정을 완료하였습니다.";
            return new BaseResponse<>(result);
        }catch (BaseException exception){
            System.out.println(exception);
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /*상품 삭제*/
    @ResponseBody
    @PatchMapping("/{goodsIdx}/status")
    public BaseResponse<String> deleteGoods(@PathVariable("goodsIdx") int goodsIdx)  {


        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            goodsService.deleteGoods(userIdxByJwt,goodsIdx);
            String result="삭제 되었습니다.";
            return new BaseResponse<>(result);
        }
        catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


    @ResponseBody
    @GetMapping("/categorys")
    public BaseResponse<List<GetCategoryRes>> getCategory(){

        try{
            List<GetCategoryRes> getCategoryRes = goodsProvider.getCategorys();
            return new BaseResponse<>(getCategoryRes);
        }
        catch (BaseException exception){
            System.out.println(exception);
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("/categorys/{categoryIdx}")
    public BaseResponse<List<GetCategoryOptionRes>> getCategory(@PathVariable("categoryIdx") int categoryIdx){

        try{
            List<GetCategoryOptionRes> getCategoryOptionRes = goodsProvider.getCategory(categoryIdx);
            return new BaseResponse<>(getCategoryOptionRes);
        }
        catch (BaseException exception){
            System.out.println(exception);
            return new BaseResponse<>(exception.getStatus());
        }
    }
    @ResponseBody
    @GetMapping("/goods-search")
    public BaseResponse<List<GetGoodsSearchRes>> getSearchGoods(@RequestParam(required = false) String searchGoods){

        try{
            List<GetGoodsSearchRes> getGoodsSearchRes = goodsProvider.getSearchGoods(searchGoods);
            return new BaseResponse<>(getGoodsSearchRes);
        }
        catch (BaseException exception){
            System.out.println(exception);
            return new BaseResponse<>(exception.getStatus());
        }
    }



}


