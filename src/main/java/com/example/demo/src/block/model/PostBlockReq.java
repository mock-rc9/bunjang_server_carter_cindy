package com.example.demo.src.block.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class PostBlockReq {
    public PostBlockReq(){};
    private int blockedUserIdx;

}
