package com.yk.blog.core.dto;

import com.yk.blog.domain.dto.Blog;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author yikang
 * @date 2018/8/31
 */

public class BlogReqDTO extends BlogBaseDTO{
    @ApiModelProperty(name = "Id",value = "更新时传入博客id")
    private Integer Id;

    public Blog changeToBlog(boolean isInsert){
        Blog result = new Blog();
        result.setTitle(getTitle());
        result.setContent(getContent());
        result.setId(getId());
        if(isInsert){
            result.setCreateTime(System.currentTimeMillis());
            result.setUserId(getUserId());
        }
        return result;
    }

    @Override
    public String toString() {
        return "BlogReqDTO{" +
                "Id=" + Id +
                "} " + super.toString();
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

}
