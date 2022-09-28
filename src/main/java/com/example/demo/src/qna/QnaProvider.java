package com.example.demo.src.qna;

import com.example.demo.config.BaseException;
import com.example.demo.src.qna.model.GetQnaRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@Transactional(readOnly = true)
public class QnaProvider {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final QnaDao qnaDao;

    @Autowired
    public QnaProvider(QnaDao qnaDao){
        this.qnaDao = qnaDao;
    }

    public List<GetQnaRes> getQnaList(int userIdx) throws BaseException {
        try {
            List<GetQnaRes> getQnaRes = qnaDao.getQnaList(userIdx);
            return getQnaRes;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
