package com.example.demo.src.order;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.order.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app")
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
    @PostMapping("/orders")
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
    @GetMapping("/orders/{orderIdx}")
    public BaseResponse<GetOrderRes> getOrder(@PathVariable("orderIdx") int orderIdx){
        try {
            jwtService.getUserIdx();
            GetOrderRes getOrderRes = orderProvider.getOrder(orderIdx);
            return new BaseResponse<>(getOrderRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 주문 취소 API
     * [DELETE] /orders/:orderIdx
     * @return
     */
    @ResponseBody
    @DeleteMapping("/orders/{orderIdx}")
    public BaseResponse<String> deleteOrder(@PathVariable("orderIdx") int orderIdx){
        try {
            jwtService.getUserIdx();

            orderService.deleteOrder(orderIdx);

            String result = "주문이 취소되었습니다";
            return new BaseResponse<>(result);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 구매 내역 조회 API
     * [GET] /myorders
     * @return BaseResponse<List<GetMyTradesRes>>
     */
    @ResponseBody
    @GetMapping("/myorders")
    public BaseResponse<List<GetMyTradesRes>> getMyOrders(@RequestParam(required = false) String orderStatus) {
        try {
            int userIdx = jwtService.getUserIdx();

            if(orderStatus == null){
                List<GetMyTradesRes> getMyTradesRes = orderProvider.getMyOrders(userIdx);
                return new BaseResponse<>(getMyTradesRes);
            }

            List<GetMyTradesRes> getMyTradesRes = orderProvider.getMyOrdersByStatus(userIdx, orderStatus);
            return new BaseResponse<>(getMyTradesRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 판매 내역 조회 API
     * [GET] /mysales
     * @return BaseResponse<List<GetMyTradesRes>>
     */
    @ResponseBody
    @GetMapping("/mysales")
    public BaseResponse<List<GetMyTradesRes>> getMySales(@RequestParam(required = false) String orderStatus) {
        try {
            int userIdx = jwtService.getUserIdx();

            if(orderStatus == null){
                List<GetMyTradesRes> getMyTradesRes = orderProvider.getMySales(userIdx);
                return new BaseResponse<>(getMyTradesRes);
            }

            List<GetMyTradesRes> getMyTradesRes = orderProvider.getMySalesByStatus(userIdx, orderStatus);
            return new BaseResponse<>(getMyTradesRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 주문시 상품 정보 조회 API
     * [GET] /app/carts/:goodsIdx
     * @return BaseResponse<GetOrderGoodsInfoRes>
     */
    @ResponseBody
    @GetMapping("/carts/{goodsIdx}")
    public BaseResponse<GetOrderGoodsInfoRes> getOrderGoodsInfo(@PathVariable("goodsIdx") int goodsIdx) {
        try {
            jwtService.getUserIdx();

            GetOrderGoodsInfoRes getOrderGoodsInfoRes = orderProvider.getOrderGoodsInfo(goodsIdx);
            return new BaseResponse<>(getOrderGoodsInfoRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


}
