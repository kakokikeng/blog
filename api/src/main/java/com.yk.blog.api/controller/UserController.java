package com.yk.blog.api.controller;

import com.t4f.gaea.dto.GenericResult;
import com.t4f.gaea.dto.Result;
import com.yk.blog.core.dto.UserReqDTO;
import com.yk.blog.core.dto.UserRespDTO;
import com.yk.blog.core.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yikang
 * @date 2018/8/29
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @ApiOperation("创建用户")
    @PostMapping("")
    public Result createUser(@RequestBody UserReqDTO userReqDTO) {
        return userService.createUser(userReqDTO);
    }

    @ApiOperation("删除用户")
    @DeleteMapping("{userId}")
    public Result deleteUser(@PathVariable("userId") String userId) {
        return userService.deleteUser(userId);
    }

    @ApiOperation("修改用户信息")
    @PutMapping("{userId}")
    public Result updateUser(@PathVariable("userId") String userId, @RequestBody UserReqDTO userReqDTO) {
        return userService.updateUser(userId,userReqDTO);
    }

    @ApiOperation("修改密码")
    @PutMapping("{email}/{oldPasswd}/{newPasswd}")
    public Result modifyPasswd(@ApiParam("登录邮箱") @PathVariable("email") String email,
                               @ApiParam("原来的密码") @PathVariable("oldPasswd") String oldPasswd,
                               @ApiParam("新的密码") @PathVariable("newPasswd") String newPasswd){
        return userService.modifyPasswd(email,oldPasswd,newPasswd);
    }


    @ApiOperation("搜索用户列表")
    @GetMapping("users")
    public GenericResult<List<UserRespDTO>> getUsers(@RequestParam("userIds")List<String> userIds){
        return userService.getUsers(userIds);
    }

}
