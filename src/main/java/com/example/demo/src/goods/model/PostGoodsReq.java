package com.example.demo.src.goods.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostGoodsReq {
    private int userIdx;
    private String goodsName;
    private String goodsContent;
    private int goodsPrice;
    private String IsSecurePayment;
    private String IsDeilveryFee;
    private int goodsCount;
    private int goodsCondition;
    private String IsExchange;
    private int categoryOptionIdx;
    private List<PostGoodsImgReq> imgs;
}

