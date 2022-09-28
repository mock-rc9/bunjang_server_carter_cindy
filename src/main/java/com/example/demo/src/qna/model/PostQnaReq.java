package com.example.demo.src.qna.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
@AllArgsConstructor
public class PostQnaReq {
    private String qnaCategory;
    private String qnaContent;
}
