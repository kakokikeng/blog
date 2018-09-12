package com.yk.blog.domain.dto;

import java.util.Date;

/**
 * @author yikang
 * @date 2018/8/29
 */

public class User {

    private String id;
    private String userName;
    private String email;
    private Integer fans;
    private Integer blogs;
    private Date createTime;
    private String passwd;

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", fans=" + fans +
                ", blogs=" + blogs +
                ", createTime=" + createTime +
                ", passwd='" + passwd + '\'' +
                '}';
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getFans() {
        return fans;
    }

    public void setFans(Integer fans) {
        this.fans = fans;
    }

    public Integer getBlogs() {
        return blogs;
    }

    public void setBlogs(Integer blogs) {
        this.blogs = blogs;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
