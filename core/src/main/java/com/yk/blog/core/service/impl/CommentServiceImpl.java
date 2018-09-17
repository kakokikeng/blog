package com.yk.blog.core.service.impl;

import com.yk.blog.core.dto.GenericResult;
import com.yk.blog.core.dto.Result;
import com.yk.blog.core.dto.CommentReqDTO;
import com.yk.blog.core.dto.CommentRespDTO;
import com.yk.blog.core.service.AuthorityService;
import com.yk.blog.core.service.BlogService;
import com.yk.blog.core.service.CommentService;
import com.yk.blog.core.service.UserService;
import com.yk.blog.core.constant.Constant;
import com.yk.blog.core.constant.ErrorMessages;
import com.yk.blog.core.utils.GenericResultUtils;
import com.yk.blog.core.utils.Utils;
import com.yk.blog.data.dao.CommentMapper;
import com.yk.blog.domain.dto.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;

import static com.yk.blog.core.utils.GenericResultUtils.generateResultWithCount;
import static com.yk.blog.core.utils.UserUtils.wrongUserIdGenericResult;

/**
 * @author yikang
 * @date 2018/9/3
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    AuthorityService authorityService;

    @Autowired
    UserService userService;

    @Autowired
    BlogService blogService;

    @Autowired
    JedisPool jedispool;


    @Override
    public Result deleteCommentByBlogId(int blogId) {
        return GenericResultUtils.generateResultWithCount(commentMapper.deleteCommentByBlogId(blogId));
    }

    @Override
    public Result comment(CommentReqDTO commentReqDTO, String token) {
        if (commentNotLegal(commentReqDTO)) {
            return wrongUserIdGenericResult();
        }
        if (!authorityService.verifyToken(token)) {
            return GenericResultUtils.genericNormalResult(false, ErrorMessages.TOKEN_NOT_AVAILABLE.message);
        }
        try (Jedis jedis = jedispool.getResource()) {
            int count = commentMapper.insertComment(commentReqDTO.changeToComment());
            if (count > 0) {
                long commentCount = jedis.hincrBy(Utils.generatePrefix(Constant.BLOG_COMMENT_COUNT), String.valueOf(commentReqDTO.getBlogId()), 1);
                blogService.updateBlogCommentCount(commentReqDTO.getBlogId(), (int) commentCount);
            }
            return generateResultWithCount(count);
        }

    }

    private boolean commentNotLegal(CommentReqDTO commentReqDTO) {
        if (commentReqDTO.getAttachedId() != null) {
            List<String> userIds = new ArrayList<>();
            userIds.add(commentReqDTO.getUserId());
            userIds.add(commentReqDTO.getAttachedId());
            return !userService.existUsers(userIds);
        } else {
            return !userService.existUser(commentReqDTO.getUserId());
        }
    }

    @Override
    public GenericResult<List<CommentRespDTO>> getComments(int blogId) {
        List<Comment> comments = commentMapper.getComments(blogId);
        List<CommentRespDTO> datas = new ArrayList<>();
        if (comments != null) {
            for (Comment comment : comments) {
                datas.add(new CommentRespDTO(comment));
            }
        }
        return GenericResultUtils.genericResult(Boolean.TRUE, datas);
    }

    @Override
    public Result deleteComment(int blogId, int commentId, String token) {
        if (!authorityService.verifyToken(token)) {
            return GenericResultUtils.genericNormalResult(false, ErrorMessages.TOKEN_NOT_AVAILABLE.message);
        }
        try (Jedis jedis = jedispool.getResource()) {
            String userId = jedis.hget(Utils.generatePrefix(Constant.TOKEN_WITH_USER_ID), token);
            int count = commentMapper.deleteComment(userId, commentId);
            if (count > 0) {
                long commentCount = jedis.hincrBy(Utils.generatePrefix(Constant.BLOG_COMMENT_COUNT), String.valueOf(blogId), -1);
                blogService.updateBlogCommentCount(blogId, (int) commentCount);
            }
            return generateResultWithCount(count);
        }
    }
}
