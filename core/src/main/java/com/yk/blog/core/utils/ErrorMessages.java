package com.yk.blog.core.utils;

/**
 * @author yikang
 * @date 2018/9/3
 */

public enum ErrorMessages {

    WRONG_USER_ID("WRONG USER ID", ""),
    FOLLOW_RECORD_NOT_EXIST("FOLLOW RECORD NOT EXIST",""),
    ERROR_INCREASE_FANS("ERROR INCREASE FANS",""),
    BLOG_NOT_EXIST("BLOG NOT EXIST", "");

    public String message;
    public String code;

    ErrorMessages(String message, String code) {
        this.message = message;
        this.code = code;
    }

}
