package com.example.demo.src.faq;

import com.example.demo.src.faq.model.GetFaqRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;


@Service
public class FaqDao {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetFaqRes> getFaqsByTitle(String searchTitle) {
        String checkFaqsQuery = "select * from Faq where faqTItle LIKE concat('%',?,'%') and faqStatus='active'";
        String faqTitleParams = searchTitle;
        return this.jdbcTemplate.query(checkFaqsQuery,
                (rs,rowNum)->new GetFaqRes(
                        rs.getString("faqTItle"),
                        rs.getString("faqContent")),faqTitleParams);
    }

    public List<GetFaqRes> getFaqs() {
        String checkFaqsQuery = "select * from Faq where faqStatus='active'";
        return this.jdbcTemplate.query(checkFaqsQuery,
                (rs,rowNum)->new GetFaqRes(
                        rs.getString("faqTItle"),
                        rs.getString("faqContent")));
    }

    public GetFaqRes getNotice(int faqIdx) {

        String checkFaqQuery = "select * from Faq where faqIdx=? and faqStatus='active'";
        int getFaqParams= faqIdx;
        return this.jdbcTemplate.queryForObject(checkFaqQuery,
                (rs,rowNum)->new GetFaqRes(
                        rs.getString("faqTItle"),
                        rs.getString("faqContent")),getFaqParams);

    }
}
