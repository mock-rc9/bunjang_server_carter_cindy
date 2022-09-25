package com.example.demo.src.follow;

import com.example.demo.src.follow.model.GetFollowRes;
import com.example.demo.src.follow.model.GetGoodsListRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class FollowDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetGoodsListRes> getGoodsList(int userIdx){
        String getGoodsListQuery = "select goodsIdx, goodsPrice\n" +
                "    , (select goodsImgUrl\n" +
                "       from GoodsImg\n" +
                "       where GoodsImg.goodsIdx = Goods.goodsIdx\n" +
                "       limit 1) as goodsImgUrl\n" +
                "from Goods\n" +
                "where userIdx = ?";
        int getGoodsListParams = userIdx;

        return jdbcTemplate.query(getGoodsListQuery,
                (rs, rsNum) -> new GetGoodsListRes(
                        rs.getInt("goodsIdx"),
                        rs.getInt("goodsPrice"),
                        rs.getString("goodsImgUrl")),
                getGoodsListParams);
    }

    public List<GetFollowRes> getFollowers(int userIdx) {
        String getFollowersQuery = "select userIdx, userImgUrl, userNickName\n" +
                "    , (select COUNT(*)\n" +
                "       from Goods\n" +
                "       where Goods.userIdx = User.userIdx) as goodsCount\n" +
                "    , (select COUNT(*)\n" +
                "       from Follow\n" +
                "       where followingIdx = User.userIdx) as followerCount\n" +
                "from User\n" +
                "where userIdx in (select followerIdx\n" +
                "from Follow\n" +
                "where followingIdx = ? and followStatus = 'active')";
        int getFollowersParams = userIdx;

        return jdbcTemplate.query(getFollowersQuery,
                (rs, rsNum) -> new GetFollowRes(
                        rs.getInt("userIdx"),
                        rs.getString("userImgUrl"),
                        rs.getString("userNickName"),
                        rs.getInt("goodsCount"),
                        rs.getInt("followerCount"),
                        getGoodsList(rs.getInt("userIdx"))),
                getFollowersParams);
    }

    public List<GetFollowRes> getFollowings(int userIdx) {
        String getFollowingsQuery = "select userIdx, userImgUrl, userNickName\n" +
                "    , (select COUNT(*)\n" +
                "       from Goods\n" +
                "       where Goods.userIdx = User.userIdx) as goodsCount\n" +
                "    , (select COUNT(*)\n" +
                "       from Follow\n" +
                "       where followingIdx = User.userIdx) as followerCount\n" +
                "from User\n" +
                "where userIdx in (select followingIdx\n" +
                "from Follow\n" +
                "where followerIdx = ? and followStatus = 'active')";
        int getFollowingsParams = userIdx;

        return jdbcTemplate.query(getFollowingsQuery,
                (rs, rsNum) -> new GetFollowRes(
                        rs.getInt("userIdx"),
                        rs.getString("userImgUrl"),
                        rs.getString("userNickName"),
                        rs.getInt("goodsCount"),
                        rs.getInt("followerCount"),
                        getGoodsList(rs.getInt("userIdx"))),
                getFollowingsParams);
    }

    public int follow(int followerIdx, int followingIdx){
        String followQuery = "insert into Follow (followerIdx, followingIdx) VALUES (?, ?);";
        Object[] followParams = new Object[]{followerIdx, followingIdx};
        this.jdbcTemplate.update(followQuery, followParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    public int checkFollow(int followerIdx, int followingIdx){
        String checkFollowQuery = "select followIdx\n" +
                "from Follow\n" +
                "where followerIdx = ? and followingIdx = ?";
        Object[] checkFollowParams = new Object[]{followerIdx, followingIdx};

        return this.jdbcTemplate.queryForObject(checkFollowQuery, int.class, checkFollowParams);
    }

    public int unfollow(int followIdx){
        String unfollowQuery = "update Follow set followStatus = 'deleted' where followIdx = ?";
        int unfollowParams = followIdx;

        return this.jdbcTemplate.update(unfollowQuery, unfollowParams);
    }

}
