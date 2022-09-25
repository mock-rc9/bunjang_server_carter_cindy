package com.example.demo.src.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class GetMyTradesRes {
    private int orderIdx;
    private String orderStatus;
    private String goodsName;
    private String goodsPrice;
    private String userNickName;
    private String orderTime;
    private int isReview;
}
