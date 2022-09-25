package com.example.demo.src.goods.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetGoodsRes {
    private GetGoodsDataRes getGoodsDataRes;
    private List<GetStoreGoodsRes> getStoreGoodsRes;
    private List<GetStoreReviewRes> getStoreReviewRes;
    private GetStoreDataRes getStoreDataRes;
}
