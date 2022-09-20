package com.example.demo.src.goods;


import com.example.demo.src.home.HomeDao;
import com.example.demo.src.home.HomeProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final GoodsProvider goodsProvider;
    private final GoodsDao goodsDao;

    @Autowired
    public GoodsService(GoodsProvider goodsProvider,GoodsDao goodsDao){
        this.goodsDao=goodsDao;
        this.goodsProvider=goodsProvider;
    }


}
