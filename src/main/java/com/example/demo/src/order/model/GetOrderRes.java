package com.example.demo.src.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class GetOrderRes {
    private int goodsIdx;
    private String orderStatus;
    private int goodsPrice;
    private String goodsName;
    private String orderTime;
    private String sellerName;

    private String orderPaymentMethod;
    private String orderPaymentBank;
    private int orderTotalPrice;
    private int orderGoodsPrice;
    private int orderFee;
    private int deliveryFee;

    private int addressIdx;
    private String userName;
    private String userPhoneNum;
    private String address;
    private String orderDeliveryReq;
}
