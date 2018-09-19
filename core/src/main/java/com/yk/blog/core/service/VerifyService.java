package com.yk.blog.core.service;

import com.yk.blog.core.dto.Result;
import com.yk.blog.core.dto.UserReqDTO;


/**
 * @author yikang
 * @date 2018/9/11
 */

public interface VerifyService {

    /**
     * 生成验证码发送给指定邮箱,每个邮箱每分钟最多请求五次
     *
     * @param email 接收验证码的邮箱
     * @return
     * @Author yikang
     * @Date 2018/9/11
     */
    Result generateVerifyCode(String email);

    /**
     * 输入验证码验证
     *
     * @param email      收到验证码的邮箱
     * @param verifyCode 输入的验证码
     * @param userReqDTO 新建用户的信息
     * @return
     * @Author yikang
     * @Date 2018/9/11
     */
    Result validation(String email, String verifyCode, UserReqDTO userReqDTO);

}
