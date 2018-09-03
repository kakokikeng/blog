package com.yk.blog.domain.dto;

/**
 * @author yikang
 * @date 2018/9/3
 */

public class Follower {

    private int id;
    private String userId;
    private String followerId;

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

    public String getFollowerId() {
        return followerId;
    }

    public void setFollowerId(String followerId) {
        this.followerId = followerId;
    }
}
