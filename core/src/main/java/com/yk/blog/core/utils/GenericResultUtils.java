package com.yk.blog.core.utils;

import com.t4f.gaea.dto.GenericResult;
import com.t4f.gaea.dto.Result;


/**
 * @author yikang
 * @date 2018/9/3
 */

public class GenericResultUtils {

    public static <T> GenericResult<T> genericResult(boolean success, T... data) {
        GenericResult<T> result = new GenericResult<>();
        if (data.length > 0) {
            result.setData(data[0]);
        }
        result.setSuccess(success);
        return result;
    }

    public static Result generateResultWithCount(int count) {
        if (count > 0) {
            return GenericResultUtils.genericNormalResult(true);
        } else {
            return GenericResultUtils.genericNormalResult(false);
        }
    }


    public static <T> GenericResult<T> genericFailureResult(String message, String... code) {
        GenericResult<T> result = new GenericResult<>();
        result.setSuccess(false);
        result.setMessage(message);
        if (code.length > 0) {
            result.setCode(code[0]);
        }
        return result;
    }

    public static Result genericNormalResult(boolean success, String... message) {
        Result result = new Result();
        result.setSuccess(success);
        if (message.length > 0) {
            result.setMessage(message[0]);
        }
        return result;
    }

}
