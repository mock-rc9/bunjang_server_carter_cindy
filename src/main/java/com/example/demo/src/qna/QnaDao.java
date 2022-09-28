package com.example.demo.src.qna;

import com.example.demo.src.qna.model.GetQnaImgRes;
import com.example.demo.src.qna.model.GetQnaListRes;
import com.example.demo.src.qna.model.GetQnaRes;
import com.example.demo.src.qna.model.PostQnaReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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

    public List<GetQnaListRes> getQnaList(int userIdx){
        String getQnaListQuery = "select qnaIdx, qnaCategory, qnaStatus\n" +
                "from Qna\n" +
                "where userIdx = ?";
        int getQnaListParams = userIdx;

        return this.jdbcTemplate.query(getQnaListQuery,
                (rs, rsNum) -> new GetQnaListRes(
                        rs.getInt("qnaIdx"),
                        rs.getString("qnaCategory"),
                        rs.getString("qnaStatus")),
                getQnaListParams);
    }

    public List<GetQnaImgRes> getQnaImgs(int qnaIdx){
        String getQnaImgsQuery = "select qnaImgIdx, qnaImgUrl\n" +
                "from QnaImg\n" +
                "where qnaIdx = ?";
        int getQnaImgsParams = qnaIdx;

        return this.jdbcTemplate.query(getQnaImgsQuery,
                (rs, rsNum) -> new GetQnaImgRes(
                        rs.getInt("qnaImgIdx"),
                        rs.getString("qnaImgUrl")),
                getQnaImgsParams);
    }

    public GetQnaRes getQna(int qnaIdx){
        String getQnaListQuery = "select qnaIdx, qnaCategory, REPLACE(qnaUpdatedAt, '-', '.') as qnaUpdatedAt, qnaContent\n" +
                "from Qna\n" +
                "where qnaIdx = ?";
        int getQnaParams = qnaIdx;

        return this.jdbcTemplate.queryForObject(getQnaListQuery,
                (rs, rsNum) -> new GetQnaRes(
                        rs.getInt("qnaIdx"),
                        rs.getString("qnaCategory"),
                        rs.getString("qnaUpdatedAt"),
                        rs.getString("qnaContent"),
                        getQnaImgs(rs.getInt("qnaIdx"))),
                getQnaParams);
    }



}
