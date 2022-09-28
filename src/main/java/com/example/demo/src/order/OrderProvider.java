package com.example.demo.src.order;

import com.example.demo.config.BaseException;
import com.example.demo.src.order.model.GetMyTradesRes;
import com.example.demo.src.order.model.GetOrderGoodsInfoRes;
import com.example.demo.src.order.model.GetOrderRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@Transactional(readOnly = true)
public class OrderProvider {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OrderDao orderDao;
    private final JwtService jwtService;

    @Autowired
    public OrderProvider(OrderDao orderDao, JwtService jwtService){
        this.orderDao = orderDao;
        this.jwtService = jwtService;
    }

    public GetOrderRes getOrder(int orderIdx) throws BaseException {
        try {
            GetOrderRes getOrderRes = orderDao.getOrder(orderIdx);
            return getOrderRes;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetMyTradesRes> getMyOrders(int userIdx) throws BaseException {
        try {
            List<GetMyTradesRes> getMyOrdersRes = orderDao.getMyOrders(userIdx);
            return getMyOrdersRes;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetMyTradesRes> getMyOrdersByStatus(int userIdx, String orderStatus) throws BaseException {
        try {
            List<GetMyTradesRes> getMyOrdersByStatusRes = orderDao.getMyOrdersByStatus(userIdx, orderStatus);
            return getMyOrdersByStatusRes;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetMyTradesRes> getMySales(int userIdx) throws BaseException {
        try {
            List<GetMyTradesRes> getMySalesRes = orderDao.getMySales(userIdx);
            return getMySalesRes;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetMyTradesRes> getMySalesByStatus(int userIdx, String orderStatus) throws BaseException {
        try {
            List<GetMyTradesRes> getMySalesByStatusRes = orderDao.getMySalesByStatus(userIdx, orderStatus);
            return getMySalesByStatusRes;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetOrderGoodsInfoRes getOrderGoodsInfo(int goodsIdx) throws BaseException {
        try {
            GetOrderGoodsInfoRes getOrderGoodsInfoRes = orderDao.getOrderGoodsInfo(goodsIdx);
            return getOrderGoodsInfoRes;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
