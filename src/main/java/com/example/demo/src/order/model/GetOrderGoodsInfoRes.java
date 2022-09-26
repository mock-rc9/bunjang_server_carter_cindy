package com.example.demo.src.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class GetOrderGoodsInfoRes {
    private String goodsName;
    private String goodsImgUrl;
    private int goodsPrice;
}
