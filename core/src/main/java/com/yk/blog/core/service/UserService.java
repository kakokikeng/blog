package com.yk.blog.core.service;

import com.yk.blog.core.dto.GenericResult;
import com.yk.blog.core.dto.Result;
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
     *  随机获取用户
     *  @author  yikang
     *  @date  2019/5/16
     *  @param  n
     *  @return  java.util.List<com.yk.blog.domain.dto.User>
     */
    List<User> getUsersByRandom(int n);

    /**
     * @param
     * @return
     * @author yikang
     * @date 2019/5/14
     */
    Result updateUserByToken(String token, UserReqDTO userReqDTO);

    /**
     *  获取登录用户信息
     *  @author  yikang
     *  @date  2019/4/22
     *  @param
     *  @return
     */
    UserRespDTO getLoginUserInfo(String token);

    /**
     *  通过邮箱获取用户基本信息
     *  @author  yikang
     *  @date  2019/2/24
     *  @param  email 被查询用户的邮箱
     *  @return  用户基本信息
     */
    UserRespDTO getUserMessageByEmail(String email);

    /**
     *  通过邮箱获得用户id
     * @param email 用户邮箱
     * @return
     *  @Author yikang
     *  @Date 2018/9/14
    */
    String getUserIdByEmail(String email);

    /**
     *  更新密码
     * @param email 登录邮箱
     * @param newPasswd 新密码
     * @param oldPasswd 旧密码
     * @return
     *  @Author yikang
     *  @Date 2018/9/11
    */
    Result modifyPasswd(String email,String oldPasswd,String newPasswd);

    /**
     * 更新密码
     *
     * @param email     登录邮箱
     * @param newPasswd 新密码
     * @param
     * @return
     * @author yikang
     * @date 2019/2/21
     */
    Result modifyPasswd(String email, String newPasswd);


    /**
     *  更新用户的粉丝数量
     * @param userId 用户id
     * @param fansCount 粉丝数量
     * @return
     *  @Author yikang
     *  @Date 2018/9/10
    */
    int updateFans(String userId,int fansCount);

    /**
     *
     *  @author  yikang
     *  @date  2019/3/25
     *  @param
     *  @return
     */
    int updateFollows(String userId,int followCount);

    /**
     * 更新用户的博客数
     *
     * @param userId      用户id
     * @param updateCount 更新的数量，增加则为正数，删除为负数
     * @return 操作失败返回0
     *  @Author yikang
     *  @Date 2018/9/10
    */
    int updateBlogCount(String userId, int updateCount);

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
     * @param userId 登录邮箱
     * @param passwd 登录密码
     * @return
     * @Author yikang
     * @Date 2018/9/5
     */
    Result deleteUser(String userId,String passwd);

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
