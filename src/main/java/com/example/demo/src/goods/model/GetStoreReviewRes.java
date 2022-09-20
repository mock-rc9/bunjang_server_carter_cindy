package com.example.demo.src.goods.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetStoreReviewRes {

    private int reviewIdx;
    private String reviewContent;
    private Double score;
    private String reviewCreatedAt;
    private String reviewUpdatedAtTime;


}
