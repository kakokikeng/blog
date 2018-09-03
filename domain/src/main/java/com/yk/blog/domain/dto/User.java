package com.yk.blog.domain.dto;

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
    private Long createTime;

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", fans=" + fans +
                ", blogs=" + blogs +
                ", createTime=" + createTime +
                '}';
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

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
