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

    public GetOrderRes getOrder(int orderIdx){
        String getOrderQuery = "select G.goodsIdx as goodsIdx, orderStatus, G.goodsPrice as goodsPrice, goodsName\n" +
                "     , REPLACE(SUBSTRING(orderCreatedAt, 1, 19), '-', '/') as orderTime\n" +
                "     , userNickName as sellerName, orderPaymentMethod, orderPaymentBank, orderTotalPrice\n" +
                "     , Orders.goodsPrice as orderGoodsPrice, orderFee, deliveryFee\n" +
                "    , A.addressIdx as addressIdx, userName, userPhoneNum\n" +
                "     , CONCAT(address, ' ', addressDetail) as address, orderDeliveryReq\n" +
                "from Orders\n" +
                "inner join User U on Orders.buyerIdx = U.userIdx\n" +
                "inner join Goods G on Orders.goodsIdx = G.goodsIdx\n" +
                "inner join Address A on Orders.addressIdx = A.addressIdx\n" +
                "where orderIdx = ?";
        int getOrderParams = orderIdx;
        return this.jdbcTemplate.queryForObject(getOrderQuery,
                (rs, rsNum) -> new GetOrderRes(
                        rs.getInt("goodsIdx"),
                        rs.getString("orderStatus"),
                        rs.getInt("goodsPrice"),
                        rs.getString("goodsName"),
                        rs.getString("orderTime"),
                        rs.getString("sellerName"),
                        rs.getString("orderPaymentMethod"),
                        rs.getString("orderPaymentBank"),
                        rs.getInt("orderTotalPrice"),
                        rs.getInt("orderGoodsPrice"),
                        rs.getInt("orderFee"),
                        rs.getInt("deliveryFee"),
                        rs.getInt("addressIdx"),
                        rs.getString("userName"),
                        rs.getString("userPhoneNum"),
                        rs.getString("address"),
                        rs.getString("orderDeliveryReq")),
                getOrderParams);
    }

    public int deleteOrder(int orderIdx){
        String deleteOrderQuery = "update Orders set orderStatus = 'deleted' where orderIdx = ?";
        int deleteOrderParams = orderIdx;
        return this.jdbcTemplate.update(deleteOrderQuery, deleteOrderParams);
    }

}
