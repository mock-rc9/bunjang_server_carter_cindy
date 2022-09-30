package com.example.demo.src.faq;

import com.example.demo.config.BaseException;
import com.example.demo.src.faq.model.GetFaqRes;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.GET_EMPTY_FAQ;

@Service
public class FaqProvider {

    private final FaqDao faqDao;

    public FaqProvider(FaqDao faqDao) {
        this.faqDao = faqDao;
    }

    public GetFaqRes getfaq(int faqIdx) throws BaseException {
        if(faqDao.checkFaqExits(faqIdx)==0){
            throw new BaseException(GET_EMPTY_FAQ);
        }
        try {
            GetFaqRes getFaqRes = faqDao.getNotice(faqIdx);
            return getFaqRes;
        }catch (Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetFaqRes> getfaqs() throws BaseException {

        try {

            List<GetFaqRes> getFaqRes = faqDao.getFaqs();
            return getFaqRes;
        }
        catch (Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetFaqRes> getfaqsByTitle(String searchTitle) throws BaseException {

        try {

            List<GetFaqRes> getFaqRes = faqDao.getFaqsByTitle(searchTitle);
            return getFaqRes;
        }
         catch (Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkFaqExits(int faqIdx) throws BaseException {
        try{
            return faqDao.checkFaqExits(faqIdx);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }

    }
}
