package com.example.demo.src.address;

import com.example.demo.src.address.model.GetAddressRes;
import com.example.demo.src.address.model.PatchAddressReq;
import com.example.demo.src.address.model.PostAddressReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class AddressDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetAddressRes> getAddresses(int userIdx){
        String getAddressQuery = "select addressIdx, IsBaseAddress, userName, address, addressDetail, userPhoneNum\n" +
                "from Address\n" +
                "where userIdx = ?";
        int getAddressParams = userIdx;
        return this.jdbcTemplate.query(getAddressQuery,
                (rs, rowNum) -> new GetAddressRes(
                        rs.getInt("addressIdx"),
                        rs.getString("IsBaseAddress"),
                        rs.getString("userName"),
                        rs.getString("address"),
                        rs.getString("addressDetail"),
                        rs.getString("userPhoneNum")),
                getAddressParams);
    }

    public int createAddress(int userIdx, PostAddressReq postAddressReq){
        String createUserAddressQuery = "insert into Address (userIdx, userName, userPhoneNum, address, addressDetail, IsBaseAddress) \n" +
                "VALUES (?, ?, ?, ?, ?, ?)";
        Object[] createUserAddressParams = new Object[]{userIdx, postAddressReq.getUserName(), postAddressReq.getUserPhoneNum(), postAddressReq.getAddress(), postAddressReq.getAddressDetail(), postAddressReq.getIsBaseAddress()};
        this.jdbcTemplate.update(createUserAddressQuery, createUserAddressParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }


}
