package com.yk.blog.core.dto;

import com.yk.blog.domain.dto.Comment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author yikang
 * @date 2018/8/30
 */
@ApiModel
public class CommentRespDTO extends CommentBaseDTO{
    @ApiModelProperty("评论id")
    private Integer id;

    public CommentRespDTO(){}

    public CommentRespDTO(Comment comment){
        setId(comment.getId());
        setAttachedId(comment.getAttachedUserId());
        setBlogId(comment.getBlogId());
        setUserId(comment.getUserId());
        setCreateTime(comment.getCreateTime());
        setContent(comment.getContent());
    }

    @Override
    public String toString() {
        return "CommentRespDTO{" +
                "id=" + id +
                "} " + super.toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
