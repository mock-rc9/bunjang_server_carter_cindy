package com.example.demo.src.home.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor


public class GetHomeDataRes {
    private int goodsIdx;
    private String goodsName;
    private int goodsPrice;
    private String goodsUpdatedAt;
    private String IsSecurePayment;
    private String goodsUpdatedAtTime;
    private String address;
    private List<GetHomeImgRes> Gimgs;
}
