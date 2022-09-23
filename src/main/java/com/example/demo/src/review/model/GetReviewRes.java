package com.example.demo.src.review.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetReviewRes {

    private int sellerIdx;
    private int buyerIdx;
    private double score;
    private String reviewContent;

}
