package com.yk.blog.core.utils;

import com.t4f.gaea.dto.GenericResult;
import com.t4f.gaea.dto.Result;
import com.yk.blog.core.dto.BlogRespDTO;

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
}
