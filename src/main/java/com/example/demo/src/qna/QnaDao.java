package com.example.demo.src.qna;

import com.example.demo.src.qna.model.PostQnaReq;
import com.example.demo.src.user.model.PostUserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class QnaDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int createQna(int userIdx, PostQnaReq postQnaReq){
        String createQnaQuery = "insert into Qna (userIdx, qnaCategory, qnaContent) VALUES (?,?,?)";
        Object[] createQnaParams = new Object[]{userIdx, postQnaReq.getQnaCategory(), postQnaReq.getQnaContent()};
        this.jdbcTemplate.update(createQnaQuery, createQnaParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    public int createQnaImg(int qnaIdx, String qnaImgUrl){
        String createQnaImgQuery = "insert into QnaImg (qnaIdx, qnaImgUrl) VALUES (?,?)";
        Object[] createQnaImgParams = new Object[]{qnaIdx, qnaImgUrl};
        this.jdbcTemplate.update(createQnaImgQuery, createQnaImgParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

}
