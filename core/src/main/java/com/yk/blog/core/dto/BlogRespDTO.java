package com.yk.blog.core.dto;

import com.yk.blog.domain.dto.Blog;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author yikang
 * @date 2018/8/29
 */
@ApiModel
public class BlogRespDTO extends BlogBaseDTO{
    @ApiModelProperty(name = "id",value = "该篇博客id")
    private Integer id;
    @ApiModelProperty(name = "createTime",value = "博客创建时间")
    private Long createTime;
    @ApiModelProperty(name = "readCount",value = "博客阅读量")
    private Integer readCount;
    @ApiModelProperty(name = "likeCount",value = "点赞数")
    private Integer likeCount;
    @ApiModelProperty(name = "commentCount",value = "评论数")
    private Integer commentCount;

    public BlogRespDTO(){}


    @Override
    public String toString() {
        return "BlogRespDTO{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", readCount=" + readCount +
                ", likeCount=" + likeCount +
                ", commentCount=" + commentCount +
                "} " + super.toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

}
