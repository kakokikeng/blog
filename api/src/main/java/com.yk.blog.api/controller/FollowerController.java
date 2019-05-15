package com.yk.blog.api.controller;

import com.yk.blog.core.dto.GenericResult;
import com.yk.blog.core.dto.Result;
import com.yk.blog.core.dto.UserRespDTO;
import com.yk.blog.core.service.FollowerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author yikang
 * @date 2018/8/30
 */



@RestController
@RequestMapping("follower")
public class FollowerController {

    @Autowired
    FollowerService followerService;

    @ApiOperation("关注某个用户")
    @PostMapping("{followedId}")
    public Result follow(@ApiParam("被关注的用户id") @PathVariable("followedId") String followedId,
                         @ApiParam("登陆后获得的token") @RequestParam("token") String token,
                         @ApiParam("是否接收消息的推送") @RequestParam("messagePush") boolean messagePush) {
        return followerService.follow(followedId, token, messagePush);
    }

    @ApiOperation("查询登录用户是否关注B用户")
    @GetMapping("{followedId}")
    public Result getIfFollowed(@ApiParam("被关注的用户id") @PathVariable("followedId") String followedId,
                                @ApiParam("登陆后获得的token") @RequestParam("token") String token) {
        return followerService.getIfFollowed(followedId, token);
    }

    @ApiOperation("取消关注")
    @DeleteMapping("{followedId}")
    public Result unfollow(@ApiParam("被关注的用户id") @PathVariable("followedId") String followedId,
                           @ApiParam("登陆后获得的token") @RequestParam("token") String token) {
        return followerService.unfollow(followedId, token);
    }

    @ApiOperation("查询所有关注我的用户")
    @GetMapping("{userId}/followers")
    public GenericResult<List<UserRespDTO>> getFollowers(@ApiParam("被查询用户id") @PathVariable("userId") @NotNull String userId) {
        return followerService.getFollowers(userId);
    }

    @ApiOperation("获取登录用户的粉丝")
    @GetMapping("fans")
    public GenericResult<List<UserRespDTO>> getLoginFans(@RequestParam("token") String token) {
        return followerService.getLoginFans(token);
    }

    @ApiOperation("获取用户所关注的用户")
    @GetMapping("{userId}/follows")
    public GenericResult<List<UserRespDTO>> getFolloweders(@PathVariable("userId") @ApiParam("被查询用户ID") @NotNull String userId) {
        return followerService.getFolloweders(userId);
    }

    @ApiOperation("获取登录用户关注的用户")
    @GetMapping("follows")
    public GenericResult<List<UserRespDTO>> getLoginFolloweders(@RequestParam("token") String token) {
        return followerService.getLoginFolloweders(token);
    }


}
