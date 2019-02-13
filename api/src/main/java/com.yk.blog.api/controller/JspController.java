package com.yk.blog.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author yikang
 * @date 2019/2/11
 */
@RequestMapping
@Controller
public class JspController {
    @RequestMapping("index")
    public String turnIndex(){
        return "index";
    }

    @RequestMapping("forgetPasswd")
    public String turnForgetPasswd(){
        return "forgetPasswd";
    }

    @RequestMapping("signUp")
    public String turnSignUp(){
        return "signUp";
    }
}
