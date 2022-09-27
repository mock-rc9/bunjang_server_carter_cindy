package com.example.demo.src.report;

import com.example.demo.config.BaseException;
import com.example.demo.src.report.model.PostReportReq;
import com.example.demo.src.report.model.PostReportRes;
import com.example.demo.src.user.model.PostUserReq;
import com.example.demo.src.user.model.PostUserRes;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
@Transactional
public class ReportService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ReportDao reportDao;

    @Autowired
    public ReportService(ReportDao reportDao){
        this.reportDao = reportDao;
    }

    public PostReportRes createReport(int userIdx, int goodsIdx, PostReportReq postReportReq) throws BaseException {
        try{
            int reportIdx = reportDao.createReport(userIdx, goodsIdx, postReportReq);
            return new PostReportRes(reportIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
