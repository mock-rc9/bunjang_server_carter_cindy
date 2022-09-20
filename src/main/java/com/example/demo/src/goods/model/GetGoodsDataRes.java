package com.example.demo.src.goods.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetGoodsDataRes {

    private int goodsIdx;
    private int userIdx;
    private String goodsName;
    private String goodsContent;
    private int goodsPrice;
    private String goodsUpdatedAt;
    private String goodsUpdatedAtTime;
    private String IsSecurePayment;
    private String IsDeilveryFee;
    private int goodsCount;
    private String goodsCondition;
    private List<GetGoodsImgRes> Imgs;

}