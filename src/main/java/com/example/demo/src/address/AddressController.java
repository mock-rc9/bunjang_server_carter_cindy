package com.example.demo.src.address;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.address.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;


@RestController
@RequestMapping("/app/addresses")
public class AddressController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final AddressProvider addressProvider;
    @Autowired
    private final AddressService addressService;
    @Autowired
    private final JwtService jwtService;

    public AddressController(AddressProvider addressProvider, AddressService addressService, JwtService jwtService){
        this.addressProvider = addressProvider;
        this.addressService = addressService;
        this.jwtService = jwtService;
    }

    /**
     * 특정 유저 전체 주소 조회 API
     * [GET] /addresses
     * @return BaseResponse<List<GetAddressRes>>
     */
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetAddressRes>> getAddresses(){
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            List<GetAddressRes> getAddressRes = addressProvider.getAddresses(userIdxByJwt);
            return new BaseResponse<>(getAddressRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 유저 주소 생성 API(새 주소 추가)
     * [POST] /addresses
     * @return BaseResponse<PostAddressRes>
     */
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostAddressRes> createUserAddress(@RequestBody PostAddressReq postAddressReq){
        // 이름 유효성 검사
        if(postAddressReq.getUserName() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_NAME);
        }
        // 휴대폰번호 유효성 검사
        if(postAddressReq.getUserPhoneNum() == null){
            return new BaseResponse<>(POST_ADDRESS_EMPTY_PHONENUM);
        }
        if(!(postAddressReq.getUserPhoneNum().length() == 10 || postAddressReq.getUserPhoneNum().length() == 11)){
            return new BaseResponse<>(POST_ADDRESS_INVALID_PHONENUM);
        }
        // 주소 유효성 검사
        if(postAddressReq.getAddress() == null){
            return new BaseResponse<>(POST_ADDRESS_EMPTY_ADDRESS);
        }
        // 상세 주소 유효성 검사
        if(postAddressReq.getAddressDetail() == null){
            return new BaseResponse<>(POST_ADDRESS_EMPTY_ADDRESSDETAIL);
        }

        if(postAddressReq.getIsBaseAddress() == null){
            postAddressReq.setIsBaseAddress("N");
        }

        try{
            int userIdxByJwt = jwtService.getUserIdx();
            PostAddressRes postAddressRes = addressService.createAddress(userIdxByJwt, postAddressReq);
            return new BaseResponse<>(postAddressRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

}
