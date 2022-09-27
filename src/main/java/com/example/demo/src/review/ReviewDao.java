package com.example.demo.src.review;


import com.example.demo.src.payment.model.GetPaymentRes;
import com.example.demo.src.review.model.GetReviewRes;
import com.example.demo.src.review.model.PatchReviewReq;
import com.example.demo.src.review.model.PostReviewReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
public class ReviewDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public List<GetReviewRes> getReviews(int userIdx) {
        String getReviewQuery = "select * from Review where buyerIdx = ? and reviewStatus='active'";
        int getReviewparams = userIdx;
        return this.jdbcTemplate.query(getReviewQuery,
                (rs, rowNum) -> new GetReviewRes(
                        rs.getInt("sellerIdx"),
                        rs.getInt("buyerIdx"),
                        rs.getDouble("score"),
                        rs.getString("reviewContent")), getReviewparams
        );

    }

    public int createReview(int buyerIdx, PostReviewReq postReviewReq) {
        String createReviewQuery ="insert into Review (" +
                "buyerIdx" +
                ",sellerIdx" +
                ",score" +
                ",reviewContent," +
                "orderIdx)VALUES (?,?,?,?,?)";
        Object[] createReviewParams = new Object[]{buyerIdx,postReviewReq.getSellerIdx(),postReviewReq.getScore(),postReviewReq.getReviewContent(),postReviewReq.getOrderIdx()};
        this.jdbcTemplate.update(createReviewQuery, createReviewParams);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    public int checkUserExist(int userIdx) {
        String checkUserExistQuery = "select exists(select userIdx from User where userIdx = ?)";
        int checkUserExistParams = userIdx;
        return this.jdbcTemplate.queryForObject(checkUserExistQuery,
                int.class,
                checkUserExistParams);

    }



    public int deleteReview(int reviewIdx) {
        String deleteReviewQuery = "UPDATE Review\n" +
                "        SET reviewStatus = 'deleted'\n" +
                "        WHERE reviewIdx = ? ";
        Object[] deleteReviewParams = new Object[]{reviewIdx};
        return this.jdbcTemplate.update(deleteReviewQuery,deleteReviewParams);


    }

    public int checkUserOrderExist(int userIdx, int orderIdx) {
        String checkUserExistQuery = "select exists(select userIdx from User where userIdx = ?)";
        int checkUserExistParams = userIdx;
        return this.jdbcTemplate.queryForObject(checkUserExistQuery,
                int.class,
                checkUserExistParams);
    }


    public int updateReview(int reviewIdx, PatchReviewReq patchReviewReq) {
        String updateGoodsQuery = "UPDATE Review\n" +
                "        SET reviewContent = ?\n" +
                "        , score = ?\n" +
                "        WHERE reviewIdx = ?" ;
        Object[] updateGoodsParams = new Object[]{patchReviewReq.getReviewContent(),patchReviewReq.getScore(), reviewIdx};
        return this.jdbcTemplate.update(updateGoodsQuery,updateGoodsParams);
    }

    public int checkBuyerExist(int userIdx){
        String checkUserExistQuery = "select exists(select orderIdx from Orders where buyerIdx = ?)";
        int checkUserExistParams = userIdx;
        return this.jdbcTemplate.queryForObject(checkUserExistQuery,
                int.class,
                checkUserExistParams);
    }

    public int checkReviewExist(int reviewIdx) {
        String checkUserExistQuery = "select exists(select reviewIdx from Review where reviewIdx = ?)";
        int checkUserExistParams = reviewIdx;
        return this.jdbcTemplate.queryForObject(checkUserExistQuery,
                int.class,
                checkUserExistParams);
    }
}

