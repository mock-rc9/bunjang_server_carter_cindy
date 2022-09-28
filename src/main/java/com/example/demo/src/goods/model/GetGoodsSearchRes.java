package com.example.demo.src.goods.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public class GetGoodsSearchRes {
    private int goodsIdx;
    private String goodsName;
    private String goodsAddress;
    private String IsSecurePayment;
    private int goodsPrice;
    private String goodsUpdatedAt;
    private int chat;
    private List<GetGoodsLikeRes> likes;
}
