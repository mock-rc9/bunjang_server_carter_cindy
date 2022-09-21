package com.example.demo.src.address.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetAddressRes {
    private int addressIdx;
    private String IsBaseAddress;
    private String userName;
    private String address;
    private String addressDetail;
    private String userPhoneNum;
}
