package com.yk.blog.core.utils;

import com.yk.blog.core.dto.GenericResult;
import com.yk.blog.core.dto.Result;
import com.yk.blog.core.constant.ErrorMessages;
import com.yk.blog.core.dto.UserRespDTO;
import com.yk.blog.domain.dto.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author yikang
 * @date 2018/9/3
 */

public class UserUtils {

    public static Result wrongUserIdResult(){
        return GenericResultUtils.genericFailureResult(ErrorMessages.WRONG_USER_ID.message,ErrorMessages.WRONG_USER_ID.code);
    }

    public static <T>GenericResult<T> wrongUserIdGenericResult(){
        return GenericResultUtils.genericFailureResult(ErrorMessages.WRONG_USER_ID.message,ErrorMessages.WRONG_USER_ID.code);
    }

    public static List<String> getIdList(String ... id){
        return Arrays.asList(id);
    }

    public static List<UserRespDTO> changeUserListToDTOList(List<User> userList){
        List<UserRespDTO> result = new ArrayList<>();
        if(userList != null && userList.size() != 0){
            for (User user:userList) {
                result.add(new UserRespDTO(user));
            }
        }
        return result;
    }

}
