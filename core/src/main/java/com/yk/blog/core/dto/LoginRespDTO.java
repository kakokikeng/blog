package com.yk.blog.core.dto;

/**
 * @author yikang
 * @date 2018/9/13
 */

public class LoginRespDTO {

    private String token;

    @Override
    public String toString() {
        return "LoginRespDTO{" +
                "token='" + token + '\'' +
                '}';
    }

    public LoginRespDTO(){

    }

    public LoginRespDTO(String token){
        this.token = token;
    }



    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
