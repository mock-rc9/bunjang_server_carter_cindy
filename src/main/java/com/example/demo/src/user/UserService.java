package com.example.demo.src.user;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.demo.config.BaseException;
import com.example.demo.src.user.model.PatchUserReq;
import com.example.demo.src.user.model.PostUserReq;
import com.example.demo.src.user.model.PostUserRes;
import com.example.demo.src.user.model.UploadFile;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
@Transactional
public class UserService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserDao userDao;
    private final UserProvider userProvider;
    private final JwtService jwtService;
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    @Autowired
    public UserService(UserDao userDao, UserProvider userProvider, JwtService jwtService, AmazonS3 amazonS3) {
        this.userDao = userDao;
        this.userProvider = userProvider;
        this.jwtService = jwtService;
        this.amazonS3 = amazonS3;

    }

    //POST
    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
        // 중복된 이메일
        if(userProvider.checkEmail(postUserReq.getUserEmail()) == 1){
            throw new BaseException(DUPLICATED_EMAIL);
        }

        String pwd;
        try{
            // 암호화
            pwd = new SHA256().encrypt(postUserReq.getUserPassword());
            postUserReq.setUserPassword(pwd);

        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        try{
            int userIdx = userDao.createUser(postUserReq);
            // jwt 발급.
            String jwt = jwtService.createJwt(userIdx);
            return new PostUserRes(userIdx, jwt);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void modifyUserInfo(int userIdx, PatchUserReq patchUserReq) throws BaseException {
        try{
            int result = userDao.modifyUserInfo(userIdx, patchUserReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USERINFO);
            }
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void modifyUploadFile(int userIdx, MultipartFile multipartFile) throws IOException, BaseException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());

        String originalFilename = multipartFile.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(index + 1);

        String storeFileName = UUID.randomUUID() + "." + ext;
        String key = "userImg/" +storeFileName;

        amazonS3.putObject(bucket, key, multipartFile.getInputStream(), objectMetadata);
        String storeFileUrl = amazonS3.getUrl(bucket, key).toString();

        UploadFile uploadFile = new UploadFile(originalFilename, storeFileUrl);


//        try {
            int result = userDao.modifyUploadFile(userIdx, uploadFile);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USERINFO);
            }
//        } catch (Exception exception){
//            throw new BaseException(DATABASE_ERROR);
//        }
    }

    public void deleteUser(int userIdx) throws BaseException {
        try {
            int result = userDao.deleteUser(userIdx);
            if(result == 0) {
                throw new BaseException(DELETE_FAIL_USER);
            }
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
