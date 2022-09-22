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

        try {
            int userIdxJwt = jwtService.getUserIdx();
            paymentService.modifyPayment(userIdxJwt, paymentIdx, patchPaymentReq);
            String result = "수정을 완료하였습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            System.out.println(exception);
            return new BaseResponse<>(exception.getStatus());
        }
    }
}







