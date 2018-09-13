package com.yk.blog.core.service;

import com.yk.blog.core.dto.LoginReqDTO;
import com.yk.blog.core.dto.Result;

/**
 * @author yikang
 * @date 2018/9/13
 */
public interface AuthorityService {

    /**
     * 登录操作，先检测token是否存在，不存在再验证邮箱和密码
     * 使用redis哈希存储，k为token，v为token获得时的时间戳，定时清除超过n天的token
     *
     * @param loginReqDTO 登录信息
     * @return
     * @Author yikang
     * @Date 2018/9/13
     */
    Result login(LoginReqDTO loginReqDTO);

    /**
     * 注销操作，使用token进行注销
     *
     * @param token 登陆后获得的token
     * @return
     * @Author yikang
     * @Date 2018/9/13
     */
    Result logout(String token);

}
