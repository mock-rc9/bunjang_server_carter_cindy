package com.example.demo.src.review.model;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatchReviewReq {


    private String reviewContent;
    private Double score;
}
