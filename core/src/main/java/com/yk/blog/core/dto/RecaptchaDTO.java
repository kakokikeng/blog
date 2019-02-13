package com.yk.blog.core.dto;


/**
 *  验证码信息类
 *  @Author yikang
 *  @Date 2018/9/6
*/
public class RecaptchaDTO {

    private String key;
    private String verifyCode;

    @Override
    public String toString() {
        return "RecaptchaDTO{" +
                "key='" + key + '\'' +
                ", verifyCode='" + verifyCode + '\'' +
                '}';
    }

    public RecaptchaDTO(String key, String verifyCode) {
        this.key = key;
        this.verifyCode = verifyCode;
    }

    public RecaptchaDTO() {
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
