package com.example.demo.src.notice;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.notice.model.GetNoticeRes;
import com.example.demo.src.storelike.model.GetStoreLikeRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notices")
public class NoticeController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final NoticeProvider noticeProvider;

    @Autowired
    private final NoticeService noticeService;


    public NoticeController(NoticeProvider noticeProvider, NoticeService noticeService) {
        this.noticeProvider = noticeProvider;
        this.noticeService = noticeService;
    }

    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetNoticeRes>> getStoreNotices(){

        try {
            List<GetNoticeRes> getNoticeRes = noticeProvider.getNotices();
            return new BaseResponse<>(getNoticeRes);
        }catch (BaseException exception){
            System.out.println(exception);
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("/{noticeIdx}")
    public BaseResponse <GetNoticeRes> getStoreNotice(@RequestParam (required = false) int noticeIdx){

        try {
            GetNoticeRes getNoticeRes = noticeProvider.getNotice(noticeIdx);
            return new BaseResponse<>(getNoticeRes);
        }catch (BaseException exception){
            System.out.println(exception);
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
