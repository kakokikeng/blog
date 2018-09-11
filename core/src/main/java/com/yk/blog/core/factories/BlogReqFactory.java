package com.yk.blog.core.factories;

import com.yk.blog.core.dto.BlogBaseDTO;
import com.yk.blog.domain.dto.Blog;
import org.springframework.stereotype.Component;

/**
 * @author yikang
 * @date 2018/9/11
 */
@Component
public class BlogReqFactory{

    public Blog createBlogByDto(BlogBaseDTO blogBaseDTO) {
        Blog result = new Blog();
        result.setTitle(blogBaseDTO.getTitle());
        result.setContent(blogBaseDTO.getContent());
        result.setCreateTime(System.currentTimeMillis());
        result.setUserId(blogBaseDTO.getUserId());
        return result;
    }
}

