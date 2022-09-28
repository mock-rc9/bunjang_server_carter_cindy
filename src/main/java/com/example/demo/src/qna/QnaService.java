package com.example.demo.src.qna;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.demo.config.BaseException;
import com.example.demo.src.qna.model.PostQnaReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
@Transactional
public class QnaService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final QnaDao qnaDao;
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Autowired
    public QnaService(QnaDao qnaDao, AmazonS3 amazonS3){
        this.qnaDao = qnaDao;
        this.amazonS3 = amazonS3;
    }

    public int createQna(int userIdx, PostQnaReq postQnaReq) throws BaseException {
        try{
            int qnaIdx = qnaDao.createQna(userIdx, postQnaReq);
            return qnaIdx;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void createQnaFile(int qnaIdx, List<MultipartFile> multipartFile) throws IOException, BaseException {
        for(int i = 0; i < multipartFile.size(); i++){
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.get(i).getContentType());
            objectMetadata.setContentLength(multipartFile.get(i).getSize());

            String originalFilename = multipartFile.get(i).getOriginalFilename();
            int index = originalFilename.lastIndexOf(".");
            String ext = originalFilename.substring(index + 1);

            String storeFileName = UUID.randomUUID() + "." + ext;
            String key = "qnaImg/" + storeFileName;

            amazonS3.putObject(bucket, key, multipartFile.get(i).getInputStream(), objectMetadata);
            String storeFileUrl = amazonS3.getUrl(bucket, key).toString();

            try {
                int result = qnaDao.createQnaImg(qnaIdx, storeFileUrl);
                if(result == 0){
                    throw new BaseException(CREATE_FAIL_QNAIMG);
                }
            } catch (Exception exception){
                throw new BaseException(DATABASE_ERROR);
            }
        }
    }

}
