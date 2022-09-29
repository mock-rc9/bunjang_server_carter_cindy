package com.example.demo.src.payment;


import com.example.demo.config.BaseException;
import com.example.demo.src.payment.model.GetPaymentRes;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.USERS_EMPTY_USER_ID;

@Service
public class PaymentProvider {

    private final PaymentDao paymentDao;
    private final JwtService jwtService;

    @Autowired
    public PaymentProvider(PaymentDao paymentDao, JwtService jwtService) {
        this.paymentDao = paymentDao;
        this.jwtService = jwtService;
    }

    public List<GetPaymentRes> getPayment(int userIdx) throws BaseException {

        if(checkUserExits(userIdx)==0){
            throw new BaseException(USERS_EMPTY_USER_ID);
        }

        try {
            List<GetPaymentRes> getPaymentRes = paymentDao.getPayment(userIdx);
            return getPaymentRes;
        }
        catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkUserExits(int userIdx) throws BaseException {
        try {
            return paymentDao.checkUserExist(userIdx);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }

    }

    public int checkPaymentExits(int paymentIdx) throws BaseException {
        try{
            return paymentDao.checkPaymentExist(paymentIdx);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkUserPaymentExist(int userIdx, int paymentIdx) throws BaseException {
        try {
            return paymentDao.checkUserGoodsExist(userIdx,paymentIdx);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
