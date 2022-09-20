package com.example.demo.src.goods;


import com.example.demo.src.goods.model.GetGoodsDataRes;
import com.example.demo.src.goods.model.GetGoodsImgRes;
import com.example.demo.src.goods.model.GetStoreGoodsRes;
import com.example.demo.src.goods.model.GetStoreReviewRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
public class GoodsDao {



    private List<GetGoodsImgRes> getGoodsImgRes;
    private List<GetStoreReviewRes> getStoreReviewRes;


    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    /*상세 제품 쿼리*/
    public GetGoodsDataRes getGoods(int goodsIdx) {
        String getGoodsQuery ="select *,\n" +
                "        case when TIMESTAMPDIFF(SECOND, G.goodsUpdatedAt,CURRENT_TIMESTAMP)<60\n" +
                "        then concat(TIMESTAMPDIFF(SECOND, G.goodsUpdatedAt,CURRENT_TIMESTAMP),'초 전')\n" +
                "        when TIMESTAMPDIFF(MINUTE , G.goodsUpdatedAt,CURRENT_TIMESTAMP)<60\n" +
                "        then concat(TIMESTAMPDIFF(MINUTE , G.goodsUpdatedAt,CURRENT_TIMESTAMP),'분 전')\n" +
                "        when TIMESTAMPDIFF(HOUR , G.goodsUpdatedAt,CURRENT_TIMESTAMP)<24\n" +
                "        then concat(TIMESTAMPDIFF(HOUR , G.goodsUpdatedAt,CURRENT_TIMESTAMP),'시간 전')\n" +
                "        when TIMESTAMPDIFF(DAY , G.goodsUpdatedAt,CURRENT_TIMESTAMP)<30\n" +
                "        then concat(TIMESTAMPDIFF(DAY , G.goodsUpdatedAt,CURRENT_TIMESTAMP),'일 전')\n" +
                "        when TIMESTAMPDIFF(MONTH ,G.goodsUpdatedAt,CURRENT_TIMESTAMP) < 12\n" +
                "        then concat(TIMESTAMPDIFF(MONTH ,G.goodsUpdatedAt,CURRENT_TIMESTAMP), '달 전')\n" +
                "        else concat(TIMESTAMPDIFF(YEAR,G.goodsUpdatedAt,CURRENT_TIMESTAMP), '년 전')\n" +
                "        end AS goodsUpdatedAtTime\n" +
                "       from Goods G where goodsIdx=?";
        int getGoodsParams = goodsIdx;
        String getGoodsImgQuery ="select * from GoodsImg where goodsIdx=?";

        return this.jdbcTemplate.queryForObject(getGoodsQuery,
                (rs, rowNum) -> new GetGoodsDataRes(
                        rs.getInt("goodsIdx"),
                        rs.getInt("userIdx"),
                        rs.getString("goodsName"),
                        rs.getString("goodsContent"),
                        rs.getInt("goodsPrice"),
                        rs.getString("goodsUpdatedAt"),
                        rs.getString("goodsUpdatedAtTime"),
                        rs.getString("IsSecurePayment"),
                        rs.getString("IsDeilveryFee"),
                        rs.getInt("goodsCount"),
                        rs.getString("goodsCondition"),
                        getGoodsImgRes = this.jdbcTemplate.query(getGoodsImgQuery,
                                (rk,rownum)->new GetGoodsImgRes(rk.getInt("goodsIdx"),
                                        rk.getString("goodsImgUrl")),rs.getInt("goodsIdx")))
                ,getGoodsParams) ;

    }
    public List<GetStoreGoodsRes> getStoreGoods(int userIdx){
        String getStoreQuery ="select * from Goods where userIdx=?";
        int getStoreparams = userIdx;
        String getGoodsImgQuery ="select * from GoodsImg inner join Goods where userIdx=?";
        String getStoreReviewQuery="select *\n" +
                "from Review inner join User U on Review.buyerIdx = U.userIdx\n" +
                "\n" +
                "where sellerIdx=?";

        return this.jdbcTemplate.query(getStoreQuery,
                (rs,rowNum)->new GetStoreGoodsRes(
                        rs.getInt("goodsIdx"),
                        rs.getInt("userIdx"),
                        rs.getString("goodsName"),
                        rs.getInt("goodsPrice"),
                        getGoodsImgRes=this.jdbcTemplate.query(getGoodsImgQuery,
                                (rk,rownum)->new GetGoodsImgRes(
                                        rk.getInt("goodsIdx"),
                                        rk.getString("goodsImgUrl")),
                                rs.getInt("userIdx"))),getStoreparams
                        );

    }

    public List<GetStoreReviewRes> getStoreReviews(int userIdx){
        int getStoreparams = userIdx;
        String getStoreReviewQuery="select *,\n" +
                "        case when TIMESTAMPDIFF(SECOND, R.reviewUpdatedAt,CURRENT_TIMESTAMP)<60\n" +
                "        then concat(TIMESTAMPDIFF(SECOND, R.reviewUpdatedAt,CURRENT_TIMESTAMP),'초 전')\n" +
                "        when TIMESTAMPDIFF(MINUTE , R.reviewUpdatedAt,CURRENT_TIMESTAMP)<60\n" +
                "        then concat(TIMESTAMPDIFF(MINUTE , R.reviewUpdatedAt,CURRENT_TIMESTAMP),'분 전')\n" +
                "        when TIMESTAMPDIFF(HOUR , R.reviewUpdatedAt,CURRENT_TIMESTAMP)<24\n" +
                "        then concat(TIMESTAMPDIFF(HOUR , R.reviewUpdatedAt,CURRENT_TIMESTAMP),'시간 전')\n" +
                "        when TIMESTAMPDIFF(DAY , R.reviewUpdatedAt,CURRENT_TIMESTAMP)<30\n" +
                "        then concat(TIMESTAMPDIFF(DAY , R.reviewUpdatedAt,CURRENT_TIMESTAMP),'일 전')\n" +
                "        when TIMESTAMPDIFF(MONTH ,R.reviewUpdatedAt,CURRENT_TIMESTAMP) < 12\n" +
                "        then concat(TIMESTAMPDIFF(MONTH ,R.reviewUpdatedAt,CURRENT_TIMESTAMP), '달 전')\n" +
                "        else concat(TIMESTAMPDIFF(YEAR,R.reviewUpdatedAt,CURRENT_TIMESTAMP), '년 전')\n" +
                "        end AS reviewUpdatedAtTime\n" +
                "from Review R inner join User U on R.buyerIdx = U.userIdx\n" +
                "inner join Goods G on U.userIdx = G.userIdx\n" +
                "where sellerIdx=? group by reviewIdx;;";

        return this.jdbcTemplate.query(getStoreReviewQuery,
                (rm,rownum)->new GetStoreReviewRes(
                        rm.getInt("goodsIdx"),
                        rm.getString("reviewContent"),
                        rm.getDouble("score"),
                        rm.getString("reviewCreatedAt"),
                        rm.getString("reviewUpdatedAtTime"))
                ,getStoreparams);
    }
}
