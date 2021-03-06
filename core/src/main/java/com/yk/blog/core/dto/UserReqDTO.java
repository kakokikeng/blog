package com.yk.blog.core.dto;

import com.sun.istack.internal.NotNull;
import com.yk.blog.core.utils.Utils;
import com.yk.blog.domain.dto.User;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author yikang
 * @date 2018/8/30
 */

public class UserReqDTO extends UserBaseDTO{


    @ApiModelProperty("密码")
    private String passwd;

    @Override
    public String toString() {
        return "UserReqDTO{" +
                "passwd='" + passwd + '\'' +
                "} " + super.toString();
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public User changeToUser(String ... userId){
        User result = new User();
        if(userId.length > 0){
            result.setId(userId[0]);
        }else{
            result.setId(generateUserId());
            result.setPasswd(Utils.generateMd5(getPasswd()));
        }
        result.setUserName(getUserName());
        result.setEmail(getEmail());
        if(getCreateTime() != null){
            result.setCreateTime(new Date(getCreateTime()));
        }
        return result;
    }


}
