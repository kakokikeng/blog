package com.yk.blog.core.service;

import com.yk.blog.core.dto.GenericResult;
import com.yk.blog.core.dto.Result;
import com.yk.blog.core.dto.CommentReqDTO;
import com.yk.blog.core.dto.CommentRespDTO;

import java.util.List;

/**
 * @author yikang
 * @date 2018/9/3
 */

public interface CommentService {

    /**
     * 删除博客的所有评论
     *
     * @param blogId 被删除博客id
     * @return
     * @Author yikang
     * @Date 2018/9/14
     */
    Result deleteCommentByBlogId(int blogId);

    /**
     * 通过登录用户id、被回复用户id和博客id进行评论，直接评论被回复用户为空
     *
     * @param commentReqDTO 评论信息
     * @param token         用户的token
     * @return 评论结果
     * @Author yikang
     * @Date 2018/9/4
     */
    Result comment(CommentReqDTO commentReqDTO, String token);

    /**
     * 通过博客id获取所有评论
     *
     * @param blogId 博客id
     * @return 评论结果
     * @Author yikang
     * @Date 2018/9/4
     */
    GenericResult<List<CommentRespDTO>> getComments(int blogId);

    /**
     * 删除自己的评论
     *
     * @param blogId    博客id
     * @param commentId 评论id
     * @param token     用户的token
     * @return 删除结果
     * @Author yikang
     * @Date 2018/9/4
     */
    Result deleteComment(int blogId, int commentId, String token);

}
