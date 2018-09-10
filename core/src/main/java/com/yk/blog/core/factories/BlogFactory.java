package com.yk.blog.core.factories;

import com.yk.blog.core.dto.BlogRespDTO;
import com.yk.blog.domain.dto.Blog;


/**
 * @author yikang
 * @date 2018/9/6
 */

public class BlogFactory {

    public BlogRespDTO createRespDtoByBlog(Blog blog){
        return new BlogRespDTO(blog);
    }

}
