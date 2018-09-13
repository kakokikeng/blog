package com.yk.blog.core.dto;


/**
 * 普通结果类
 *
 * @Author yikang
 * @Date 2018/9/13
 */
public class Result {
    private boolean success;
    private String code;
    private String message;

    public Result() {
    }

    public boolean isSuccess() {
        return this.success;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
