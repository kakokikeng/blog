package com.yk.blog.core.service;

import com.yk.blog.core.dto.GenericResult;
import com.yk.blog.core.dto.Result;
import com.yk.blog.core.dto.UserRespDTO;

import java.util.List;

/**
 * @author yikang
 * @date 2018/9/3
 */
public interface FollowerService {

    /**
     * 关注某个用户
     *
     * @param followId   登录用户id
     * @param followedId 被关注用户id
     * @return 是否成功
     * @Author yikang
     * @Date 2018/9/4
     */
    Result follow(String followId, String followedId);

    /**
     * 取消关注
     *
     * @param followId   登录用户id
     * @param followedId 被关注用户id
     * @return 是否成功
     * @Author yikang
     * @Date 2018/9/4
     */
    Result unfollow(String followId, String followedId);

    /**
     * 获得用户所有关注者
     *
     * @param userId 用户id
     * @return
     * @Author yikang
     * @Date 2018/9/4
     */
    GenericResult<List<UserRespDTO>> getFollowers(String userId);

    /**
     * 获得用户关注的所有用户
     *
     * @param userId 用户id
     * @return
     * @Author yikang
     * @Date 2018/9/4
     */
    GenericResult<List<UserRespDTO>> getFolloweders(String userId);

}
