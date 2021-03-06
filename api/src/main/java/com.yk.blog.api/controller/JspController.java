package com.yk.blog.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author yikang
 * @date 2019/2/11
 */
@RequestMapping
@Controller
public class JspController {
    @GetMapping("/")
    public String homePage(){
        return "login";
    }

    @GetMapping("myCollection")
    public String myCollection(){
        return "myCollection";
    }

    @GetMapping("modifyInfo")
    public String modifyInfo() {
        return "modifyInfo";
    }

    @GetMapping("createBlog")
    public String createBlog(){
        return "createBlog";
    }

    @GetMapping("message")
    public String message(){
        return "message";
    }

    @GetMapping("myBlogs")
    public String myBlogs() {
        return "myBlogs";
    }

    @GetMapping("myFllows")
    public String myFllows() {
        return "myFllows";
    }

    @GetMapping("myFollowers")
    public String myFollowers() {
        return "myFans";
    }

    @GetMapping("userPage")
    public String userPage(){
        return "userPage";
    }

    @GetMapping("login")
    public String login(){
        return "login";
    }

    @GetMapping("404")
    public String notFound(){
        return "404";
    }

    @GetMapping("blogPage")
    public String blogPage(){
        return "blogPage";
    }

    @GetMapping("search")
    public String search(){
        return "search";
    }


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

    @RequestMapping("success")
    public String success(){
        return "success";
    }

    @RequestMapping("changePasswd")
    public String changePasswd(){
        return "changePasswd";
    }
}
