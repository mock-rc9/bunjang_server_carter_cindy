package com.example.demo.src.qna;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.qna.model.PostQnaReq;
import com.example.demo.src.qna.model.PostQnaRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/qnas")
public class QnaController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final QnaProvider qnaProvider;

    @Autowired
    private final QnaService qnaService;

    @Autowired
    private JwtService jwtService;

    public QnaController(QnaProvider qnaProvider, QnaService qnaService, JwtService jwtService){
        this.qnaProvider = qnaProvider;
        this.qnaService = qnaService;
        this.jwtService = jwtService;
    }

    /**
     * 문의글 업로드 API
     * [POST] /qnas
     * @return BaseResponse<PostQnaRes>
     */
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostQnaRes> createQna(@RequestPart(value = "multipartFiles", required = false) List<MultipartFile> multipartFiles, @RequestPart(value = "postQnaReq") PostQnaReq postQnaReq) throws IOException{
        // 문의 유효성 검사
        if(postQnaReq.getQnaCategory() == null){
            return new BaseResponse<>(POST_QNA_EMPTY_CATEGORY);
        }
        if(postQnaReq.getQnaContent() == null){
            return new BaseResponse<>(POST_QNA_EMPTY_CONTENT);
        }

        try{
            int userIdx = jwtService.getUserIdx();
            int qnaIdx = qnaService.createQna(userIdx, postQnaReq);
            if(multipartFiles != null && !multipartFiles.isEmpty()){
                qnaService.createQnaFile(qnaIdx, multipartFiles);
            }
            PostQnaRes postQnaRes = new PostQnaRes(qnaIdx);
            return new BaseResponse<>(postQnaRes);
        } catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

}
