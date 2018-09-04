package com.yk.blog.data.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author yikang
 * @date 2018/9/3
 */
@Mapper
public interface FollowerMapper {

    /**
     * 进行关注插入操作
     *
     * @param followId   进行关注的用户id
     * @param followedId 被关注用户的id
     * @return 返回操作记录数
     * @Author yikang
     * @Date 2018/9/4
     */
    int insertFollowRecord(String followId, String followedId);

    /**
     * 通过关注人和被关注人id获得记录id
     *
     * @param followId   进行关注的用户id
     * @param followedId 被关注的用户id
     * @return 记录id
     * @Author yikang
     * @Date 2018/9/4
     */
    Integer getIdByFollowAndFollower(String followId, String followedId);

    /**
     *  取消关注，删除关注记录
     * @param recordId 记录id
     * @return
     *  @Author yikang
     *  @Date 2018/9/4
    */
    int deleteRecord(int recordId);

    /**
     *  批量获取关注我的用户id
     * @param userId 用户id
     * @return 关注我的用户id列表
     *  @Author yikang
     *  @Date 2018/9/4
    */
    List<String> getFollowers(String userId);

    /**
     *  批量获取关注我的用户id
     * @param userId 用户id
     * @return 我关注的用户id列表
     *  @Author yikang
     *  @Date 2018/9/4
     */
    List<String> getFolloweders(String userId);


}
