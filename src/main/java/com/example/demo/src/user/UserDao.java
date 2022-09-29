package com.example.demo.src.user;


import com.example.demo.src.address.model.PatchAddressReq;
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
        String createUserQuery = "insert into User (userEmail, userPassword, userNickName) VALUES (?,?,?)";
        Object[] createUserParams = new Object[]{postUserReq.getUserEmail(), postUserReq.getUserPassword(), postUserReq.getUserNickName()};
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
                "     , IsSecurePayment, goodsPrice, goodsName\n" +
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
                "    , (select COUNT(*)\n" +
                "        from GoodsLike\n" +
                "        where GoodsLike.goodsIdx = G.goodsIdx) as countLike\n" +
                "from User\n" +
                "inner join Goods G on User.userIdx = G.userIdx\n" +
                "where G.userIdx = ? and goodsStatus = 'active'";
        int getGoodsParams = userIdx;
        return jdbcTemplate.query(getGoodsQuery,
                (rs, rsNum) -> new GetGoodsRes(
                        rs.getInt("goodsIdx"),
                        rs.getString("goodsStatus"),
                        rs.getString("goodsImgUrl"),
                        rs.getString("IsSecurePayment"),
                        rs.getInt("goodsPrice"),
                        rs.getString("goodsName"),
                        rs.getString("goodsAddress"),
                        rs.getString("lastUploadTime"),
                        rs.getInt("countLike")),
                getGoodsParams);
    }

    public List<GetGoodsRes> getGoodsByName(int userIdx, String searchName){
        String getGoodsByNameQuery = "select G.goodsIdx, goodsStatus\n" +
                "     , (select goodsImgUrl from GoodsImg\n" +
                "       where G.goodsIdx = GoodsImg.goodsIdx limit 1) as goodsImgUrl\n" +
                "     , IsSecurePayment, goodsPrice, goodsName\n" +
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
                "    , (select COUNT(*)\n" +
                "        from GoodsLike\n" +
                "        where GoodsLike.goodsIdx = G.goodsIdx) as countLike\n" +
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
                        rs.getString("IsSecurePayment"),
                        rs.getInt("goodsPrice"),
                        rs.getString("goodsName"),
                        rs.getString("goodsAddress"),
                        rs.getString("lastUploadTime"),
                        rs.getInt("countLike")),
                getGoodsByNameParams1, getGoodsByNameParams2);
    }

    public GetMyPageRes getMyPage(int userIdx){
        String getMyPageAndGoodsQuery = "select userIdx, userImgUrl, userNickName, userContent\n" +
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
                        rs.getString("userContent"),
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
        String getMyPageByNameQuery = "select userIdx, userImgUrl, userNickName, userContent\n" +
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
                        rs.getString("userContent"),
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

    public List<GetReviewsRes> getReviews(int userIdx) {
        String getReviewsQuery = "select reviewIdx, score as reviewScore, reviewContent, isSecurePayment\n" +
                "     , (select userNickName\n" +
                "        from User\n" +
                "        where User.userIdx = Review.buyerIdx) as reviewer\n" +
                "    , case\n" +
                "        when TIMESTAMPDIFF(MINUTE , reviewCreatedAt, NOW()) <= 0\n" +
                "            then CONCAT('방금 전')\n" +
                "        when TIMESTAMPDIFF(MINUTE, reviewCreatedAt, NOW()) < 60\n" +
                "            then CONCAT(TIMESTAMPDIFF(MINUTE, reviewCreatedAt, NOW()), '분 전')\n" +
                "        when TIMESTAMPDIFF(HOUR, reviewCreatedAt, NOW()) < 24\n" +
                "            then CONCAT(TIMESTAMPDIFF(HOUR, reviewCreatedAt, NOW()), '시간 전')\n" +
                "        when TIMESTAMPDIFF(DAY, reviewCreatedAt, NOW()) < 30\n" +
                "            then CONCAT(TIMESTAMPDIFF(DAY, reviewCreatedAt, NOW()), '일 전')\n" +
                "        when TIMESTAMPDIFF(MONTH, reviewCreatedAt, NOW()) < 12\n" +
                "            then CONCAT(TIMESTAMPDIFF(MONTH, reviewCreatedAt, NOW()), '달 전')\n" +
                "    else CONCAT(TIMESTAMPDIFF(YEAR, reviewCreatedAt, NOW()), '년 전') end as lastUploadTime\n" +
                "    , G.goodsIdx as goodsIdx, goodsName\n" +
                "from Review\n" +
                "inner join User U on Review.sellerIdx = U.userIdx\n" +
                "inner join Orders O on Review.orderIdx = O.orderIdx\n" +
                "inner join Goods G on O.goodsIdx = G.goodsIdx\n" +
                "where U.userIdx = ? and reviewStatus = 'active'";
        int getReviewsParams = userIdx;

        return this.jdbcTemplate.query(getReviewsQuery,
                (rs, rsNum) -> new GetReviewsRes(
                        rs.getInt("reviewIdx"),
                        rs.getString("reviewScore"),
                        rs.getString("reviewContent"),
                        rs.getString("isSecurePayment"),
                        rs.getString("reviewer"),
                        rs.getString("lastUploadTime"),
                        rs.getInt("goodsIdx"),
                        rs.getString("goodsName")),
                getReviewsParams);
    }

    public int modifyUserInfo(int userIdx, PatchUserReq patchUserReq){
        String modifyUserInfoQuery = "update User set userNickName = ?, userContent = ? where userIdx = ?";
        Object[] modifyUserInfoParams = new Object[]{patchUserReq.getUserNickName(), patchUserReq.getUserContent(), userIdx};
        return this.jdbcTemplate.update(modifyUserInfoQuery, modifyUserInfoParams);
    }

    public int modifyUploadFile(int userIdx, UploadFile uploadFile){
        String modifyUploadFileQuery = "update User set userImgUrl = ?, userImgFileName = ? where userIdx = ?";
        Object[] modifyUploadFileParams = new Object[]{uploadFile.getStoreFileUrl(), uploadFile.getUploadFileName(), userIdx};
        return this.jdbcTemplate.update(modifyUploadFileQuery, modifyUploadFileParams);
    }

    public int deleteUser(int userIdx){
        String deleteUserQuery = "update User set userStatus = 'deleted' where userIdx = ?";
        int deleteUserParams = userIdx;
        return this.jdbcTemplate.update(deleteUserQuery, deleteUserParams);
    }

    public GetSellerPageRes getSellerPage(int userIdx, int sellerIdx){
        String getSellerPageQuery = "select userIdx as sellerIdx\n" +
                "     , (select exists (select followIdx\n" +
                "        from Follow\n" +
                "        where followerIdx = ? and followingIdx = ? and followStatus = 'active')) as isFollow\n" +
                "     , userImgUrl, userNickName, userContent\n" +
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
        int getSellerPageParams1 = userIdx;
        int getSellerPageParams2 = sellerIdx;
        int getSellerPageParams3 = sellerIdx;
        return jdbcTemplate.queryForObject(getSellerPageQuery,
                (rs, rsNum) -> new GetSellerPageRes(
                        rs.getInt("sellerIdx"),
                        rs.getInt("isFollow"),
                        rs.getString("userImgUrl"),
                        rs.getString("userNickName"),
                        rs.getString("userContent"),
                        rs.getDouble("scoreAvg"),
                        rs.getInt("tradeCount"),
                        rs.getInt("follower"),
                        rs.getInt("following"),
                        rs.getInt("securePaymentCount"),
                        rs.getString("openDay"),
                        rs.getString("userStatusCheck"),
                        getGoods(sellerIdx)),
                getSellerPageParams1, getSellerPageParams2, getSellerPageParams3);
    }

    public String getUserPhoneNum(int userIdx){
        String getUserPhoneNumQuery = "select userPhoneNum\n" +
                "from User\n" +
                "where userIdx = ?";
        int getUserPhoneNumParams = userIdx;

        return this.jdbcTemplate.queryForObject(getUserPhoneNumQuery, String.class, getUserPhoneNumParams);
    }

}
