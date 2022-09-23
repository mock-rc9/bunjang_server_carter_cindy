package com.example.demo.src.review;


import com.example.demo.src.payment.model.GetPaymentRes;
import com.example.demo.src.review.model.GetReviewRes;
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
        String getReviewQuery = "select * from Review where =?";
        int getReviewparams = userIdx;
        return this.jdbcTemplate.query(getReviewQuery,
                (rs, rowNum) -> new GetReviewRes(
                        rs.getInt("sellerIdx"),
                        rs.getInt("buyerIdx"),
                        rs.getDouble("score"),
                        rs.getString("reviewContent")), getReviewparams
        );

    }

//    public int createReview(int userIdx, PostReviewReq postReviewReq) {
//
//    }

    public int checkUserExist(int userIdx) {
        String checkUserExistQuery = "select exists(select userIdx from User where userIdx = ?)";
        int checkUserExistParams = userIdx;
        return this.jdbcTemplate.queryForObject(checkUserExistQuery,
                int.class,
                checkUserExistParams);

    }

//    public int checkSellerExist(int paymentIdx) {
//    }
//
//    public int checkUserOrderExist(int userIdx, int paymentIdx) {
//    }
}

