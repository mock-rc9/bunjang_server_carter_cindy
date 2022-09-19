package com.example.demo.src.oauth;

import com.example.demo.src.oauth.model.UserInfo;
import com.example.demo.src.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UserInfoDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int createUser(UserInfo kakaoUser){
        String createUserQuery = "insert into User (userEmail, userPassword, userNickName, userImgUrl) VALUES (?,?,?,?)";
        Object[] createUserParams = new Object[]{kakaoUser.getUserEmail(), kakaoUser.getUserPassword(), kakaoUser.getUserNickName(), kakaoUser.getUserImgUrl()};
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

    public User getPwd(String userEmail){
        String getPwdQuery = "select userIdx, userEmail, userPassword, userNickName, userImgUrl, userStatus from User where userEmail = ?";
        String getPwdParams = userEmail;

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

}
