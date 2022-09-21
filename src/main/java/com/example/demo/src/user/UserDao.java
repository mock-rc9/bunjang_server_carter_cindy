package com.example.demo.src.user;


import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public int createUser(PostUserReq postUserReq){
        String createUserQuery = "insert into User (userEmail, userPassword, userNickName, userImgUrl) VALUES (?,?,?,?)";
        Object[] createUserParams = new Object[]{postUserReq.getUserEmail(), postUserReq.getUserPassword(), postUserReq.getUserNickName(), postUserReq.getUserImgUrl()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    public int checkEmail(String userEmail){
        String checkEmailQuery = "select exists(select userEmail from User where userEmail = ?)";
        String checkEmailParams = userEmail;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);
    }

    public User getPwd(PostLoginReq postLoginReq){
        String getPwdQuery = "select userIdx, userEmail, userPassword, userNickName, userImgUrl, userStatus from User where userEmail = ?";
        String getPwdParams = postLoginReq.getUserEmail();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs,rowNum)-> new User(
                        rs.getInt("userIdx"),
                        rs.getString("userEmail"),
                        rs.getString("userPassword"),
                        rs.getString("userNickName"),
                        rs.getString("userImgUrl"),
                        rs.getString("userStatus")
                ),
                getPwdParams
                );

    }

    public List<GetGoodsRes> getGoods(int userIdx){
        String getGoodsQuery = "select G.goodsIdx, goodsStatus\n" +
                "     , (select goodsImgUrl from GoodsImg\n" +
                "       where G.goodsIdx = GoodsImg.goodsIdx limit 1) as goodsImgUrl\n" +
                "     , goodsPrice, goodsName\n" +
                "     , case\n" +
                "         when goodsAddress is null then '지역정보 없음'\n" +
                "        else goodsAddress end as goodsAddress\n" +
                "    , case\n" +
                "        when TIMESTAMPDIFF(MINUTE , goodsCreatedAt, NOW()) <= 0\n" +
                "            then CONCAT('방금 전')\n" +
                "        when TIMESTAMPDIFF(MINUTE, goodsCreatedAt, NOW()) < 60\n" +
                "            then CONCAT(TIMESTAMPDIFF(MINUTE, goodsCreatedAt, NOW()), '분 전')\n" +
                "        when TIMESTAMPDIFF(HOUR, goodsCreatedAt, NOW()) < 24\n" +
                "            then CONCAT(TIMESTAMPDIFF(HOUR, goodsCreatedAt, NOW()), '시간 전')\n" +
                "        when TIMESTAMPDIFF(DAY, goodsCreatedAt, NOW()) < 30\n" +
                "            then CONCAT(TIMESTAMPDIFF(DAY, goodsCreatedAt, NOW()), '일 전')\n" +
                "        when TIMESTAMPDIFF(MONTH, goodsCreatedAt, NOW()) < 12\n" +
                "            then CONCAT(TIMESTAMPDIFF(MONTH, goodsCreatedAt, NOW()), '달 전')\n" +
                "    else CONCAT(TIMESTAMPDIFF(YEAR, goodsCreatedAt, NOW()), '년 전') end as lastUploadTime\n" +
                "from User\n" +
                "inner join Goods G on User.userIdx = G.userIdx\n" +
                "where G.userIdx = ? and goodsStatus = 'active'";
        int getGoodsParams = userIdx;
        return jdbcTemplate.query(getGoodsQuery,
                (rs, rsNum) -> new GetGoodsRes(
                        rs.getInt("goodsIdx"),
                        rs.getString("goodsStatus"),
                        rs.getString("goodsImgUrl"),
                        rs.getInt("goodsPrice"),
                        rs.getString("goodsName"),
                        rs.getString("goodsAddress"),
                        rs.getString("lastUploadTime")),
                getGoodsParams);
    }

    public List<GetGoodsRes> getGoodsByName(int userIdx, String searchName){
        String getGoodsByNameQuery = "select G.goodsIdx, goodsStatus\n" +
                "     , (select goodsImgUrl from GoodsImg\n" +
                "       where G.goodsIdx = GoodsImg.goodsIdx limit 1) as goodsImgUrl\n" +
                "     , goodsPrice, goodsName\n" +
                "     , case\n" +
                "         when goodsAddress is null then '지역정보 없음'\n" +
                "        else goodsAddress end as goodsAddress\n" +
                "    , case\n" +
                "        when TIMESTAMPDIFF(MINUTE , goodsCreatedAt, NOW()) <= 0\n" +
                "            then CONCAT('방금 전')\n" +
                "        when TIMESTAMPDIFF(MINUTE, goodsCreatedAt, NOW()) < 60\n" +
                "            then CONCAT(TIMESTAMPDIFF(MINUTE, goodsCreatedAt, NOW()), '분 전')\n" +
                "        when TIMESTAMPDIFF(HOUR, goodsCreatedAt, NOW()) < 24\n" +
                "            then CONCAT(TIMESTAMPDIFF(HOUR, goodsCreatedAt, NOW()), '시간 전')\n" +
                "        when TIMESTAMPDIFF(DAY, goodsCreatedAt, NOW()) < 30\n" +
                "            then CONCAT(TIMESTAMPDIFF(DAY, goodsCreatedAt, NOW()), '일 전')\n" +
                "        when TIMESTAMPDIFF(MONTH, goodsCreatedAt, NOW()) < 12\n" +
                "            then CONCAT(TIMESTAMPDIFF(MONTH, goodsCreatedAt, NOW()), '달 전')\n" +
                "    else CONCAT(TIMESTAMPDIFF(YEAR, goodsCreatedAt, NOW()), '년 전') end as lastUploadTime\n" +
                "from User\n" +
                "inner join Goods G on User.userIdx = G.userIdx\n" +
                "where G.userIdx = ? and goodsName like concat('%', ?,'%') and goodsStatus = 'active'";
        int getGoodsByNameParams1 = userIdx;
        String getGoodsByNameParams2 = searchName;
        return jdbcTemplate.query(getGoodsByNameQuery,
                (rs, rsNum) -> new GetGoodsRes(
                        rs.getInt("goodsIdx"),
                        rs.getString("goodsStatus"),
                        rs.getString("goodsImgUrl"),
                        rs.getInt("goodsPrice"),
                        rs.getString("goodsName"),
                        rs.getString("goodsAddress"),
                        rs.getString("lastUploadTime")),
                getGoodsByNameParams1, getGoodsByNameParams2);
    }

    public GetMyPageRes getMyPage(int userIdx){
        String getMyPageAndGoodsQuery = "select userIdx, userImgUrl, userNickName\n" +
                "     , (select case when AVG(score) is null then 0 else AVG(score) end\n" +
                "        from Review\n" +
                "        where sellerIdx = User.userIdx\n" +
                "          and reviewStatus = 'active') as scoreAvg\n" +
                "    , (select COUNT(orderIdx)\n" +
                "        from Orders\n" +
                "        inner join Goods G on Orders.goodsIdx = G.goodsIdx\n" +
                "        where userIdx = User.userIdx and orderStatus = 'active') as tradeCount\n" +
                "    , (select COUNT(followIdx)\n" +
                "        from Follow\n" +
                "        where followingIdx = User.userIdx and followStatus = 'active') as follower\n" +
                "    , (select COUNT(followIdx)\n" +
                "        from Follow\n" +
                "        where followerIdx = User.userIdx and followStatus = 'active') as following\n" +
                "    , (select COUNT(orderIdx)\n" +
                "        from Orders\n" +
                "        inner join Goods G on Orders.goodsIdx = G.goodsIdx\n" +
                "        where userIdx = User.userIdx and goodsStatus = 'active' and IsSecurePayment = 'Y') as securePaymentCount\n" +
                "    , CONCAT('+', TIMESTAMPDIFF(DAY, userCreatedAt, now())) as openDay\n" +
                "    , 'OK' as userStatusCheck\n" +
                "from User\n" +
                "where userIdx = ? and userStatus = 'active'";
        int getMyPageAndGoodsParams = userIdx;
        return jdbcTemplate.queryForObject(getMyPageAndGoodsQuery,
                (rs, rsNum) -> new GetMyPageRes(
                        rs.getInt("userIdx"),
                        rs.getString("userImgUrl"),
                        rs.getString("userNickName"),
                        rs.getDouble("scoreAvg"),
                        rs.getInt("tradeCount"),
                        rs.getInt("follower"),
                        rs.getInt("following"),
                        rs.getInt("securePaymentCount"),
                        rs.getString("openDay"),
                        rs.getString("userStatusCheck"),
                        getGoods(userIdx)),
                getMyPageAndGoodsParams);
    }

    public GetMyPageRes getMyPageByName(int userIdx, String searchName){
        String getMyPageByNameQuery = "select userIdx, userImgUrl, userNickName\n" +
                "     , (select case when AVG(score) is null then 0 else AVG(score) end\n" +
                "        from Review\n" +
                "        where sellerIdx = User.userIdx\n" +
                "          and reviewStatus = 'active') as scoreAvg\n" +
                "    , (select COUNT(orderIdx)\n" +
                "        from Orders\n" +
                "        inner join Goods G on Orders.goodsIdx = G.goodsIdx\n" +
                "        where userIdx = User.userIdx and orderStatus = 'active') as tradeCount\n" +
                "    , (select COUNT(followIdx)\n" +
                "        from Follow\n" +
                "        where followingIdx = User.userIdx and followStatus = 'active') as follower\n" +
                "    , (select COUNT(followIdx)\n" +
                "        from Follow\n" +
                "        where followerIdx = User.userIdx and followStatus = 'active') as following\n" +
                "    , (select COUNT(orderIdx)\n" +
                "        from Orders\n" +
                "        inner join Goods G on Orders.goodsIdx = G.goodsIdx\n" +
                "        where userIdx = User.userIdx and goodsStatus = 'active' and IsSecurePayment = 'Y') as securePaymentCount\n" +
                "    , CONCAT('+', TIMESTAMPDIFF(DAY, userCreatedAt, now())) as openDay\n" +
                "    , 'OK' as userStatusCheck\n" +
                "from User\n" +
                "where userIdx = ? and userStatus = 'active'";
        int getMyPageByNameParams = userIdx;
        return jdbcTemplate.queryForObject(getMyPageByNameQuery,
                (rs, rsNum) -> new GetMyPageRes(
                        rs.getInt("userIdx"),
                        rs.getString("userImgUrl"),
                        rs.getString("userNickName"),
                        rs.getDouble("scoreAvg"),
                        rs.getInt("tradeCount"),
                        rs.getInt("follower"),
                        rs.getInt("following"),
                        rs.getInt("securePaymentCount"),
                        rs.getString("openDay"),
                        rs.getString("userStatusCheck"),
                        getGoodsByName(userIdx, searchName)),
                getMyPageByNameParams);
    }

}
