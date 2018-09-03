package com.yk.blog.core.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author yikang
 * @date 2018/8/31
 */

public class BlogReqDTO extends BlogBaseDTO{
    @ApiModelProperty(name = "Id",value = "更新时传入博客id")
    private Integer Id;

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
