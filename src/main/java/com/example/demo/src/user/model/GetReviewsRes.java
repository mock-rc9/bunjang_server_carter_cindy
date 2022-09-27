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
    private String reviewer;
    private String lastUploadTime;
}
