package com.example.demo.src.oauth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class UserInfo {
    private String userEmail;
    private String userPassword;
    private String userNickName;
    private String userImgUrl;
}
