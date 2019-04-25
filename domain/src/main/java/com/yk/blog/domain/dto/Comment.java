package com.yk.blog.domain.dto;

/**
 * @author yikang
 * @date 2018/8/29
 */

public class Comment {

    private Integer id;
    private String userName;
    private String attachedUserName;
    private String userId;
    private String attachedUserId;
    private Long createTime;
    private Integer blogId;
    private String content;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", attachedUserId='" + attachedUserId + '\'' +
                ", createTime=" + createTime +
                ", blogId=" + blogId +
                ", content='" + content + '\'' +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAttachedUserName() {
        return attachedUserName;
    }

    public void setAttachedUserName(String attachedUserName) {
        this.attachedUserName = attachedUserName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAttachedUserId() {
        return attachedUserId;
    }

    public void setAttachedUserId(String attachedUserId) {
        this.attachedUserId = attachedUserId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
