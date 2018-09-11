package com.yk.blog.core.dto;

import com.sun.istack.internal.NotNull;
import com.yk.blog.core.utils.Utils;
import com.yk.blog.domain.dto.User;

/**
 * @author yikang
 * @date 2018/8/30
 */

public class UserReqDTO extends UserBaseDTO{

    @NotNull
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
            result.setPassWd(Utils.generateMd5(getPasswd()));
        }
        result.setUserName(getUserName());
        result.setEmail(getEmail());
        result.setCreateTime(getCreateTime());
        return result;
    }


}
