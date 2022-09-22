package com.example.demo.src.order;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.order.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/orders")
public class OrderController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final OrderProvider orderProvider;
    @Autowired
    private final OrderService orderService;
    @Autowired
    private final JwtService jwtService;

    public OrderController(OrderProvider orderProvider, OrderService orderService, JwtService jwtService){
        this.orderProvider = orderProvider;
        this.orderService = orderService;
        this.jwtService = jwtService;
    }


    /**
     * 주문 생성 API
     * [POST] /orders
     * @return BaseResponse<PostOrderRes>
     */
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostOrderRes> createOrder(@RequestBody PostOrderReq postOrderReq){
        try {
            int buyerIdx = jwtService.getUserIdx();

            // 결제 수단 유효성 검사
            if(postOrderReq.getOrderPaymentMethod() == null){
                return new BaseResponse<>(POST_ORDER_EMPTY_ORDERPAYMENTMETHOD);
            }

            PostOrderRes postOrderRes = orderService.createOrder(buyerIdx, postOrderReq);
            return new BaseResponse<>(postOrderRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 주문 상세 내역 조회 API
     * [GET] /orders/:orderIdx
     * @return BaseResponse<GetOrderRes>
     */
    @ResponseBody
    @GetMapping("/{orderIdx}")
    public BaseResponse<GetOrderRes> getOrder(@PathVariable("orderIdx") int orderIdx){
        try {
            jwtService.getUserIdx();
            GetOrderRes getOrderRes = orderProvider.getOrder(orderIdx);
            return new BaseResponse<>(getOrderRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


}
