package com.yk.blog.core.service;

import com.yk.blog.core.dto.Result;

/**
 * @author yikang
 * @date 2018/9/4
 */

public interface CountService {

    /**
     * 更新用户的博客数
     *
     * @param userId      用户id
     * @param updateCount 更新的数量，增加则为正数，删除为负数
     * @return 操作失败返回0
     * @Author yikang
     * @Date 2018/9/6
     */
    int updateBlogCount(String userId, int updateCount);

    /**
     * 通过登录用户和博客id对博客进行点赞
     *
     * @param userId 登录用户id
     * @param blogId 当前博客id
     * @return
     * @Author yikang
     * @Date 2018/9/3
     */
    Result increaseLikeCount(String userId, int blogId);

    /**
     * 阅读量增加
     *
     * @param blogId 博客id
     * @return
     * @Author yikang
     * @Date 2018/9/4
     */
    Result increaseReadCount(int blogId);

    /**
     * 获取阅读量
     *
     * @param blogId 博客id
     * @return 阅读量
     * @Author yikang
     * @Date 2018/9/4
     */
    int getReadCount(int blogId);

    /**
     * 更新用户的粉丝数，从redis获取粉丝数量
     *
     * @param userId 用户id
     * @return 操作失败返回0
     * @Author yikang
     * @Date 2018/9/4
     */
    int updateFans(String userId);

}
