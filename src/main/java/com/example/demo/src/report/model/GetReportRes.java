package com.example.demo.src.report.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class GetReportRes {
    private int reportIdx;
    private int goodsIdx;
    private String reportTitle;
    private String reportContent;
}
