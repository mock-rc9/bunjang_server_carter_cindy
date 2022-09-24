package com.example.demo.src.oauth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SocialUserInfo {
    private long id; // id
    private String nickname; // profile_nickname
    private String email; // account_email
}
