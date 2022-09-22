package com.example.demo.src.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostOrderReq {
    private int goodsIdx;
    private int addressIdx;
    private String orderDeliveryReq;
    private int goodsPrice;
    private int orderFee;
    private int deliveryFee;
    private int orderTotalPrice;
    private String orderPaymentMethod;
    private String orderPaymentBank;
}
