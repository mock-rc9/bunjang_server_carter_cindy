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
            int followIdx = followDao.follow(followerIdx, followingIdx);
            return new PostFollowRes(followIdx);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void unfollow(int followerIdx, int followingIdx) throws BaseException {
        try {
            int followIdx = followDao.checkFollow(followerIdx, followingIdx);
            int result = followDao.unfollow(followIdx);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_UNFOLLOW);
            }
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
