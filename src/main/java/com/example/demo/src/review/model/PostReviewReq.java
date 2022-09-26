package com.example.demo.src.review.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostReviewReq {

    private int buyerIdx;
    private int sellerIdx;
    private Double score;
    private String reviewContent;
    private int orderIdx;
}
