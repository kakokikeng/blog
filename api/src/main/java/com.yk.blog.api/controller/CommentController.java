package com.yk.blog.api.controller;

import com.yk.blog.core.dto.CommentReqDTO;
import com.yk.blog.core.dto.CommentRespDTO;
import com.yk.blog.core.dto.GenericResult;
import com.yk.blog.core.dto.Result;
import com.yk.blog.core.service.CommentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yikang
 * @date 2018/8/29
 */
@RestController
@RequestMapping("comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @ApiOperation("通过登录用户id、被回复用户id和博客id进行评论，直接评论被回复用户为空")
    @PostMapping
    public Result comment(@ApiParam("评论相关信息") @RequestBody CommentReqDTO commentReqDTO,
                          @ApiParam("用户的token") @RequestParam("token") String token) {
        return commentService.comment(commentReqDTO, token);
    }

    @ApiOperation("通过博客id获取所有评论")
    @GetMapping("{blogId}")
    public GenericResult<List<CommentRespDTO>> getComments(@ApiParam("博客id") @PathVariable("blogId") int blogId) {
        return commentService.getComments(blogId);
    }

    @ApiOperation("删除评论")
    @DeleteMapping("{blogId}/{commentId}")
    public Result deleteComment(@ApiParam("博客id") @PathVariable("blogId") int blogId,
                                @ApiParam("评论id") @PathVariable("commentId") int commentId,
                                @ApiParam("用户的token") @RequestParam("token") String token) {
        return commentService.deleteComment(blogId, commentId, token);
    }
}
