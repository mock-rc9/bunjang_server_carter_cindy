package com.example.demo.src.follow.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class GetMyFeedRes {
    private int goodsIdx;
    private String isSecurePayment;
    private String goodsImgUrl;
    private String goodsPrice;
    private String goodsName;
    private String userImgUrl;
    private String userNickName;
}
