package com.example.demo.src.block;

import com.example.demo.config.BaseException;
import com.example.demo.src.block.model.PostBlockReq;
import com.example.demo.src.block.model.PostBlockRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class BlockService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final BlockDao blockDao;

    private final BlockProvider blockProvider;

    public BlockService(BlockDao blockDao, BlockProvider blockProvider) {
        this.blockDao = blockDao;
        this.blockProvider = blockProvider;
    }

    public void deleteBlock(int userIdx, int patchBlockReq) throws BaseException {
        if(blockProvider.checkUserExits(userIdx)==0){
            throw new BaseException(USERS_EMPTY_USER_ID);
        }
        try{
            int result = blockDao.deleteblock(userIdx);
            if(result==0){
                throw new BaseException(DELETE_FAIL_BLOCK);
            }
        }catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }

    }


    public PostBlockRes createBlock(int userIdx, PostBlockReq postBlockReq) throws BaseException {

        if(blockProvider.checkUserExits(userIdx)==0){
            throw new BaseException(USERS_EMPTY_USER_ID);
        }
        try {
            int result = blockDao.createBlock(userIdx,postBlockReq);
            return new PostBlockRes(result);
        }catch (Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }


    }
}
