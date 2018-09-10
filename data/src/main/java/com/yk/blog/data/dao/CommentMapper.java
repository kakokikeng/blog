package com.yk.blog.data.dao;

import com.yk.blog.domain.dto.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author yikang
 * @date 2018/9/3
 */
@Mapper
public interface CommentMapper {

    /**
     * 插入评论
     *
     * @param comment 评论信息
     * @return
     * @Author yikang
     * @Date 2018/9/4
     */
    int insertComment(Comment comment);

    /**
     * 删除评论
     *
     * @param commentId 评论id
     * @param userId    登录用户id
     * @return
     * @Author yikang
     * @Date 2018/9/4
     */
    int deleteComment(String userId, int commentId);

    /**
     * 查询博客评论
     *
     * @param blogId 博客id
     * @return
     * @Author yikang
     * @Date 2018/9/4
     */
    List<Comment> getComments(int blogId);

}
