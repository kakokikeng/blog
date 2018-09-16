package com.yk.blog.data.dao;

import com.yk.blog.domain.dto.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yikang
 * @date 2018/9/3
 */
@Mapper
public interface CommentMapper {

    /**
     * 通过博客id删除评论
     * @author kk
     * @date 2018/9/16
     * @param blogId 博客id
     * @return int
     **/
    int deleteCommentByBlogId(int blogId);

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
     * @param id     评论id
     * @param userId 登录用户id
     * @return
     * @Author yikang
     * @Date 2018/9/4
     */
    int deleteComment(@Param("userId") String userId, @Param("id") int id);

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
