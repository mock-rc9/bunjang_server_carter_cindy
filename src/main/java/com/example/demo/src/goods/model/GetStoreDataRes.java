package com.example.demo.src.goods.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetStoreDataRes {
    private double score;
    private String userImgUrl;
    private String userNickName;
    private List<GetStoreFollowRes> follow;

}
