package com.example.demo.src.faq;

import com.example.demo.config.BaseException;
import com.example.demo.src.faq.model.GetFaqRes;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class FaqProvider {

    private final FaqDao faqDao;

    public FaqProvider(FaqDao faqDao) {
        this.faqDao = faqDao;
    }

    public GetFaqRes getfaq(int faqIdx) throws BaseException {
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
}
