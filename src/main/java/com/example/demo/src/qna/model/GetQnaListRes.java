package com.example.demo.src.qna.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
@AllArgsConstructor
public class GetQnaListRes {
    private int qnaIdx;
    private String qnaCategory;
    private String qnaStatus;
}
