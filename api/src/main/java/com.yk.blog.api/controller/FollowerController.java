package com.yk.blog.api.controller;

import com.t4f.gaea.dto.Result;
import com.yk.blog.core.dto.UserRespDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

    @ApiOperation("关注某个用户")
    @PostMapping("{followerId}/{followedId}")
    public Result follow(@ApiParam("登录用户id") @PathVariable("followerId") String followerId,
                         @ApiParam("被关注的用户id") @PathVariable("followedId") String followedId) {
        return null;
    }

    @ApiOperation("取消关注")
    @DeleteMapping("{followerId}/{followedId}")
    public Result cancel(@ApiParam("登录用户id") @PathVariable("followerId") String followerId,
                         @ApiParam("被关注的用户id") @PathVariable("followedId") String followedId) {
        return null;
    }

    @ApiOperation("查询所有关注我的用户")
    @GetMapping("{userId}")
    public List<UserRespDTO> getFollower(@ApiParam("被查询用户id") @PathVariable("userId") String userId) {
        return null;
    }

}
