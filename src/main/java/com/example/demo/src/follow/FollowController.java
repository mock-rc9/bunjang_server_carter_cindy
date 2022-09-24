package com.example.demo.src.follow;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.follow.model.GetFollowRes;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/app")
public class FollowController {

    @Autowired
    private final FollowProvider followProvider;

    @Autowired
    private final FollowService followService;

    @Autowired
    private final JwtService jwtService;

    public FollowController(FollowProvider followProvider, FollowService followService, JwtService jwtService){
        this.followProvider = followProvider;
        this.followService = followService;
        this.jwtService = jwtService;
    }

    /**
     * 팔로워 조회 API
     * [GET] /followers
     * @return BaseResponse<List<GetFollowRes>>
     */
    @ResponseBody
    @GetMapping("/followers")
    public BaseResponse<List<GetFollowRes>> getFollowers(){
        try {
            int userIdx = jwtService.getUserIdx();

            List<GetFollowRes> getFollowersRes = followProvider.getFollowers(userIdx);
            return new BaseResponse<>(getFollowersRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 팔로잉 조회 API
     * [GET] /followings
     * @return BaseResponse<List<GetFollowRes>>
     */
    @ResponseBody
    @GetMapping("/followings")
    public BaseResponse<List<GetFollowRes>> getFollowings(){
        try {
            int userIdx = jwtService.getUserIdx();

            List<GetFollowRes> getFollowingsRes = followProvider.getFollowings(userIdx);
            return new BaseResponse<>(getFollowingsRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

}
