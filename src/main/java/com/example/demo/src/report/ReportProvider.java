package com.example.demo.src.report;

import com.example.demo.config.BaseException;
import com.example.demo.src.report.model.GetReportRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@Transactional(readOnly = true)
public class ReportProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ReportDao reportDao;

    @Autowired
    public ReportProvider(ReportDao reportDao){
        this.reportDao = reportDao;
    }

    public List<GetReportRes> getReports(int userIdx) throws BaseException {
        try {
            List<GetReportRes> getReports = reportDao.getReports(userIdx);
            return getReports;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
