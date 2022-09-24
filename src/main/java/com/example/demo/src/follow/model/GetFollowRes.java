package com.example.demo.src.follow.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class GetFollowRes {
    private int userIdx;
    private String userImgUrl;
    private String userNickName;
    private int goodsCount;
    private int followerCount;
    private List<GetGoodsListRes> goodsList;
}
