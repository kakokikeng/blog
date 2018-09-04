package com.yk.blog.core.service;

import com.t4f.gaea.dto.Result;
import io.swagger.annotations.ApiParam;

/**
 * @author yikang
 * @date 2018/9/4
 */

public interface CountService {


    /**
     *  通过登录用户和博客id对博客进行点赞
     * @param userId 登录用户id
     * @param blogId 当前博客id
     * @return
     *  @Author yikang
     *  @Date 2018/9/3
     */
    Result increaseLikeCount(String userId, int blogId);

    /**
     *  阅读量增加
     * @param blogId 博客id
     * @return
     *  @Author yikang
     *  @Date 2018/9/4
    */
    Result increaseReadCount(int blogId);

    /**
     *  获取阅读量
     * @param blogId 博客id
     * @return 阅读量
     *  @Author yikang
     *  @Date 2018/9/4
    */
    int getReadCount(int blogId);

}
