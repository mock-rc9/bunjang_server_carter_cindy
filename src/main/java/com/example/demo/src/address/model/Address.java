package com.example.demo.src.address.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class Address {
    private int addressIdx;
    private String userName;
    private String userPhoneNum;
    private String address;
    private String addressDetail;
    private String isBaseAddress;
}
