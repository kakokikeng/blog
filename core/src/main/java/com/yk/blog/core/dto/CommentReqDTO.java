package com.yk.blog.core.dto;

import com.yk.blog.domain.dto.Comment;

import java.sql.Timestamp;

/**
 * @author yikang
 * @date 2018/8/31
 */

public class CommentReqDTO extends CommentBaseDTO{

    public CommentReqDTO(){}

    public Comment changeToComment(){
        Comment comment = new Comment();
        comment.setAttachedUserId(getAttachedId());
        comment.setUserName(getUserName());
        comment.setBlogId(getBlogId());
        comment.setContent(getContent());
        comment.setCreateTime(new Timestamp(System.currentTimeMillis()));
        comment.setUserId(getUserId());
        return comment;
    }


    @Override
    public String toString() {
        return "CommentReqDTO{} " + super.toString();
    }
}
