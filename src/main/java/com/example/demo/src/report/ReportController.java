package com.example.demo.src.report;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.report.model.GetReportRes;
import com.example.demo.src.report.model.PostReportReq;
import com.example.demo.src.report.model.PostReportRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isDigit;

@RestController
@RequestMapping("/app/reports")
public class ReportController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final ReportProvider reportProvider;

    @Autowired
    private final ReportService reportService;

    @Autowired
    private final JwtService jwtService;

    public ReportController(ReportProvider reportProvider, ReportService reportService, JwtService jwtService){
        this.reportProvider = reportProvider;
        this.reportService = reportService;
        this.jwtService = jwtService;
    }

    /**
     * 신고하기 API
     * [POST] /app/reports/:goodsIdx
     */
    @ResponseBody
    @PostMapping("{goodsIdx}")
    public BaseResponse<PostReportRes> createReport(@PathVariable("goodsIdx") int goodsIdx, @RequestBody PostReportReq postReportReq){
        // 유효성 검사
        if(postReportReq.getReportTitle() == null){
            return new BaseResponse<>(POST_REPORT_EMPTY_TITLE);
        }

        try {
            int userIdx = jwtService.getUserIdx();

            PostReportRes postReportRes = reportService.createReport(userIdx, goodsIdx, postReportReq);
            return new BaseResponse<>(postReportRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 내 상점 재재 내역 조회 API
     * [GET] /app/reports
     * @return
     */
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetReportRes>> getReports(){
        try {
            int userIdx = jwtService.getUserIdx();

            List<GetReportRes> getReportRes = reportProvider.getReports(userIdx);
            return new BaseResponse<>(getReportRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


}
