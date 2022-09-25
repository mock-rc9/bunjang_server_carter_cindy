package com.example.demo.src.follow;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.follow.model.GetFollowRes;
import com.example.demo.src.follow.model.PostFollowRes;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 팔로우 API
     * [POST] /follows/:followingIdx
     * @return BaseResponse<PostFollowRes>
     */
    @ResponseBody
    @PostMapping("/follows/{followingIdx}")
    public BaseResponse<PostFollowRes> follow(@PathVariable("followingIdx") int followingIdx){
        try {
            int followerIdx = jwtService.getUserIdx();

            PostFollowRes postFollowRes = followService.follow(followerIdx, followingIdx);
            return new BaseResponse<>(postFollowRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 팔로우 취소 API
     * [PATCH] /follows/:followingIdx
     * @return
     */
    @ResponseBody
    @PatchMapping("/follows/{followingIdx}")
    public BaseResponse<String> unfollow(@PathVariable("followingIdx") int followingIdx){
        try {
            int followerIdx = jwtService.getUserIdx();

            followService.unfollow(followerIdx, followingIdx);
            String result = "팔로우가 취소되었습니다";
            return new BaseResponse<>(result);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

}
