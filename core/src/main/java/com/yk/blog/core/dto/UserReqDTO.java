package com.yk.blog.core.dto;

import com.yk.blog.domain.dto.User;

/**
 * @author yikang
 * @date 2018/8/30
 */

public class UserReqDTO extends UserBaseDTO{

    public User changeToUser(String ... userId){
        User result = new User();
        if(userId.length > 0){
            result.setId(userId[0]);
        }else{
            result.setId(generateUserId());
        }
        result.setUserName(getUserName());
        result.setEmail(getEmail());
        result.setCreateTime(getCreateTime());
        return result;
    }



    @Override
    public String toString() {
        return "UserReqDTO{} " + super.toString();
    }
}
