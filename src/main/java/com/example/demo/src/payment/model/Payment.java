package com.example.demo.src.payment.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Payment {
    private int paymentIdx;
    private String accountHolder;
    private String paymentBank;
    private int accountNum;
}
