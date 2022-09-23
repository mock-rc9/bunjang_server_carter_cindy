package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class GetGoodsRes {
    private int goodsIdx;
    private String goodsStatus;
    private String goodsImgUrl;
    private String IsSecurePayment;
    private int goodsPrice;
    private String goodsName;
    private String goodsAddress;
    private String lastUploadTime;
    private int countLike;
}
