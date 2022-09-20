package com.example.demo.src.home;

import com.example.demo.src.home.model.GetHomeDataRes;
import com.example.demo.src.home.model.GetHomeImgRes;
import com.example.demo.src.home.model.GetHomeRes;
import com.example.demo.src.home.model.GetMainPageImgRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
public class HomeDao {


    private JdbcTemplate jdbcTemplate;

    private List<GetHomeImgRes> getHomeImgRes;
    private List<GetMainPageImgRes> getMainPageImgRes;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public List<GetHomeDataRes> getHome() {
        String getHomeQuery = "select *\n" +
                "from Goods inner join  User U on Goods.userIdx = U.userIdx\n" +
                "left join Address A on U.userIdx = A.userIdx";
        String getHomeImgQuery = "select *\n" +
                "from GoodsImg";
        return this.jdbcTemplate.query(getHomeQuery,
                (rs,rowNum) -> new GetHomeDataRes(
                        rs.getInt("goodsIdx"),
                        rs.getString("goodsName"),
                        rs.getInt("goodsPrice"),
                        rs.getString("goodsCreatedAt"),
                        rs.getString( "IsSecurePayment"),
                        rs.getString("address"),
                        getHomeImgRes= this.jdbcTemplate.query(getHomeImgQuery,
                                (rk,rownum)->new GetHomeImgRes(rk.getInt("goodsIdx"),
                                        rk.getString("goodsImgUrl"))
                                )
                        )
        );

    }

    /* 메인페이지 이미지 URL 불러오는 쿼리문*/
    public List<GetMainPageImgRes> getPageImg(){
        String getMainImgQuery = "select *\n" +
                "from GoodsImg";
        return this.jdbcTemplate.query(getMainImgQuery,
                (rs,rowNum) -> new GetMainPageImgRes(
                        rs.getString("mainPageImgUrl")));


    }
}