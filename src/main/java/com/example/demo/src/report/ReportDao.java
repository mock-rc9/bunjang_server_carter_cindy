package com.example.demo.src.report;

import com.example.demo.src.report.model.GetReportRes;
import com.example.demo.src.report.model.PostReportReq;
import com.example.demo.src.user.model.PostUserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ReportDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public int createReport(int userIdx, int goodsIdx, PostReportReq postReportReq){
        String createReportQuery = "insert into Report (userIdx, goodsIdx, reportTitle, reportContent) VALUES (?,?,?,?)";
        Object[] createReportParams = new Object[]{userIdx, goodsIdx, postReportReq.getReportTitle(), postReportReq.getReportContent()};
        this.jdbcTemplate.update(createReportQuery, createReportParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    public List<GetReportRes> getReports(int userIdx){
        String getReportsQuery = "select reportIdx, G.goodsIdx as goodsIdx, reportTitle, reportContent\n" +
                "from Report\n" +
                "inner join Goods G on Report.goodsIdx = G.goodsIdx\n" +
                "where G.userIdx = ? and reportStatus = 'active'";
        int getReportParams = userIdx;

        return this.jdbcTemplate.query(getReportsQuery,
                (rs, rsNum) -> new GetReportRes(
                        rs.getInt("reportIdx"),
                        rs.getInt("goodsIdx"),
                        rs.getString("reportTitle"),
                        rs.getString("reportContent")),
                getReportParams);
    }
}
