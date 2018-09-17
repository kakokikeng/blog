package com.yk.blog.api.controller;

import com.yk.blog.core.dto.GenericResult;
import com.yk.blog.core.dto.LoginReqDTO;
import com.yk.blog.core.dto.Result;
import com.yk.blog.core.dto.Token;
import com.yk.blog.core.service.AuthorityService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yikang
 * @date 2018/9/11
 */

//TODO 扩展为单点登录

@RequestMapping("authority")
@RestController
public class AuthorityController {

    @Autowired
    AuthorityService authorityService;

    @ApiOperation("登录")
    @PostMapping("")
    public GenericResult<Token> login(@RequestBody @ApiParam("登录账号密码信息")LoginReqDTO loginReqDTO){
        return authorityService.login(loginReqDTO);
    }

    @ApiOperation("注销")
    @DeleteMapping("")
    public Result logout(@RequestParam("token") @ApiParam("登陆后获得的token") String token){
        return authorityService.logout(token);
    }


}
