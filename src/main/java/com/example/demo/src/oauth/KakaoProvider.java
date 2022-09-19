package com.example.demo.src.oauth;

import com.example.demo.config.BaseException;
import com.example.demo.src.oauth.model.OAuthLoginRes;
import com.example.demo.src.user.model.User;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KakaoProvider {

    private final UserInfoDao userInfoDao;

    private final JwtService jwtService;

    public int checkEmail(String email) throws BaseException {
        try {
            return userInfoDao.checkEmail(email);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public OAuthLoginRes logIn(String userEmail) throws BaseException {
        // 이메일 유효성 검사
        if(userInfoDao.checkEmail(userEmail) == 0){
            throw new BaseException(FAILED_TO_LOGIN);
        }

        User kakaoUser = userInfoDao.getPwd(userEmail);

        // 유효성 검사
        if(kakaoUser.getUserStatus().equals("inactive")) {
            throw new BaseException(INACTIVE_USER);
        }
        if(kakaoUser.getUserStatus().equals("deleted")) {
            throw new BaseException(DELETED_USER);
        }

        int userIdx = kakaoUser.getUserIdx();
        String jwt = jwtService.createJwt(userIdx);
        return new OAuthLoginRes(userIdx, jwt);
    }


}
