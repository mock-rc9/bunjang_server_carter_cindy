package com.example.demo.src.payment;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.payment.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final PaymentProvider paymentProvider;
    @Autowired
    private final PaymentService paymentService;
    @Autowired
    private final JwtService jwtService;

    public PaymentController(PaymentProvider paymentProvider, PaymentService paymentService, JwtService jwtService) {
        this.paymentProvider = paymentProvider;
        this.paymentService = paymentService;
        this.jwtService = jwtService;

    }

    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostPaymentRes> createPayment(@RequestBody PostPaymentReq postPaymentReq) {


        if(postPaymentReq.getPaymentBank()==null){
            return new BaseResponse<>(POST_PAYMENT_EMPTY_BANK);
        }

        if(postPaymentReq.getAccountHolder()==null){
            return new BaseResponse<>(POST_PAYMENT_EMPTY_ACCOUNTHOLDER);
        }
        if(postPaymentReq.getAccountNum()==null){
            return new BaseResponse<>(POST_PAYMENT_EMPTY_ACCOUNTNUM);
        }

        try {
            int userIdxJwt = jwtService.getUserIdx();
            PostPaymentRes postPaymentRes = paymentService.createPayment(userIdxJwt, postPaymentReq);
            return new BaseResponse<>(postPaymentRes);

        } catch (BaseException exception) {
            System.out.println(exception);
            return new BaseResponse<>(exception.getStatus());
        }
    }


    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetPaymentRes>> getPayment() {


        try {
            int userIdxJwt = jwtService.getUserIdx();

            List<GetPaymentRes> getPaymentRes = paymentProvider.getPayment(userIdxJwt);

            return new BaseResponse<>(getPaymentRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }

    }

    @ResponseBody
    @PatchMapping("/{paymentIdx}")
    public BaseResponse<String> modifyPayment(@PathVariable("paymentIdx") int paymentIdx, @RequestBody PatchPaymentReq patchPaymentReq) {

        if(patchPaymentReq.getPaymentBank()==null){
            return new BaseResponse<>(POST_PAYMENT_EMPTY_BANK);
        }
        if(patchPaymentReq.getAccountHolder()==null){
            return new BaseResponse<>(POST_PAYMENT_EMPTY_ACCOUNTHOLDER);
        }
        if(patchPaymentReq.getAccountNum()==null){
            return new BaseResponse<>(POST_PAYMENT_EMPTY_ACCOUNTNUM);
        }
        try {

            int userIdxJwt = jwtService.getUserIdx();
            paymentService.modifyPayment(userIdxJwt, paymentIdx, patchPaymentReq);
            String result = "????????? ?????????????????????.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            System.out.println(exception);
            return new BaseResponse<>(exception.getStatus());
        }
    }
    @ResponseBody
    @PatchMapping("/{paymentIdx}/status")
    public BaseResponse<String> deletePayment(@PathVariable("paymentIdx") int paymentIdx) {

        try {
            int userIdxJwt = jwtService.getUserIdx();
            paymentService.deletePayment(userIdxJwt, paymentIdx);
            String result = "????????? ?????????????????????.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            System.out.println(exception);
            return new BaseResponse<>(exception.getStatus());
        }
    }
}







