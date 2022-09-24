package com.example.demo.src.follow.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class GetGoodsListRes {
    private int goodsIdx;
    private int goodsPrice;
    private String goodsImgUrl;
}
