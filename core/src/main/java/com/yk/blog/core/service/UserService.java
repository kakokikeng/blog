package com.yk.blog.core.service;

/**
 * @author yikang
 * @date 2018/9/3
 */

public interface UserService {
    /**
     *  通过userId判断这个用户是否存在
     * @param userId 被查询用户id
     * @return
     *  @Author yikang
     *  @Date 2018/9/3
    */
    boolean existUser(String userId);

}
