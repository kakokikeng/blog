package com.yk.blog.core.service;

import java.util.List;

/**
 * @author yikang
 * @date 2018/9/3
 */

public interface UserService {
    /**
     * 通过userId判断这个用户是否存在
     *
     * @param userId 被查询用户id
     * @return 存在则返回true
     * @Author yikang
     * @Date 2018/9/3
     */
    boolean existUser(String userId);

    /**
     * 批量判断用户id是否存在
     *
     * @param userIds 用户id
     * @return 所有都存在才返回true
     * @Author yikang
     * @Date 2018/9/4
     */
    boolean existUsers(List<String> userIds);


}
