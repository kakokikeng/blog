package com.yk.blog.data.dao;

import com.yk.blog.domain.dto.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author yikang
 * @date 2018/9/3
 */
@Mapper
public interface UserMapper {

    /**
     * 更新用户的粉丝数
     *
     * @param fansCount 粉丝总数
     * @param userId    用户id
     * @return
     * @Author yikang
     * @Date 2018/9/10
     */
    int updateFans(String userId, int fansCount);

    /**
     * 更新用户的博客数
     *
     * @param userId   用户id
     * @param addCount 更新的数量，增加则为正数，删除为负数
     * @return 操作失败返回0
     * @Author yikang
     * @Date 2018/9/10
     */
    int updateBlogCountByUserId(String userId, int addCount);

    /**
     * 新增用户
     *
     * @param user 用户信息
     * @return
     * @Author yikang
     * @Date 2018/9/5
     */
    int insertUser(User user);

    /**
     * 删除用户
     *
     * @param userId 用户id
     * @return
     * @Author yikang
     * @Date 2018/9/5
     */
    int deleteUser(String userId);

    /**
     * 更新用户信息
     *
     * @param userId     用户id
     * @param userReqDTO 用户信息
     * @return
     * @Author yikang
     * @Date 2018/9/5
     */
    int updateUser(String userId, User userReqDTO);

    /**
     * 通过用户id列表查询用户信息
     *
     * @param userIds 用户id列表
     * @return 用户信息列表
     * @Author yikang
     * @Date 2018/9/5
     */
    List<User> getUserListByIdList(List<String> userIds);

    /**
     * 通过用户id查询用户是否存在
     *
     * @param userId 用户id
     * @return 返回主键
     * @Author yikang
     * @Date 2018/9/5
     */
    String existUser(String userId);

    /**
     * 通过用户id列表查询用户是否全部存在
     *
     * @param userIds 用户id列表
     * @return 返回主键
     * @Author yikang
     * @Date 2018/9/5
     */
    List<String> existUsers(List<String> userIds);

}
