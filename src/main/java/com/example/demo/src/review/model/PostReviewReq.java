package com.example.demo.src.review.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostReviewReq {
    private int sellerIdx;
    private int buyerIdx;
    private Double score;
    private String reviewContent;
    private int orderIdx;
}
