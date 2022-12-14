package com.example.demo.src.block;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.block.model.GetBlockRes;
import com.example.demo.src.block.model.PostBlockReq;
import com.example.demo.src.block.model.PostBlockRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/block")
public class BlockController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final BlockProvider blockProvider;

    @Autowired
    private final BlockService blockService;

    @Autowired
    private final JwtService jwtService;

    public BlockController(BlockProvider blockProvider, BlockService blockService, JwtService jwtService) {
        this.blockProvider = blockProvider;
        this.blockService = blockService;
        this.jwtService = jwtService;
    }

    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetBlockRes>> getBlock(){

        try {
            int userIdxJwt = jwtService.getUserIdx();
            List<GetBlockRes> getBlockRes = blockProvider.getBlock(userIdxJwt);
            return new BaseResponse<>(getBlockRes);
        }
        catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostBlockRes> createBlock(@RequestBody PostBlockReq postBlockReq){

        try {
            int userIdxJwt = jwtService.getUserIdx();
            PostBlockRes postBlockRes= blockService.createBlock(userIdxJwt,postBlockReq);
            return new BaseResponse<>(postBlockRes);

        } catch (BaseException exception) {
            System.out.println(exception);
            return new BaseResponse<>(exception.getStatus());
        }
    }
    @ResponseBody
    @PatchMapping("/{blockIdx}/status")
    public BaseResponse<String> deleteBlock(@PathVariable("blockIdx")  int blockIdx){

        try {
            int userIdxJwt = jwtService.getUserIdx();
            blockService.deleteBlock(userIdxJwt,blockIdx);
            String result = "?????? ????????? ?????????????????????.";
            return new BaseResponse<>(result);

        } catch (BaseException exception) {
            System.out.println(exception);
            return new BaseResponse<>(exception.getStatus());
        }
    }

}
