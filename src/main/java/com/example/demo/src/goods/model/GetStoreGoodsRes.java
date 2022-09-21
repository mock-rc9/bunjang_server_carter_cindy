package com.example.demo.src.goods.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetStoreGoodsRes {

    private int goodsIdx;
    private int userIdx;
    private String goodsName;
    private int goodsPrice;
    private List<GetGoodsImgRes> getGoodsImgRes;
}
