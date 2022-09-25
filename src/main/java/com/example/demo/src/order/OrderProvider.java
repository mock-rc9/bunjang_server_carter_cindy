package com.example.demo.src.order;

import com.example.demo.config.BaseException;
import com.example.demo.src.order.model.GetMyTradesRes;
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

    public List<GetMyTradesRes> getMyAllTrades(int type, int userIdx) throws BaseException {
        try {
            if (type == 1) { // 판매 내역
                List<GetMyTradesRes> MyAllSellerTrades = orderDao.getMyAllSellerTrades(userIdx);
                return MyAllSellerTrades;
            } else { // 구매 내역
                List<GetMyTradesRes> getMyAllBuyTrades = orderDao.getMyAllBuyTrades(userIdx);
                return getMyAllBuyTrades;
            }
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetMyTradesRes> getMyTrades(int type, int userIdx, String orderStatus) throws BaseException {
        try {
            if (type == 1) { // 판매 내역
                List<GetMyTradesRes> MySellerTrades = orderDao.getMySellerTrades(userIdx, orderStatus);
                return MySellerTrades;
            } else { // 구매 내역
                List<GetMyTradesRes> getMyBuyTrades = orderDao.getMyBuyTrades(userIdx, orderStatus);
                return getMyBuyTrades;
            }
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
