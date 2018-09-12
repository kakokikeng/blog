package com.yk.blog.api.controller;

import com.t4f.gaea.dto.Result;
import com.yk.blog.core.dto.LoginReqDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yikang
 * @date 2018/9/11
 */
//TODO 用户登录 使用MD5加密存储密码 已登录用户拥有token
@RequestMapping("")
@RestController
public class LoginController {

    @ApiOperation("登录")
    @PostMapping("login")
    public Result login(@RequestBody @ApiParam("登录账号密码信息")LoginReqDTO loginReqDTO){
        return null;
    }

}
