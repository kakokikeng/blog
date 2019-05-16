package com.yk.blog.domain.dto;

/**
 * @author yikang
 * @date 2019/5/16
 */
public class Collection {

    private int id;
    private String userId;
    private int blogId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }
}
