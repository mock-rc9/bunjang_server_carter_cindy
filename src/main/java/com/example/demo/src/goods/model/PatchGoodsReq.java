package com.example.demo.src.goods.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatchGoodsReq {
    public PatchGoodsReq(){};
    private String goodsContent;
}
