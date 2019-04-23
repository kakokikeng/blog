package com.yk.blog.core.factories;

import com.yk.blog.core.dto.BlogRespDTO;
import com.yk.blog.domain.dto.Blog;
import org.springframework.stereotype.Component;

/**
 * @author yikang
 * @date 2018/9/11
 */
@Component
public class BlogRespFactory{

    public BlogRespDTO createBlogDtoByBlog(Blog blog) {
        BlogRespDTO result = new BlogRespDTO();
        result.setId(blog.getId());
        result.setCommentCount(blog.getCommentCount());
        result.setCreateTime(blog.getCreateTime());
        result.setLikeCount(blog.getLikeCount());
        result.setReadCount(blog.getReadCount());
        result.setContent(blog.getContent());
        result.setTitle(blog.getTitle());
        result.setUserId(blog.getUserId());
        return result;
    }

}
