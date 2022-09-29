package com.example.demo.src.order;

import com.example.demo.src.order.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

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
                "    , A.addressIdx as addressIdx, userName, A.userPhoneNum as userPhoneNum\n" +
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

    public List<GetMyTradesRes> getMyOrders(int userIdx) {
        String getMyOrdersQuery = "select orderIdx, orderStatus, goodsName\n" +
                "     , CONCAT(G.goodsPrice, '원') as goodsPrice, userNickName\n" +
                "     , REPLACE(\n" +
                "            REPLACE(\n" +
                "                DATE_FORMAT(orderCreatedAt, '%Y.%m.%d (%p %h:%i)'), 'AM', '오전'\n" +
                "                ), 'PM', '오후') as orderTime\n" +
                "    , (select COUNT(orderIdx)\n" +
                "        from Review\n" +
                "        where Review.orderIdx = Orders.orderIdx) as isReview\n" +
                "from Orders\n" +
                "inner join Goods G on Orders.goodsIdx = G.goodsIdx\n" +
                "inner join User U on G.userIdx = U.userIdx\n" +
                "where buyerIdx = ?";
        int getMyOrdersParams = userIdx;

        return this.jdbcTemplate.query(getMyOrdersQuery,
                (rs, rsNum) -> new GetMyTradesRes(
                        rs.getInt("orderIdx"),
                        rs.getString("orderStatus"),
                        rs.getString("goodsName"),
                        rs.getString("goodsPrice"),
                        rs.getString("userNickName"),
                        rs.getString("orderTime"),
                        rs.getInt("isReview")),
                getMyOrdersParams);
    }

    public List<GetMyTradesRes> getMySales(int userIdx) {
        String getMySalesQuery = "select orderIdx, orderStatus, goodsName\n" +
                "     , CONCAT(G.goodsPrice, '원') as goodsPrice, userNickName\n" +
                "     , REPLACE(\n" +
                "            REPLACE(\n" +
                "                DATE_FORMAT(orderCreatedAt, '%Y.%m.%d (%p %h:%i)'), 'AM', '오전'\n" +
                "                ), 'PM', '오후') as orderTime\n" +
                "    , (select COUNT(orderIdx)\n" +
                "        from Review\n" +
                "        where Review.orderIdx = Orders.orderIdx) as isReview\n" +
                "from Orders\n" +
                "inner join Goods G on Orders.goodsIdx = G.goodsIdx\n" +
                "inner join User U on Orders.buyerIdx = U.userIdx\n" +
                "where G.userIdx = ?";
        int getMySalesParams = userIdx;

        return this.jdbcTemplate.query(getMySalesQuery,
                (rs, rsNum) -> new GetMyTradesRes(
                        rs.getInt("orderIdx"),
                        rs.getString("orderStatus"),
                        rs.getString("goodsName"),
                        rs.getString("goodsPrice"),
                        rs.getString("userNickName"),
                        rs.getString("orderTime"),
                        rs.getInt("isReview")),
                getMySalesParams);
    }

    public List<GetMyTradesRes> getMyOrdersByStatus(int userIdx, String orderStatus) {
        String getMyOrdersByStatusQuery = "select orderIdx, orderStatus, goodsName\n" +
                "     , CONCAT(G.goodsPrice, '원') as goodsPrice, userNickName\n" +
                "     , REPLACE(\n" +
                "            REPLACE(\n" +
                "                DATE_FORMAT(orderCreatedAt, '%Y.%m.%d (%p %h:%i)'), 'AM', '오전'\n" +
                "                ), 'PM', '오후') as orderTime\n" +
                "    , (select COUNT(orderIdx)\n" +
                "        from Review\n" +
                "        where Review.orderIdx = Orders.orderIdx) as isReview\n" +
                "from Orders\n" +
                "inner join Goods G on Orders.goodsIdx = G.goodsIdx\n" +
                "inner join User U on G.userIdx = U.userIdx\n" +
                "where buyerIdx = ? and orderStatus = ?";
        int getMyOrdersByStatusParams1 = userIdx;
        String getMyOrdersByStatusParams2 = orderStatus;

        return this.jdbcTemplate.query(getMyOrdersByStatusQuery,
                (rs, rsNum) -> new GetMyTradesRes(
                        rs.getInt("orderIdx"),
                        rs.getString("orderStatus"),
                        rs.getString("goodsName"),
                        rs.getString("goodsPrice"),
                        rs.getString("userNickName"),
                        rs.getString("orderTime"),
                        rs.getInt("isReview")),
                getMyOrdersByStatusParams1, getMyOrdersByStatusParams2);
    }

    public List<GetMyTradesRes> getMySalesByStatus(int userIdx, String orderStatus) {
        String getMySalesByStatusQuery = "select orderIdx, orderStatus, goodsName\n" +
                "     , CONCAT(G.goodsPrice, '원') as goodsPrice, userNickName\n" +
                "     , REPLACE(\n" +
                "            REPLACE(\n" +
                "                DATE_FORMAT(orderCreatedAt, '%Y.%m.%d (%p %h:%i)'), 'AM', '오전'\n" +
                "                ), 'PM', '오후') as orderTime\n" +
                "    , (select COUNT(orderIdx)\n" +
                "        from Review\n" +
                "        where Review.orderIdx = Orders.orderIdx) as isReview\n" +
                "from Orders\n" +
                "inner join Goods G on Orders.goodsIdx = G.goodsIdx\n" +
                "inner join User U on Orders.buyerIdx = U.userIdx\n" +
                "where G.userIdx = ? and orderStatus = ?";
        int getMySalesByStatusParams1 = userIdx;
        String getMySalesByStatusParams2 = orderStatus;

        return this.jdbcTemplate.query(getMySalesByStatusQuery,
                (rs, rsNum) -> new GetMyTradesRes(
                        rs.getInt("orderIdx"),
                        rs.getString("orderStatus"),
                        rs.getString("goodsName"),
                        rs.getString("goodsPrice"),
                        rs.getString("userNickName"),
                        rs.getString("orderTime"),
                        rs.getInt("isReview")),
                getMySalesByStatusParams1, getMySalesByStatusParams2);
    }

    public GetOrderGoodsInfoRes getOrderGoodsInfo(int userIdx){
        String getOrderGoodsInfoQuery = "select goodsName\n" +
                "     , (select goodsImgUrl\n" +
                "        from GoodsImg\n" +
                "        where goodsIdx = goodsIdx\n" +
                "        limit 1) as goodsImgUrl, goodsPrice\n" +
                "from Goods\n" +
                "where goodsIdx = ?";
        int getOrderGoodsInfoParams = userIdx;

        return this.jdbcTemplate.queryForObject(getOrderGoodsInfoQuery,
                (rs, rsNum) -> new GetOrderGoodsInfoRes(
                        rs.getString("goodsName"),
                        rs.getString("goodsImgUrl"),
                        rs.getInt("goodsPrice")),
                getOrderGoodsInfoParams);
    }

}
