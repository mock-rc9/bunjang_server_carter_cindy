package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private int userIdx;
    private String userEmail;
    private String userPassword;
    private String userNickName;
    private String userImgUrl;
    private String userStatus;
}
