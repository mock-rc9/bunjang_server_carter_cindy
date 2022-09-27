package com.example.demo.src.storelike;

import com.example.demo.config.BaseException;
import com.example.demo.src.storelike.model.GetStoreLikeRes;
import com.example.demo.utils.JwtService;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class StoreLikeProvider {




    private final StoreLikeDao storeLikeDao;

    private final JwtService jwtService;

    public StoreLikeProvider(StoreLikeDao storeLikeDao, JwtService jwtService) {
        this.storeLikeDao = storeLikeDao;
        this.jwtService = jwtService;
    }

    public List<GetStoreLikeRes> getStoreLikes(int userIdx) throws BaseException {



        try {
            List<GetStoreLikeRes> getStoreLikeRes = storeLikeDao.getStoreLike(userIdx);
            return getStoreLikeRes;
        }
        catch (Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
