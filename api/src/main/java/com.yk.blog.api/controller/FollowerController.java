package com.yk.blog.api.controller;

import com.yk.blog.core.dto.GenericResult;
import com.yk.blog.core.dto.Result;
import com.yk.blog.core.dto.UserRespDTO;
import com.yk.blog.core.service.FollowerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author yikang
 * @date 2018/8/30
 */

//TODO 关注用户最新信息推送 可关注不推送

@RestController
@RequestMapping("follower")
public class FollowerController {

    //TODO 修改数据库的操作全部需要带token

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
    public Result unfollow(@ApiParam("登录用户id") @PathVariable("followId") String followId,
                         @ApiParam("被关注的用户id") @PathVariable("followedId") String followedId) {
        return followerService.unfollow(followId, followedId);
    }

    @ApiOperation("查询所有关注我的用户")
    @GetMapping("{userId}/followers")
    public GenericResult<List<UserRespDTO>> getFollowers(@ApiParam("被查询用户id") @PathVariable("userId") @NotNull String userId) {
        return followerService.getFollowers(userId);
    }

    @ApiOperation("获取所有我关注的用户")
    @GetMapping("{userId}/follows")
    public GenericResult<List<UserRespDTO>> getFolloweders(@PathVariable("userId") @ApiParam("被查询用户ID") @NotNull String userId) {
        return followerService.getFolloweders(userId);
    }


}
