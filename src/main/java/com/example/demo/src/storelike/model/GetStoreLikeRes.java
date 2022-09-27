package com.example.demo.src.storelike.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetStoreLikeRes {
    private int GoodsIdx;
    private String GoodsName;
    private int GoodsPrice;
    private String UserNickName;
    private String updateAt;
    private String IsSecurePayment;
    private String Img;


}
