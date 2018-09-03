package com.yk.blog.core.dto;

import com.sun.istack.internal.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author yikang
 * @date 2018/8/31
 */
@ApiModel
public class UserBaseDTO {
    @NotNull
    @ApiModelProperty("用户名")
    private String userName;
    @NotNull
    @ApiModelProperty("创建时间")
    private Long createTime;
    @NotNull
    @ApiModelProperty("用户邮箱")
    private String email;

    @Override
    public String toString() {
        return "UserBaseDTO{" +
                "userName='" + userName + '\'' +
                ", createTime=" + createTime +
                ", email='" + email + '\'' +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
