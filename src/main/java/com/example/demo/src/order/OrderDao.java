package com.example.demo.src.order;

import com.example.demo.src.order.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class OrderDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int createOrder(int buyerIdx, PostOrderReq postOrderReq){
        String createOrderQuery = "insert into Orders (buyerIdx, goodsIdx, addressIdx, orderDeliveryReq, goodsPrice, orderFee, deliveryFee" +
                ", orderTotalPrice, orderPaymentMethod, orderPaymentBank) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] createOrderParams = new Object[]{buyerIdx, postOrderReq.getGoodsIdx(), postOrderReq.getAddressIdx(), postOrderReq.getOrderDeliveryReq(), postOrderReq.getGoodsPrice()
                , postOrderReq.getOrderFee(), postOrderReq.getDeliveryFee(), postOrderReq.getOrderTotalPrice(), postOrderReq.getOrderPaymentMethod(), postOrderReq.getOrderPaymentBank()};
        this.jdbcTemplate.update(createOrderQuery, createOrderParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

}
