package com.example.demo.src.follow;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.follow.model.PostFollowRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
@Transactional
public class FollowService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final FollowDao followDao;

    @Autowired
    public FollowService(FollowDao followDao){
        this.followDao = followDao;
    }

    public PostFollowRes follow(int followerIdx, int followingIdx) throws BaseException {
        try {
            // 이전에 비활성화나 언팔로우 또는 팔로우를 했던 유저인 경우
            if(followDao.checkNotFollow(followerIdx, followingIdx) == 1){
                int followIdx = followDao.getFollowIdx(followerIdx, followingIdx);
                // 새로 생성하지 않고 status 값을 active로 변경
                int result = followDao.modifyFollowStatus(followIdx);
                if(result == 0){
                    throw new BaseException(MODIFY_FAIL_FOLLOW);
                }
                return new PostFollowRes(followIdx);
            }
            int followIdx = followDao.follow(followerIdx, followingIdx);
            return new PostFollowRes(followIdx);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void unfollow(int followerIdx, int followingIdx) throws BaseException {
        // 팔로우를 하지 않은 유저에 대해 언팔로우 요청을 할 경우
        if(followDao.checkFollow(followerIdx, followingIdx) == 0){
            throw new BaseException(FAILED_TO_UNFOLLOW);
        }
        try {
            int followIdx = followDao.getFollowIdx(followerIdx, followingIdx);
            int result = followDao.unfollow(followIdx);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_UNFOLLOW);
            }
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
