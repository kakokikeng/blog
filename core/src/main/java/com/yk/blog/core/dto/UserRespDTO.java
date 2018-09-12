package com.yk.blog.core.dto;

import com.yk.blog.domain.dto.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yikang
 * @date 2018/8/30
 */
@ApiModel
public class UserRespDTO extends UserBaseDTO{
    @ApiModelProperty("用户id")
    private String id;
    @ApiModelProperty("粉丝数量")
    private Integer fans;
    @ApiModelProperty("博客数量")
    private Integer blogs;

    public UserRespDTO(){}

    public UserRespDTO(User user){
        setUserName(user.getUserName());
        setEmail(user.getEmail());
        setCreateTime(user.getCreateTime().getTime());
        setId(user.getId());
        setFans(user.getFans());
        setBlogs(user.getBlogs());
    }


    @Override
    public String toString() {
        return "UserRespDTO{" +
                "id='" + id + '\'' +
                ", fans=" + fans +
                ", blogs=" + blogs +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getFans() {
        return fans;
    }

    public void setFans(Integer fans) {
        this.fans = fans;
    }

    public Integer getBlogs() {
        return blogs;
    }

    public void setBlogs(Integer blogs) {
        this.blogs = blogs;
    }
}
