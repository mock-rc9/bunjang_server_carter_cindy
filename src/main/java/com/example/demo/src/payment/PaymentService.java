package com.example.demo.src.payment;

import com.example.demo.config.BaseException;
import com.example.demo.src.goods.GoodsDao;
import com.example.demo.src.goods.GoodsProvider;
import com.example.demo.src.payment.model.PatchPaymentReq;
import com.example.demo.src.payment.model.PostPaymentReq;
import com.example.demo.src.payment.model.PostPaymentRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;


@Service
public class PaymentService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PaymentProvider paymentProvider;
    private final PaymentDao paymentDao;

    @Autowired
    public PaymentService(PaymentProvider paymentProvider, PaymentDao paymentDao){
        this.paymentDao = paymentDao;
        this.paymentProvider = paymentProvider;
    }



    public PostPaymentRes createPayment(int userIdx, PostPaymentReq postPaymentReq) throws BaseException {

        try {
            int paymentIdx = paymentDao.createPayment(userIdx,postPaymentReq);
            return new PostPaymentRes(paymentIdx);
        }catch (Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void modifyPayment(int userIdx, int paymentIdx, PatchPaymentReq patchPaymentReq) throws BaseException {

        if(paymentProvider.checkUserExits(userIdx)==0){
            throw new BaseException(USERS_EMPTY_USER_ID);
        }
        if(paymentProvider.checkPaymentExits(paymentIdx)==0){
            throw new BaseException(USERS_EMPTY_USER_ID);
        }
        if(paymentProvider.checkUserPaymentExist(userIdx,paymentIdx)==0){
            throw new BaseException(USERS_EMPTY_USER_ID);
        }
        try {
            int result = paymentDao.updatePayment(paymentIdx,patchPaymentReq);
            if(result==0){
                throw new BaseException(MODIFY_FAIL_PAYMENT);
            }
        }
        catch (Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }

    }
}
