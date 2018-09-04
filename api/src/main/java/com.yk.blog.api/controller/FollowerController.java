package com.yk.blog.api.controller;

import com.t4f.gaea.dto.GenericResult;
import com.t4f.gaea.dto.Result;
import com.yk.blog.core.dto.UserRespDTO;
import com.yk.blog.core.service.FollowerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yikang
 * @date 2018/8/30
 */
@RestController("follower")
public class FollowerController {

    @Autowired
    FollowerService followerService;

    @ApiOperation("关注某个用户")
    @PostMapping("{followId}/{followedId}")
    public Result follow(@ApiParam("登录用户id") @PathVariable("followId") String followId,
                         @ApiParam("被关注的用户id") @PathVariable("followedId") String followedId) {
        return followerService.follow(followId, followedId);
    }

    @ApiOperation("取消关注")
    @DeleteMapping("{followId}/{followedId}")
    public Result cancel(@ApiParam("登录用户id") @PathVariable("followId") String followId,
                         @ApiParam("被关注的用户id") @PathVariable("followedId") String followedId) {
        return followerService.cancel(followId, followedId);
    }

    @ApiOperation("查询所有关注我的用户")
    @GetMapping("{userId}/followers")
    public GenericResult<List<UserRespDTO>> getFollowers(@ApiParam("被查询用户id") @PathVariable("userId") @NotBlank String userId) {
        return followerService.getFollowers(userId);
    }

    @ApiOperation("获取所有我关注的用户")
    @GetMapping("{userId}/follows")
    public GenericResult<List<UserRespDTO>> getFolloweders(@PathVariable("userId") @ApiParam("被查询用户ID") @NotBlank String userId) {
        return followerService.getFolloweders(userId);
    }


}
