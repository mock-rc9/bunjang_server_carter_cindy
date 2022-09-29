package com.example.demo.src.storelike.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetStoreLikeRes {

    private int goodsLikeIdx;
    private int GoodsIdx;
    private String GoodsName;
    private int GoodsPrice;
    private String UserNickName;
    private String updateAt;
    private String IsSecurePayment;
    private String Img;
    private String userImgUrl;


}
