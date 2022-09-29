package com.example.demo.src.storelike;

import com.example.demo.src.storelike.model.GetStoreLikeRes;
import com.example.demo.src.storelike.model.PostStoreLikeReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;


@Service
public class StoreLikeDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public List<GetStoreLikeRes> getStoreLike(int userIdx) {
        String getStoreLikeQuery =
                "select GL.goodsLikeIdx, G.goodsIdx, G.goodsName,G.goodsPrice,U.userNickName,GL.goodsLikeUpdatedAt,G.IsSecurePayment,GI.goodsImgUrl,U.userImgUrl,\n" +
                "        case when TIMESTAMPDIFF(SECOND, G.goodsUpdatedAt,CURRENT_TIMESTAMP)<60\n" +
                "                        then concat(TIMESTAMPDIFF(SECOND, G.goodsUpdatedAt,CURRENT_TIMESTAMP),'초 전')\n" +
                "                        when TIMESTAMPDIFF(MINUTE , G.goodsUpdatedAt,CURRENT_TIMESTAMP)<60\n" +
                "                        then concat(TIMESTAMPDIFF(MINUTE , G.goodsUpdatedAt,CURRENT_TIMESTAMP),'분 전')\n" +
                "                        when TIMESTAMPDIFF(HOUR , G.goodsUpdatedAt,CURRENT_TIMESTAMP)<24\n" +
                "                        then concat(TIMESTAMPDIFF(HOUR , G.goodsUpdatedAt,CURRENT_TIMESTAMP),'시간 전')\n" +
                "                        when TIMESTAMPDIFF(DAY , G.goodsUpdatedAt,CURRENT_TIMESTAMP)<30\n" +
                "                        then concat(TIMESTAMPDIFF(DAY , G.goodsUpdatedAt,CURRENT_TIMESTAMP),'일 전')\n" +
                "                        when TIMESTAMPDIFF(MONTH ,G.goodsUpdatedAt,CURRENT_TIMESTAMP) < 12\n" +
                "                        then concat(TIMESTAMPDIFF(MONTH ,G.goodsUpdatedAt,CURRENT_TIMESTAMP), '달 전')\n" +
                "                        else concat(TIMESTAMPDIFF(YEAR,G.goodsUpdatedAt,CURRENT_TIMESTAMP), '년 전')\n" +
                "                        end AS goodsUpdatedAtTime\n" +
                "from GoodsLike as GL\n" +
                "    inner join Goods G on GL.goodsIdx = G.goodsIdx\n" +
                "    inner join GoodsImg GI on G.goodsIdx = GI.goodsIdx\n" +
                "    inner join User U on G.userIdx = U.userIdx\n" +
                "where GL.userIdx = ? and GL.goodsLikeStatus='active' group by G.goodsIdx";
        int getStoreLikeparams = userIdx;
        return this.jdbcTemplate.query(getStoreLikeQuery,
                (rs, rowNum) -> new GetStoreLikeRes(
                        rs.getInt("goodsLikeIdx"),
                        rs.getInt("goodsIdx"),
                        rs.getString("goodsName"),
                        rs.getInt("goodsPrice"),
                        rs.getString("UserNickName"),
                        rs.getString("goodsUpdatedAtTime"),
                        rs.getString("IsSecurePayment"),
                        rs.getString("goodsImgUrl"),
                        rs.getString("userImgUrl")), getStoreLikeparams);


    }

    public int createStoreLike(int userIdx, PostStoreLikeReq postStoreLikeReq) {
        String createStoreLikeQuery ="insert into GoodsLike (userIdx,goodsIdx) VALUES (?,?)";
        Object[] createStoreLikeParams = new Object[]{userIdx,postStoreLikeReq.getGoodsIdx()};
        this.jdbcTemplate.update(createStoreLikeQuery, createStoreLikeParams);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    public int deleteStoreLike(int goodsLikeIdx) {
        String deleteStoreLikeQuery = "UPDATE GoodsLike\n" +
                "        SET goodsLikeStatus = 'deleted'\n" +
                "        WHERE goodsLikeIdx = ? ";
        Object[] deleteStoreLikeParams = new Object[]{goodsLikeIdx};
        return this.jdbcTemplate.update(deleteStoreLikeQuery,deleteStoreLikeParams);

    }

    public int checkGoodsExist(int goodsIdx) {
        String checkGoodsExistQuery = "select exists(select goodsIdx from Goods where goodsIdx = ? and goodsStatus='active')";
        int checkGoodsExistParams = goodsIdx;
        return this.jdbcTemplate.queryForObject(checkGoodsExistQuery,
                int.class,
                checkGoodsExistParams);
    }

    public int checkUserExist(int userIdx) {
        String checkUserExistQuery = "select exists(select userIdx from User where userIdx = ? and userStatus='active')";
        int checkUserExistParams = userIdx;
        return this.jdbcTemplate.queryForObject(checkUserExistQuery,
                int.class,
                checkUserExistParams);
    }
}
