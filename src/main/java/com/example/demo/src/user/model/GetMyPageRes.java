package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class GetMyPageRes {
    private int userIdx;
    private String userImgUrl;
    private String userNickName;
    private String userContent;
    private double scoreAvg;
    private int tradeCount;
    private int follower;
    private int following;
    private int securePaymentCount;
    private String openDay;
    private String userStatusCheck;
    private List<GetGoodsRes> goods;
}
