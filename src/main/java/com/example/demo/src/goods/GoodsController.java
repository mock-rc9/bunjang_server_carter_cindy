package com.example.demo.src.goods;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.goods.model.GetGoodsRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.awt.image.OffScreenImageSource;

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
}
