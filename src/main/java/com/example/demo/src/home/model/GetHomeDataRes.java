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
    private String goodsCreatedAt;
    private String IsSecurePayment;
    private String address;
    private List<GetHomeImgRes> Gimgs;
}
