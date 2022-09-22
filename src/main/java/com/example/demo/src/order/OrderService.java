package com.example.demo.src.order;

import com.example.demo.config.BaseException;
import com.example.demo.src.order.model.PostOrderReq;
import com.example.demo.src.order.model.PostOrderRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
@Transactional
public class OrderService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OrderDao orderDao;

    @Autowired
    public OrderService(OrderDao orderDao){
        this.orderDao = orderDao;
    }

    public PostOrderRes createOrder(int buyerIdx, PostOrderReq postOrderReq) throws BaseException {
        try {
            int orderIdx = orderDao.createOrder(buyerIdx, postOrderReq);
            return new PostOrderRes(orderIdx);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void deleteOrder(int orderIdx) throws BaseException {
        try {
            int result = orderDao.deleteOrder(orderIdx);
            if(result == 0){
                throw new BaseException(DELETE_FAIL_ORDER);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
