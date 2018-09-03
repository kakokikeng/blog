package com.yk.blog.core.dto;

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
