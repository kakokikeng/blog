package com.yk.blog.core.service;

import com.t4f.gaea.dto.Result;


/**
 * @author yikang
 * @date 2018/9/11
 */

public interface VerifyService {

    /**
     * 生成验证码发送给指定邮箱
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
     * @return
     * @Author yikang
     * @Date 2018/9/11
     */
    Result validation(String email, String verifyCode);

}
