package com.example.demo.src.notice;

import com.example.demo.config.BaseException;
import com.example.demo.src.notice.model.GetNoticeRes;

import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.GET_EMPTY_NOTICE;

@Service
public class NoticeProvider {


    private final NoticeDao noticeDao;

    public NoticeProvider(NoticeDao noticeDao) {
        this.noticeDao = noticeDao;
    }


    public List<GetNoticeRes> getNotices() throws BaseException {
        try {


            List<GetNoticeRes> getNoticeRes = noticeDao.getNotices();
            return getNoticeRes;
        }catch (Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetNoticeRes getNotice(int noticeIdx) throws BaseException {

        if(noticeDao.checkNoticeExits(noticeIdx)==0){
            throw new BaseException(GET_EMPTY_NOTICE);
        }
        try {
        GetNoticeRes getNoticeRes = noticeDao.getNotice(noticeIdx);
        return getNoticeRes;
    }catch (Exception exception){
        System.out.println(exception);
        throw new BaseException(DATABASE_ERROR);
    }
    }
    public int checkNoticeExits(int noticeIdx) throws BaseException {
        try{
            return noticeDao.checkNoticeExits(noticeIdx);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }

    }
}
