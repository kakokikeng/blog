package com.yk.blog.core.constant;

/**
 * @author yikang
 * @date 2018/9/3
 */

public enum ErrorMessages {
    WRONG_USER_ID("WRONG USER ID", ""),
    CAN_NOT_FOLLOW_YOURSELF("CAN NOT FOLLOW YOURSELF",""),
    ERROR_LOGIN_INFORMATION("ERROR LOGIN INFORMATION",""),
    TOKEN_NOT_AVAILABLE("TOKEN NOT AVAILABLE",""),
    ALREADY_FOLLOWED("ALREADY FOLLOWED",""),
    ALREADY_LOGIN("ALREADY LOGIN",""),
    NOT_FOLLOWED("NOT FOLLOWED",""),
    ERROR_INCREASE_FANS("ERROR INCREASE FANS",""),
    BLOG_NOT_EXIST("BLOG NOT EXIST", ""),
    UPDATE_FAILED("UPDATE FAILED",""),
    REQUEST_TOO_FREQUENTLY("REQUEST TOO FREQUENTLY, PLEASE REQUEST LATER",""),
    EMAIL_ALREADY_USED("EMAIL ALREADY BEEN USED",""),
    ERROR_PASSWORD("ERROR PASSWORD",""),
    VERIFY_CODE_NOT_EXIST("VERIFY CODE NOT EXIST",""),
    VERIFY_CODE_ERROR("VERIFY CODE ERROR",""),
    BLOG_ALREADY_LIKED("YOU HAVE ALREADY LIKED THE BLOG","");

    public String message;
    public String code;

    ErrorMessages(String message, String code) {
        this.message = message;
        this.code = code;
    }

}
