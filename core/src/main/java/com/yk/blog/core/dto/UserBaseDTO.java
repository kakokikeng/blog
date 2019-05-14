package com.yk.blog.core.dto;

import com.mysql.jdbc.StringUtils;
import com.sun.istack.internal.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.logging.log4j.util.Strings;

import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.UUID;
import java.util.logging.SimpleFormatter;

/**
 * @author yikang
 * @date 2018/8/31
 */
@ApiModel
public class UserBaseDTO {

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("创建时间")
    private Long createTime;

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

    public String generateUserId(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        long timeStamp = System.currentTimeMillis();
        String randomStr = UUID.randomUUID().toString();
        String result = formatter.format(timeStamp) + randomStr.substring(randomStr.length() - 12, randomStr.length());
        return result;
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
