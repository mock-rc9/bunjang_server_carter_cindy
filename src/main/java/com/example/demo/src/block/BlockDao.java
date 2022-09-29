package com.example.demo.src.block;

import com.example.demo.src.block.model.GetBlockRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
public class BlockDao {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public int  checkUserExist(int userIdx) {
        String checkUserExistQuery = "select exists(select userIdx from User where userIdx = ?)";
        int checkUserExistParams = userIdx;
        return this.jdbcTemplate.queryForObject(checkUserExistQuery,
                int.class,
                checkUserExistParams);
    }

    public List<GetBlockRes> getBlocks(int userIdx) {
        String getUserBlockQuery ="select * from Block\n" +
                "    inner join User U on Block.blockedUserIdx = U.userIdx\n" +
                "         where Block.userIdx=?";
        int getUserParams=userIdx;
        return this.jdbcTemplate.query(getUserBlockQuery,
                (rs,rowNum)->new GetBlockRes(
                        rs.getString("userNickName"),
                        rs.getString("userImgUrl"),
                        rs.getString("blockUpdatedAt")),getUserParams);
    }
}
