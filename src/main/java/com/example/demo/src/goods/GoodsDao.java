package com.example.demo.src.goods;


import com.example.demo.src.goods.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
public class GoodsDao {



    private List<GetGoodsImgRes> getGoodsImgRes;
    private List<GetStoreReviewRes> getStoreReviewRes;
    private List<GetGoodsLikeRes> getGoodsLikeRes;
    private List<GetCategoryOptionRes> getCategoryOptionRes;

    private List<GetStoreFollowRes> getStoreFollowRes;



    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public List<GetCategoryRes> getCategorys() {
        String getCategoryQuery ="select * from Category";
        String getCategoryOptionQuery ="select * from CategoryOption where categoryIdx=?";
        return this.jdbcTemplate.query(getCategoryQuery,
                (rs,rowNum)->new GetCategoryRes(
                        rs.getInt("categoryIdx"),
                        rs.getString("categoryName"),
                        getCategoryOptionRes = this.jdbcTemplate.query(getCategoryOptionQuery,
                                (rk,rownum)->new GetCategoryOptionRes(rk.getInt("categoryIdx"),
                                        rk.getInt("categoryOptionIdx"),
                                        rk.getString("categoryOptionName")),
                                rs.getInt("categoryIdx"))));
    }
    public List<GetCategoryOptionRes> getCategory(int categoryIdx) {

        int getCategoryParams = categoryIdx;
        String getCategoryOptionQuery ="select * from CategoryOption where categoryIdx=?";
        return this.jdbcTemplate.query(getCategoryOptionQuery,
                (rk,rownum)->new GetCategoryOptionRes(
                        rk.getInt("categoryIdx"),
                        rk.getInt("categoryOptionIdx"),
                        rk.getString("categoryOptionName")),getCategoryParams);

    }


    /*?????? ?????? ??????*/
    public GetGoodsDataRes getGoods(int goodsIdx) {
        String getGoodsQuery ="select G.goodsIdx,G.userIdx,G.goodsName,G.goodsContent,G.goodsPrice,G.goodsUpdatedAt,G.IsDeilveryFee,G.IsDeilveryFee,G.goodsCount,G.goodsCondition,G.IsExchange,G.IsSecurePayment, COUNT(chatRoomIdx) as Chat,\n" +
                "                        case when TIMESTAMPDIFF(SECOND, G.goodsUpdatedAt,CURRENT_TIMESTAMP)<60\n" +
                "                        then concat(TIMESTAMPDIFF(SECOND, G.goodsUpdatedAt,CURRENT_TIMESTAMP),'??? ???')\n" +
                "                        when TIMESTAMPDIFF(MINUTE , G.goodsUpdatedAt,CURRENT_TIMESTAMP)<60\n" +
                "                        then concat(TIMESTAMPDIFF(MINUTE , G.goodsUpdatedAt,CURRENT_TIMESTAMP),'??? ???')\n" +
                "                        when TIMESTAMPDIFF(HOUR , G.goodsUpdatedAt,CURRENT_TIMESTAMP)<24\n" +
                "                        then concat(TIMESTAMPDIFF(HOUR , G.goodsUpdatedAt,CURRENT_TIMESTAMP),'?????? ???')\n" +
                "                        when TIMESTAMPDIFF(DAY , G.goodsUpdatedAt,CURRENT_TIMESTAMP)<30\n" +
                "                        then concat(TIMESTAMPDIFF(DAY , G.goodsUpdatedAt,CURRENT_TIMESTAMP),'??? ???')\n" +
                "                        when TIMESTAMPDIFF(MONTH ,G.goodsUpdatedAt,CURRENT_TIMESTAMP) < 12\n" +
                "                        then concat(TIMESTAMPDIFF(MONTH ,G.goodsUpdatedAt,CURRENT_TIMESTAMP), '??? ???')\n" +
                "                        else concat(TIMESTAMPDIFF(YEAR,G.goodsUpdatedAt,CURRENT_TIMESTAMP), '??? ???')\n" +
                "                        end AS goodsUpdatedAtTime,\n" +
                "                case when goodsAddress is null then '???????????? ??????'\n" +
                "                        else goodsAddress end goodsAddressnull\n" +
                "                       from Goods G left join ChatRoom CR on G.goodsIdx = CR.goodsIdx where G.goodsIdx=? and G.goodsStatus='active'";
        int getGoodsParams = goodsIdx;

        String getGoodsLikeQuery ="select count(*) as likes from GoodsLike where goodsIdx=?";




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
                        rs.getString("goodsAddressnull"),
                        rs.getString("IsDeilveryFee"),
                        rs.getInt("goodsCount"),
                        rs.getString("goodsCondition"),
                        rs.getString("IsExchange"),
                        rs.getInt("Chat"),
                        getGoodsLikeRes = this.jdbcTemplate.query(getGoodsLikeQuery,
                                (rl,rownuM)->new GetGoodsLikeRes(
                                        rl.getInt("likes")),rs.getInt("goodsIdx")),
                        getGoodsImgRes = this.jdbcTemplate.query(getGoodsImgQuery,
                                (rk,rownum)->new GetGoodsImgRes(
                                        rk.getInt("goodsIdx"),
                                        rk.getString("goodsImgUrl")),
                                rs.getInt("goodsIdx"))),getGoodsParams);

    }

    public GetStoreDataRes getStoreData(int userIdx) {

        int getStoreParams = userIdx;
        String getStoreDataQuery ="select ROUND(AVG(R.score),1) as score ,User.userNickName,User.userImgUrl\n" +
                "\n" +
                "       from User\n" +
                "\n" +
                "            inner join Goods G on User.userIdx = G.userIdx\n" +
                "            left join ChatRoom CR on G.goodsIdx = CR.goodsIdx\n" +
                "        left join Review R on User.userIdx = R.sellerIdx\n" +
                "where G.userIdx=?";

        String getFollowerQuery ="select count(followIdx) as follow\n" +
                "from User as U inner join Follow F on U.userIdx = F.followingIdx\n" +
                "where U.userIdx=? and followStatus='active' ";
        return this.jdbcTemplate.queryForObject(getStoreDataQuery,
                (rk,rownum)->new GetStoreDataRes(
                        rk.getDouble("score"),
                        rk.getString("userImgUrl"),
                        rk.getString("userNickName"),
                        getStoreFollowRes=this.jdbcTemplate.query(getFollowerQuery,
                                (rl,roWnum)->new GetStoreFollowRes(
                                        rl.getInt("follow")),getStoreParams)),getStoreParams
                        );


    }
    public List<GetStoreGoodsRes> getStoreGoods(int userIdx){
        String getStoreQuery ="select *,\n" +
                "                (select COUNT(*) from GoodsLike where GoodsLike.userIdx=Goods.userIdx and GoodsLike.goodsIdx = Goods.goodsIdx) as goodsLike\n" +
                "                  from Goods\n" +
                "                    left join User U on Goods.userIdx = U.userIdx\n" +
                "                    left join GoodsLike GL on Goods.goodsIdx = GL.goodsIdx and U.userIdx = GL.userIdx\n" +
                "                         where Goods.userIdx=? and Goods.goodsStatus='active' ";
        int getStoreparams = userIdx;
        String getGoodsImgQuery ="select * from GoodsImg left join Goods G on G.goodsIdx = GoodsImg.goodsIdx where G.goodsIdx=? and goodsStatus='active'";



        return this.jdbcTemplate.query(getStoreQuery,
                (rs,rowNum)->new GetStoreGoodsRes(
                        rs.getInt("goodsIdx"),
                        rs.getInt("userIdx"),
                        rs.getString("goodsName"),
                        rs.getInt("goodsPrice"),
                        rs.getInt("goodsLike"),
                        rs.getInt("goodsLikeIdx"),
                        getGoodsImgRes=this.jdbcTemplate.query(getGoodsImgQuery,
                                (rk,rownum)->new GetGoodsImgRes(
                                        rk.getInt("goodsIdx"),
                                        rk.getString("goodsImgUrl")),
                                rs.getInt("goodsIdx"))),getStoreparams
                        );

    }

    public List<GetStoreReviewRes> getStoreReviews(int userIdx){
        int getStoreparams = userIdx;
        String getStoreReviewQuery="select *,\n" +
                "        case when TIMESTAMPDIFF(SECOND, R.reviewUpdatedAt,CURRENT_TIMESTAMP)<60\n" +
                "        then concat(TIMESTAMPDIFF(SECOND, R.reviewUpdatedAt,CURRENT_TIMESTAMP),'??? ???')\n" +
                "        when TIMESTAMPDIFF(MINUTE , R.reviewUpdatedAt,CURRENT_TIMESTAMP)<60\n" +
                "        then concat(TIMESTAMPDIFF(MINUTE , R.reviewUpdatedAt,CURRENT_TIMESTAMP),'??? ???')\n" +
                "        when TIMESTAMPDIFF(HOUR , R.reviewUpdatedAt,CURRENT_TIMESTAMP)<24\n" +
                "        then concat(TIMESTAMPDIFF(HOUR , R.reviewUpdatedAt,CURRENT_TIMESTAMP),'?????? ???')\n" +
                "        when TIMESTAMPDIFF(DAY , R.reviewUpdatedAt,CURRENT_TIMESTAMP)<30\n" +
                "        then concat(TIMESTAMPDIFF(DAY , R.reviewUpdatedAt,CURRENT_TIMESTAMP),'??? ???')\n" +
                "        when TIMESTAMPDIFF(MONTH ,R.reviewUpdatedAt,CURRENT_TIMESTAMP) < 12\n" +
                "        then concat(TIMESTAMPDIFF(MONTH ,R.reviewUpdatedAt,CURRENT_TIMESTAMP), '??? ???')\n" +
                "        else concat(TIMESTAMPDIFF(YEAR,R.reviewUpdatedAt,CURRENT_TIMESTAMP), '??? ???')\n" +
                "        end AS reviewUpdatedAtTime\n" +
                "from Review R inner join User U on R.buyerIdx = U.userIdx\n" +
                "inner join Goods G on U.userIdx = G.userIdx\n" +
                "where sellerIdx=? group by reviewIdx";

        return this.jdbcTemplate.query(getStoreReviewQuery,
                (rm,rownum)->new GetStoreReviewRes(
                        rm.getInt("goodsIdx"),
                        rm.getInt("buyerIdx"),
                        rm.getString("reviewContent"),
                        rm.getDouble("score"),
                        rm.getString("reviewCreatedAt"),
                        rm.getString("reviewUpdatedAtTime"))
                ,getStoreparams);
    }

    public int createGoods(int userIdx,PostGoodsReq postGoodsReq) {
        String createGoodsQuery = "insert into Goods (" +
                "userIdx," +
                "goodsAddress" +
                ",goodsName" +
                ",goodsContent" +
                ",goodsPrice" +
                ",IsSecurePayment" +
                ",IsDeilveryFee" +
                ",goodsCount" +
                ",goodsCondition" +
                ",IsExchange" +
                ",categoryIdx,categoryOptionIdx) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        Object[] createGoodsParams = new Object[]{userIdx,postGoodsReq.getGoodsAddress(),postGoodsReq.getGoodsName(),postGoodsReq.getGoodsContent(),postGoodsReq.getGoodsPrice(),postGoodsReq.getIsSecurePayment(),postGoodsReq.getIsDeilveryFee(),postGoodsReq.getGoodsCount(),postGoodsReq.getGoodsCondition(),postGoodsReq.getIsExchange(),postGoodsReq.getCategoryIdx(),postGoodsReq.getCategoryOptionIdx()};
        this.jdbcTemplate.update(createGoodsQuery, createGoodsParams);
        String lastInsertIdQuery  = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }
    public int createTag(int goodsIdx, PostGoodsTagReq postGoodsTagReq) {
        String createGoodsTagQuery = "insert into Tag (goodsIdx,tagName) VALUES (?,?)";
        Object[] createGoodsTagParams = new Object[]{goodsIdx,postGoodsTagReq.getTagName()};
        this.jdbcTemplate.update(createGoodsTagQuery, createGoodsTagParams);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

//    public int createGoodsImg(int goodsIdx, MultipartFile multipartFile) {
//        String createGoodsImgQuery = "insert into GoodsImg (goodsIdx,goodsImgUrl) VALUES (?,?)";
//        Object[] createGoodsImgwParams = new Object[]{goodsIdx,multipartFile};
//        this.jdbcTemplate.update(createGoodsImgQuery, createGoodsImgwParams);
//        String lastInsertIdQuery = "select last_insert_id()";
//        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
//    }
    public int updateGoods(int goodsIdx, PatchGoodsReq patchGoodsReq) {
        String updateGoodsQuery = "UPDATE Goods\n" +
                "        SET goodsContent = ?\n" +
                "        WHERE goodsIdx = ?" ;
        Object[] updateGoodsParams = new Object[]{patchGoodsReq.getGoodsContent(), goodsIdx};
        return this.jdbcTemplate.update(updateGoodsQuery,updateGoodsParams);
    }

    public int checkUserExist(int userIdx) {
        String checkUserExistQuery = "select exists(select userIdx from User where userIdx = ? and userStatus='active')";
        int checkUserExistParams = userIdx;
        return this.jdbcTemplate.queryForObject(checkUserExistQuery,
                int.class,
                checkUserExistParams);
    }

    public int checkGoodsExist(int goodsIdx) {
        String checkGoodsExistQuery = "select exists(select goodsIdx from Goods where goodsIdx = ? and goodsStatus='active')";
        int checkGoodsExistParams = goodsIdx;
        return this.jdbcTemplate.queryForObject(checkGoodsExistQuery,
                int.class,
                checkGoodsExistParams);
    }


    public int checkUserGoodsExist(int userIdx, int goodsIdx) {
        String checkGoodsExistQuery = "select exists(select goodsIdx from Goods where goodsIdx = ? and userIdx=? and goodsStatus='active') ";
        Object[]  checkGoodsExistParams = new Object[]{goodsIdx,userIdx};
        return this.jdbcTemplate.queryForObject(checkGoodsExistQuery,
                int.class,
                checkGoodsExistParams);
    }

    public int deleteGoods(int goodsIdx) {
        String deleteGoodsQuery = "UPDATE Goods\n" +
                "        SET goodsStatus = 'deleted'\n" +
                "        WHERE goodsIdx = ? ";
        Object[] deleteGoodsParams = new Object[]{goodsIdx};
        return this.jdbcTemplate.update(deleteGoodsQuery,deleteGoodsParams);

    }




    public List<GetGoodsSearchRes> getSearchGoods(String searchGoods) {
        String getGoodsSearchQuery = "select *,\n" +
                "                        case when TIMESTAMPDIFF(SECOND, G.goodsUpdatedAt,CURRENT_TIMESTAMP)<60\n" +
                "                                        then concat(TIMESTAMPDIFF(SECOND, G.goodsUpdatedAt,CURRENT_TIMESTAMP),'??? ???')\n" +
                "                                        when TIMESTAMPDIFF(MINUTE , G.goodsUpdatedAt,CURRENT_TIMESTAMP)<60\n" +
                "                                        then concat(TIMESTAMPDIFF(MINUTE , G.goodsUpdatedAt,CURRENT_TIMESTAMP),'??? ???')\n" +
                "                                        when TIMESTAMPDIFF(HOUR , G.goodsUpdatedAt,CURRENT_TIMESTAMP)<24\n" +
                "                                        then concat(TIMESTAMPDIFF(HOUR , G.goodsUpdatedAt,CURRENT_TIMESTAMP),'?????? ???')\n" +
                "                                        when TIMESTAMPDIFF(DAY , G.goodsUpdatedAt,CURRENT_TIMESTAMP)<30\n" +
                "                                        then concat(TIMESTAMPDIFF(DAY , G.goodsUpdatedAt,CURRENT_TIMESTAMP),'??? ???')\n" +
                "                                        when TIMESTAMPDIFF(MONTH ,G.goodsUpdatedAt,CURRENT_TIMESTAMP) < 12\n" +
                "                                        then concat(TIMESTAMPDIFF(MONTH ,G.goodsUpdatedAt,CURRENT_TIMESTAMP), '??? ???')\n" +
                "                                        else concat(TIMESTAMPDIFF(YEAR,G.goodsUpdatedAt,CURRENT_TIMESTAMP), '??? ???')\n" +
                "                                        end AS goodsUpdatedAtTime,\n" +
                "                    (select COUNT(*)   from GoodsLike\n" +
                "                                        where GoodsLike.goodsIdx = G.goodsIdx) as likes,\n" +
                "                    (select COUNT(*) from ChatRoom where ChatRoom.goodsIdx = G.goodsIdx) as chat,\n" +
                "                    (select COUNT(*) from GoodsLike where GoodsLike.userIdx=G.userIdx and GoodsLike.goodsIdx = G.goodsIdx) as goodsLike\n" +
                "                        from Goods as G\n" +
                "\n" +
                "                    inner join GoodsImg GI on G.goodsIdx = GI.goodsIdx\n" +
                "                          left join ChatRoom CR on G.goodsIdx = CR.goodsIdx inner join User U on G.userIdx = U.userIdx\n" +
                "                          where G.goodsName LIKE concat('%',?,'%') and G.goodsStatus='active'";
        String getGoodsName = searchGoods;

        return jdbcTemplate.query(getGoodsSearchQuery,
                (rs,Numm)->new GetGoodsSearchRes(
                        rs.getInt("goodsIdx"),
                        rs.getString("goodsName"),
                        rs.getString("goodsAddress"),
                        rs.getString("IsSecurePayment"),
                        rs.getInt("goodsPrice"),
                        rs.getString("goodsUpdatedAtTime"),
                        rs.getInt("likes"),
                        rs.getInt("goodslike"),
                        rs.getString("goodsImgUrl"),
                        rs.getString("userImgUrl"),
                        rs.getInt("chat")),getGoodsName);
    }

    public int checkCategoryExits(int categoryIdx) {
        String checkCategoryExistQuery = "select exists(select categoryIdx from Category where categoryIdx = ?)";
        int checkCategoryExistParams = categoryIdx;
        return this.jdbcTemplate.queryForObject(checkCategoryExistQuery,
                int.class,
                checkCategoryExistParams);
    }
}
