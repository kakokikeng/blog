package com.yk.blog.api.controller;

import com.yk.blog.core.dto.Result;
import com.yk.blog.core.dto.UserReqDTO;
import com.yk.blog.core.service.VerifyService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author yikang
 * @date 2018/9/11
 */
@RequestMapping("verify")
@RestController
public class VerifyController {

    @Autowired
    VerifyService verifyService;

    @GetMapping("{email}")
    @ApiOperation("请求验证码")
    public Result generateVerifyCode(@ApiParam("接收验证码的邮箱") @PathVariable("email") String email){
        return verifyService.generateVerifyCode(email);
    }

    @PostMapping("{email}/{verifyCode}")
    @ApiOperation("验证码验证")
    public Result validation(@ApiParam("接收验证码的邮箱") @PathVariable("email") String email,
                             @ApiParam("用户输入的验证码") @PathVariable("verifyCode") String verifyCode,
                             @ApiParam("新建用户的用户信息") @RequestBody UserReqDTO userReqDTO){
        return verifyService.validation(email,verifyCode,userReqDTO);
    }

}
