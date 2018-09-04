package com.yk.blog.core.service.impl;

import com.t4f.gaea.dto.GenericResult;
import com.t4f.gaea.dto.Result;
import com.yk.blog.core.dto.CommentReqDTO;
import com.yk.blog.core.dto.CommentRespDTO;
import com.yk.blog.core.service.CommentService;
import com.yk.blog.core.service.UserService;
import com.yk.blog.core.utils.ErrorMessages;
import com.yk.blog.core.utils.GenericResultUtils;
import com.yk.blog.data.dao.CommentMapper;
import com.yk.blog.domain.dto.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    UserService userService;

    @Override
    public Result comment(CommentReqDTO commentReqDTO) {
        if (commentNotLegal(commentReqDTO)) {
            return wrongUserIdGenericResult();
        }
        int count = commentMapper.insertComment(commentReqDTO.changeToComment());
        return generateResultWithCount(count);
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
    public Result deleteComment(String userId, int commentId) {
        if (!userService.existUser(userId)) {
            GenericResultUtils.genericNormalResult(Boolean.FALSE, ErrorMessages.WRONG_USER_ID.message);
        }
        int count = commentMapper.deleteComment(userId, commentId);
        return generateResultWithCount(count);
    }
}
