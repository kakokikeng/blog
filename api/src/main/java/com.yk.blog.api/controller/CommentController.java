package com.yk.blog.api.controller;

import com.yk.blog.core.dto.GenericResult;
import com.yk.blog.core.dto.Result;
import com.yk.blog.core.dto.CommentReqDTO;
import com.yk.blog.core.dto.CommentRespDTO;
import com.yk.blog.core.service.CommentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yikang
 * @date 2018/8/29
 */
@RestController
@RequestMapping("comment")
public class CommentController {

    //TODO 修改数据库的操作全部需要带token

    @Autowired
    CommentService commentService;

    @ApiOperation("通过登录用户id、被回复用户id和博客id进行评论，直接评论被回复用户为空")
    @PostMapping
    public Result comment(@ApiParam("评论相关信息") CommentReqDTO commentReqDTO) {
        return commentService.comment(commentReqDTO);
    }

    @ApiOperation("通过博客id获取所有评论")
    @GetMapping("{blogId}/comments")
    public GenericResult<List<CommentRespDTO>> getComments(@ApiParam("博客id") @PathVariable("blogId") int blogId) {
        return commentService.getComments(blogId);
    }

    @ApiOperation("删除评论")
    @DeleteMapping("{userId}/{blogId}/{commentId}")
    public Result deleteComment(@ApiParam("登录用户id") @PathVariable("userId") String userId,
                                @ApiParam("博客id") @PathVariable("blogId") int blogId,
                                @ApiParam("评论id") @PathVariable("commentId") int commentId) {
        return commentService.deleteComment(userId,blogId, commentId);
    }
}
