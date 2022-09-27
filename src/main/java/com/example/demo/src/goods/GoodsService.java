package com.example.demo.src.goods;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.demo.config.BaseException;
import com.example.demo.src.goods.model.PatchGoodsReq;
import com.example.demo.src.goods.model.PostGoodsReq;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class GoodsService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());



    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    private final GoodsProvider goodsProvider;
    private final GoodsDao goodsDao;


    public String saveUploadFile(MultipartFile multipartFile) throws IOException {
        String s3FileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(multipartFile.getInputStream().available());

        amazonS3.putObject(bucket, s3FileName, multipartFile.getInputStream(), objMeta);

        return amazonS3.getUrl(bucket, s3FileName).toString();
    }




        public String createGoods(int userIdx, PostGoodsReq postGoodsReq, MultipartFile multipartFile) throws BaseException {



        try{

            int goodsIdx = goodsDao.createGoods(userIdx,postGoodsReq);

            String storeFileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
            String key = "goodsImg/" + storeFileName;
            ObjectMetadata objMeta = new ObjectMetadata();
            objMeta.setContentLength(multipartFile.getInputStream().available());
            amazonS3.putObject(bucket, key, multipartFile.getInputStream(), objMeta);

            for(int i=0;i<postGoodsReq.getTags().size();i++) {
                goodsDao.createTag(goodsIdx, postGoodsReq.getTags().get(i));
            }
            return amazonS3.getUrl(bucket, key).toString();
        }catch (Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }

    }

    /*상품 설명 수정*/
    public void modifyGoods(int userIdx, int goodsIdx, PatchGoodsReq patchGoodsReq) throws BaseException {

        if(goodsProvider.checkUserExits(userIdx)==0){
            throw new BaseException(USERS_EMPTY_USER_ID);
        }
        if(goodsProvider.checkGoodsExits(goodsIdx)==0){
            throw new BaseException(GOODS_EMPTY_GOODS_ID);
        }
        if(goodsProvider.checkUserGoodsExist(userIdx, goodsIdx)==0){
            throw new BaseException(GOODS_EMPTY_USER_GOODS);
        }
        try {
            int result = goodsDao.updateGoods(goodsIdx,patchGoodsReq);
            if(result==0){
                throw new BaseException(MODIFY_FAIL_GOODS);
            }
        }catch (Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void deleteGoods(int userIdx, int goodsIdx) throws BaseException {

        if(goodsProvider.checkUserExits(userIdx)==0){
            throw new BaseException(USERS_EMPTY_USER_ID);
        }
        if(goodsProvider.checkGoodsExits(goodsIdx)==0){
            throw new BaseException(GOODS_EMPTY_GOODS_ID);
        }
        if(goodsProvider.checkUserGoodsExist(userIdx, goodsIdx)==0){
            throw new BaseException(GOODS_EMPTY_USER_GOODS);
        }
        try{
            int result = goodsDao.deleteGoods(goodsIdx);
            if(result==0){
                throw new BaseException(DELETE_FAIL_GOODS);
            }

        }catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }

    }


}
