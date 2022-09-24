package com.example.demo.src.follow;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.follow.model.GetFollowRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@Transactional(readOnly = true)
public class FollowProvider {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final FollowDao followDao;

    @Autowired
    public FollowProvider(FollowDao followDao){
        this.followDao = followDao;
    }

    public List<GetFollowRes> getFollowers(int userIdx) throws BaseException {
//        try {
            List<GetFollowRes> getFollowers = followDao.getFollowers(userIdx);
            return getFollowers;
//        } catch (Exception exception){
//            throw new BaseException(DATABASE_ERROR);
//        }
    }

    public List<GetFollowRes> getFollowings(int userIdx) throws BaseException {
//        try {
        List<GetFollowRes> getFollowings = followDao.getFollowings(userIdx);
        return getFollowings;
//        } catch (Exception exception){
//            throw new BaseException(DATABASE_ERROR);
//        }
    }


}
