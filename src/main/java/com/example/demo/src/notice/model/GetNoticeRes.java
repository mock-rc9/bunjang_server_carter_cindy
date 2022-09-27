package com.example.demo.src.notice.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetNoticeRes {
    private String noticeTitle;
    private String noticeContent;
    private String noticeUpdatedAt;

}
