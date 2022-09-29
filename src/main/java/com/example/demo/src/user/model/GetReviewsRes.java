package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class GetReviewsRes {
    private int reviewIdx;
    private String reviewScore;
    private String reviewContent;
    private String isSecurePayment;
    private String reviewer;
    private String lastUploadTime;
    private int goodsIdx;
    private String goodsName;
}
