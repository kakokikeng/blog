package com.yk.blog.core.utils;

/**
 * @author yikang
 * @date 2018/9/3
 */

public enum ErrorMessages {

    WRONG_USER_ID("WRONG USER ID", ""),
    FOLLOW_RECORD_NOT_EXIST("FOLLOWED RECORD NOT EXIST",""),
    ERROR_INCREASE_FANS("ERROR INCREASE FANS",""),
    BLOG_NOT_EXIST("BLOG NOT EXIST", ""),
    BLOG_UPDATE_FAILD("BLOG UPDATE FAILD",""),
    BLOG_ALREADY_LIKED("YOU HAVE ALREADY LIKED THE BLOG","");

    public String message;
    public String code;

    ErrorMessages(String message, String code) {
        this.message = message;
        this.code = code;
    }

}
