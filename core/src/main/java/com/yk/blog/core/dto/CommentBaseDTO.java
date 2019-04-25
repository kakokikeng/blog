package com.yk.blog.core.dto;

import com.sun.istack.internal.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author yikang
 * @date 2018/8/31
 */
@ApiModel
public class CommentBaseDTO {
    @ApiModelProperty("用户id")
    private String userId;
    @ApiModelProperty("被回复用户id 没有则为空")
    private String attachedId;
    @ApiModelProperty("用户昵称")
    private String userName;
    @ApiModelProperty("被回复用户昵称")
    private String attachedUserName;
    @NotNull
    @ApiModelProperty("评论内容")
    private String content;
    @NotNull
    @ApiModelProperty("创建时间")
    private Long createTime;
    @NotNull
    @ApiModelProperty("博客id")
    private Integer blogId;

    @Override
    public String toString() {
        return "CommentBaseDTO{" +
                "userId='" + userId + '\'' +
                ", attachedId='" + attachedId + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", blogId=" + blogId +
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

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAttachedId() {
        return attachedId;
    }

    public void setAttachedId(String attachedId) {
        this.attachedId = attachedId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
