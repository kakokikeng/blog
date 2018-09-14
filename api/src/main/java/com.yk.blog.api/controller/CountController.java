package com.yk.blog.api.controller;

import com.yk.blog.core.dto.Result;
import com.yk.blog.core.service.CountService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yikang
 * @date 2018/9/4
 */
@RestController
@RequestMapping("count")
public class CountController {

    //TODO 修改数据库的操作全部需要带token

    @Autowired
    CountService countService;

    @ApiOperation("通过登录用户和博客id对博客进行点赞")
    @PutMapping("{userId}/{blogId}/laud")
    public Result increaseLikeCount(@ApiParam("登录用户id") @PathVariable("userId") String userId,
                                    @ApiParam("博客id") @PathVariable("blogId") int blogId) {
        return countService.increaseLikeCount(userId, blogId);
    }


    @ApiOperation("阅读量增加")
    @PutMapping("{blogId}/count/up")
    public Result increaseReadCount(@ApiParam("博客id") @PathVariable("blogId") int blogId){
        return countService.increaseReadCount(blogId);
    }



}
