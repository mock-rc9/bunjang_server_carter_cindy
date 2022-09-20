package com.example.demo.src.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final HomeProvider homeProvider;

    private final HomeDao homeDao;

    @Autowired
    public HomeService(HomeDao homeDao,HomeProvider homeProvider){
        this.homeDao = homeDao;
        this.homeProvider= homeProvider;
    }


}