package com.yk.blog.core.dto;

import com.sun.istack.internal.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author yikang
 * @date 2018/8/31
 */
@ApiModel("blog base DTO")
public class BlogBaseDTO {
    @NotNull
    @ApiModelProperty(name = "title",value = "博客标题")
    private String title;
    @NotNull
    @ApiModelProperty(name = "content",value = "博客内容")
    private String content;
    @ApiModelProperty(name = "userId",value = "所属用户id")
    private String userId;
    @ApiModelProperty(name = "id",value = "博客id")
    private Integer id;

    @Override
    public String toString() {
        return "BlogBaseDTO{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", userId='" + userId + '\'' +
                ", id=" + id +
                '}';
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
