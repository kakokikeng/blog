package com.yk.blog.core.constant;

/**
 * @author yikang
 * @date 2018/9/3
 */

public enum ErrorMessages {
    WRONG_USER_ID("用户名错误！", ""),
    CAN_NOT_FOLLOW_YOURSELF("不能关注自己！",""),
    ERROR_LOGIN_INFORMATION("登录信息错误！",""),
    TOKEN_NOT_AVAILABLE("TOKEN NOT AVAILABLE",""),
    ALREADY_FOLLOWED("已经关注该用户！",""),
    ALREADY_LOGIN("用户已经登录！",""),
    NOT_FOLLOWED("NOT FOLLOWED",""),
    ERROR_INCREASE_FANS("ERROR INCREASE FANS",""),
    BLOG_NOT_EXIST("该博客不存在！", ""),
    UPDATE_FAILED("更新失败！",""),
    REQUEST_TOO_FREQUENTLY("请求过于频繁，请稍后再试！",""),
    EMAIL_ALREADY_USED("邮箱已被注册！",""),
    ERROR_PASSWORD("密码错误！",""),
    VERIFY_CODE_NOT_EXIST("验证码不存在！",""),
    VERIFY_CODE_ERROR("验证码错误！",""),
    BLOG_ALREADY_LIKED("已为该博客点过赞！","");

    public String message;
    public String code;

    ErrorMessages(String message, String code) {
        this.message = message;
        this.code = code;
    }

}
