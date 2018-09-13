package com.yk.blog.core.dto;

/**
 * @author yikang
 * @date 2018/9/12
 */

public class LoginReqDTO {

    private String email;
    private String passwd;
    private String token;

    @Override
    public String toString() {
        return "LoginReqDTO{" +
                "email='" + email + '\'' +
                ", passwd='" + passwd + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
