package com.example.demo.src.notice;

import com.example.demo.src.notice.model.GetNoticeRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
public class NoticeDao {

    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public GetNoticeRes getNotice(int noticeIdx) {
        String checkNoticeQuery = "select * from Notice where noticeStatus='active' and noticeIdx=? ";
        int getNoticeParams= noticeIdx;
        return this.jdbcTemplate.queryForObject(checkNoticeQuery,
                (rs,rowNum)->new GetNoticeRes(
                        rs.getString("noticeTitle"),
                        rs.getString("noticeContent"),
                        rs.getString("noticeUpdatedAt")),getNoticeParams);
    }

    public List<GetNoticeRes> getNotices() {
        String checkNoticesQuery = "select * from Notice where noticeStatus='active'";
        return this.jdbcTemplate.query(checkNoticesQuery,
                (rs,rowNum)->new GetNoticeRes(
                        rs.getString("noticeTitle"),
                        rs.getString("noticeContent"),
                        rs.getString("noticeUpdatedAt")));
    }

    public int checkNoticeExits(int noticeIdx) {
        String checkNoticeExistQuery = "select exists(select noticeIdx from Notice where noticeIdx = ?)";
        int checkNoticeExistParams = noticeIdx;
        return this.jdbcTemplate.queryForObject(checkNoticeExistQuery,
                int.class,
                checkNoticeExistParams);
    }
}
