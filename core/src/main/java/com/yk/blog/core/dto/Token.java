package com.yk.blog.core.dto;

/**
 * @author yikang
 * @date 2018/9/13
 */

public class Token {

    private String token;

    @Override
    public String toString() {
        return "Token{" +
                "token='" + token + '\'' +
                '}';
    }

    public Token(){

    }

    public Token(String token){
        this.token = token;
    }



    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
