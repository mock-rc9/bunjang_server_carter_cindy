package com.example.demo.src.payment;


import com.example.demo.src.goods.model.GetGoodsImgRes;
import com.example.demo.src.goods.model.GetStoreGoodsRes;
import com.example.demo.src.payment.model.GetPaymentRes;
import com.example.demo.src.payment.model.PatchPaymentReq;
import com.example.demo.src.payment.model.PostPaymentReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
public class PaymentDao {

    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int createPayment(int userIdx, PostPaymentReq postPaymentReq) {
        String createPaymentQuery ="insert into Payment (" +
                "userIdx" +
                ",accountHolder" +
                ",paymentBank" +
                ",accountNum)VALUES (?,?,?,?)";
        Object[] createPaymentParams = new Object[]{userIdx,postPaymentReq.getAccountHolder(),postPaymentReq.getPaymentBank(),postPaymentReq.getAccountNum()};
        this.jdbcTemplate.update(createPaymentQuery, createPaymentParams);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    public List<GetPaymentRes> getPayment(int userIdx) {
        String getPaymentQuery ="select * from Payment where userIdx=?";
        int getPaymentparams = userIdx;
        return this.jdbcTemplate.query(getPaymentQuery,
                (rs,rowNum)->new GetPaymentRes(
                        rs.getInt("userIdx"),
                        rs.getString("accountHolder"),
                        rs.getString("paymentBank"),
                        rs.getInt("accountNum")),getPaymentparams
        );
    }

    public int updatePayment(int paymentIdx, PatchPaymentReq patchPaymentReq) {
        String updatePaymentQuery = "UPDATE Payment SET accountHolder=?, paymentBank=?,accountNum=? WHERE paymentIdx = ?";

        Object[] updatePaymentParams = new Object[]{patchPaymentReq.getAccountHolder(),patchPaymentReq.getPaymentBank(),patchPaymentReq.getAccountNum(),paymentIdx};
        return this.jdbcTemplate.update(updatePaymentQuery,updatePaymentParams);
    }

    public int checkUserExist(int userIdx) {
        String checkUserExistQuery = "select exists(select userIdx from User where userIdx = ?)";
        int checkUserExistParams = userIdx;
        return this.jdbcTemplate.queryForObject(checkUserExistQuery,
                int.class,
                checkUserExistParams);
    }

    public int checkPaymentExist(int paymentIdx) {
        String checkPaymentExistQuery = "select exists(select paymentIdx from Payment where paymentIdx = ?)";
        int checkPaymentExistParams = paymentIdx;
        return this.jdbcTemplate.queryForObject(checkPaymentExistQuery,
                int.class,
                checkPaymentExistParams);
    }

    public int checkUserGoodsExist(int userIdx, int paymentIdx) {
        String checkPaymentExistQuery = "select exists(select paymentIdx from Payment where paymentIdx = ? and userIdx=?) ";
        Object[]  checkPaymentExistParams = new Object[]{paymentIdx,userIdx};
        return this.jdbcTemplate.queryForObject(checkPaymentExistQuery,
                int.class,
                checkPaymentExistParams);
    }
}
