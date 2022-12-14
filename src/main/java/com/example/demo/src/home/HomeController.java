package com.example.demo.src.home;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.home.model.GetHomeRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final HomeProvider homeProvider;
    @Autowired
    private final HomeService homeService;
    @Autowired
    private final JwtService jwtService;




    public HomeController(HomeProvider homeProvider, HomeService homeService, JwtService jwtService){
        this.homeProvider = homeProvider;
        this.homeService = homeService;
        this.jwtService = jwtService;
    }



    @ResponseBody
    @GetMapping("")
    public BaseResponse <GetHomeRes> getHome(){

        try {
            int userIdxJwt = jwtService.getUserIdx();
            GetHomeRes getHomeRes = homeProvider.getHome(userIdxJwt);
            return new BaseResponse<>(getHomeRes);
        }
        catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


}