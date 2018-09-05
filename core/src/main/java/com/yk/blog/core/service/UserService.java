package com.yk.blog.core.service;

import com.t4f.gaea.dto.GenericResult;
import com.t4f.gaea.dto.Result;
import com.yk.blog.core.dto.UserReqDTO;
import com.yk.blog.core.dto.UserRespDTO;
import com.yk.blog.domain.dto.User;

import java.util.List;

/**
 * @author yikang
 * @date 2018/9/3
 */

public interface UserService {

    /**
     * 更新用户信息
     *
     * @param userId     用户id
     * @param userReqDTO 用户信息
     * @return
     * @Author yikang
     * @Date 2018/9/5
     */
    Result updateUser(String userId, UserReqDTO userReqDTO);

    /**
     * 通过用户id删除用户
     *
     * @param userId 用户id
     * @return
     * @Author yikang
     * @Date 2018/9/5
     */
    Result deleteUser(String userId);

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

    /**
     * 通过用户id列表获得用户信息列表
     *
     * @param userIds 用户id列表
     * @return 用户信息列表
     * @Author yikang
     * @Date 2018/9/4
     */
    List<UserRespDTO> getUserListByIdList(List<String> userIds);

    /**
     * 通过用户id列表获得用户信息列表
     *
     * @param userIds 用户id列表
     * @return 用户信息列表
     * @Author yikang
     * @Date 2018/9/4
     */
    GenericResult<List<UserRespDTO>> getUsers(List<String> userIds);

    /**
     * 新建用户
     *
     * @param userReqDTO 用户信息
     * @return
     * @Author yikang
     * @Date 2018/9/5
     */
    Result createUser(UserReqDTO userReqDTO);


}
