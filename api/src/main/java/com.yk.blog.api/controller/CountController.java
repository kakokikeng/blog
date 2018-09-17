package com.yk.blog.api.controller;

import com.yk.blog.core.dto.Result;
import com.yk.blog.core.service.CountService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author yikang
 * @date 2018/9/4
 */
@RestController
@RequestMapping("count")
public class CountController {

    @Autowired
    CountService countService;

    @ApiOperation("通过登录用户和博客id对博客进行点赞")
    @PutMapping("{blogId}/laud")
    public Result increaseLikeCount(@ApiParam("博客id") @PathVariable("blogId") int blogId,
                                    @ApiParam("用户的token") @RequestParam("token") String token) {
        return countService.increaseLikeCount(blogId,token);
    }


    @ApiOperation("阅读量增加")
    @PutMapping("{blogId}/count/up")
    public Result increaseReadCount(@ApiParam("博客id") @PathVariable("blogId") int blogId){
        return countService.increaseReadCount(blogId);
    }



}
