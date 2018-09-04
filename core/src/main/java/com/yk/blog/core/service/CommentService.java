package com.yk.blog.core.service;

import com.t4f.gaea.dto.GenericResult;
import com.t4f.gaea.dto.Result;
import com.yk.blog.core.dto.CommentReqDTO;
import com.yk.blog.core.dto.CommentRespDTO;
import io.swagger.annotations.ApiParam;

import java.util.List;

/**
 * @author yikang
 * @date 2018/9/3
 */

public interface CommentService {

    /**
     *  通过登录用户id、被回复用户id和博客id进行评论，直接评论被回复用户为空
     * @param commentReqDTO 评论信息
     * @return 评论结果
     *  @Author yikang
     *  @Date 2018/9/4
    */
    Result comment(CommentReqDTO commentReqDTO);

    /**
     *  通过博客id获取所有评论
     * @param blogId 博客id
     * @return 评论结果
     *  @Author yikang
     *  @Date 2018/9/4
     */
    GenericResult<List<CommentRespDTO>> getComments(int blogId);

    /**
     *  删除评论
     * @param userId 登录用户id
     * @param commentId 博客id
     * @return 删除结果
     *  @Author yikang
     *  @Date 2018/9/4
     */
    Result deleteComment(String userId, int commentId);

}
