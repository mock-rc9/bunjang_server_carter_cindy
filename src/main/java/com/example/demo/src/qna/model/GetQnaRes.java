package com.example.demo.src.qna.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class GetQnaRes {
    private int qnaIdx;
    private String qnaCategory;
    private String qnaUpdatedAt;
    private String qnaContent;
    private List<GetQnaImgRes> qnaImgs;
}
