package com.example.demo.src.block;

import com.example.demo.config.BaseException;
import com.example.demo.src.block.model.GetBlockRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;


@Service
public class BlockProvider {
    private final BlockDao blockDao;

    private JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    public BlockProvider(BlockDao blockDao, JwtService jwtService) {
        this.blockDao = blockDao;
        this.jwtService = jwtService;
    }

    public List<GetBlockRes> getBlock(int userIdx) throws BaseException {
        try {

            List<GetBlockRes> getBlockRes = blockDao.getBlocks(userIdx);
            return getBlockRes;
        }
        catch (Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }


    }
    /*유저 확인*/
    public int checkUserExits(int userIdx) throws BaseException{
        try {
            return blockDao.checkUserExist(userIdx);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
