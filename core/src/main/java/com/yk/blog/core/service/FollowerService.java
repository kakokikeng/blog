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
     *  判断登录用户是否关注某用户
     *  @author  yikang
     *  @date  2019/4/28
     *  @param
     *  @return
     */
    Result getIfFollowed(String followedId,String token);

    /**
     * 关注某个用户
     *
     * @param followedId 被关注用户id
     * @param token      登录用户的token
     * @return 是否成功
     * @Author yikang
     * @Date 2018/9/4
     */
    Result follow(String followedId, String token,boolean messagePush);

    /**
     * 取消关注
     *
     * @param followedId 被关注用户id
     * @param token      登录用户的token
     * @return 是否成功
     * @Author yikang
     * @Date 2018/9/4
     */
    Result unfollow(String followedId, String token);

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
