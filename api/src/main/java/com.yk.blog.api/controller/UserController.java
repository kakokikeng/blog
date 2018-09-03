package com.yk.blog.api.controller;

import com.t4f.gaea.dto.Result;
import com.yk.blog.core.dto.UserReqDTO;
import com.yk.blog.core.dto.UserRespDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yikang
 * @date 2018/8/29
 */
@RestController("user")
public class UserController {

    @ApiOperation("创建用户")
    @PostMapping("")
    public Result createUser(@RequestBody UserReqDTO userReqDTO) {
        return null;
    }

    @ApiOperation("删除用户")
    @DeleteMapping("{userId}")
    public Result deleteUser(@PathVariable("userId") String userId) {
        return null;
    }

    @ApiOperation("修改用户信息")
    @PutMapping("{userId}")
    public Result updateUser(@PathVariable("userId") String userId, @RequestBody UserReqDTO userReqDTO) {
        return null;
    }

    @ApiOperation("搜索用户列表")
    @GetMapping("users")
    public List<UserRespDTO> getUsers(@RequestParam("userIds")List<String> userIds){
        return null;
    }

}
