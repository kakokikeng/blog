package com.yk.blog.core.dto;

/**
 * @author yikang
 * @date 2018/9/12
 */

public class LoginReqDTO {

    private String email;
    private String passwd;

    @Override
    public String toString() {
        return "LoginReqDTO{" +
                "email='" + email + '\'' +
                ", passwd='" + passwd + '\'' +
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

}
