package com.example.demo.src.faq;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.faq.model.GetFaqRes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faq")
public class FaqController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final FaqProvider faqProvider;

    @Autowired
    private final FaqService faqService;

    public FaqController(FaqProvider faqProvider, FaqService faqService) {
        this.faqProvider = faqProvider;
        this.faqService = faqService;
    }
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetFaqRes>> getFaqs(@RequestParam(required = false) String searchTitle){

        try {
            if(searchTitle==null){
                List<GetFaqRes> getFaqRes = faqProvider.getfaqs();
                return new BaseResponse<>(getFaqRes);
            }
            List<GetFaqRes> getFaqRes = faqProvider.getfaqsByTitle(searchTitle);
            return new BaseResponse<>(getFaqRes);
        }catch (BaseException exception){
            System.out.println(exception);
            return new BaseResponse<>(exception.getStatus());
        }
    }
    @ResponseBody
    @GetMapping("/{faqIdx}")
    public BaseResponse <GetFaqRes> getFaq(@PathVariable("faqIdx") int faqIdx){

        try {
            GetFaqRes getFaqRes = faqProvider.getfaq(faqIdx);
            return new BaseResponse<>(getFaqRes);
        }catch (BaseException exception){
            System.out.println(exception);
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
