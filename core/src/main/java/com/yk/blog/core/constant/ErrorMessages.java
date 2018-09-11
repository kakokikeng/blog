package com.yk.blog.core.constant;

/**
 * @author yikang
 * @date 2018/9/3
 */

public enum ErrorMessages {

    WRONG_USER_ID("WRONG USER ID", ""),
    FOLLOW_RECORD_NOT_EXIST("FOLLOWED RECORD NOT EXIST",""),
    ERROR_INCREASE_FANS("ERROR INCREASE FANS",""),
    BLOG_NOT_EXIST("BLOG NOT EXIST", ""),
    UPDATE_FAILED("UPDATE FAILED",""),
    EMAIL_ALREADY_USED("EMAIL ALREADY BEEN USED",""),
    ERROR_PASSWORD("ERROR PASSWORD",""),
    VERIFY_CODE_TIME_OUT("VERIFY CODE TIME OUT",""),
    VERIFY_CODE_ERROR("VERIFY CODE ERROR",""),
    BLOG_ALREADY_LIKED("YOU HAVE ALREADY LIKED THE BLOG","");

    public String message;
    public String code;

    ErrorMessages(String message, String code) {
        this.message = message;
        this.code = code;
    }

}
