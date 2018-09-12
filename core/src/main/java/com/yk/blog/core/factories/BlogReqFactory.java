package com.yk.blog.core.factories;

import com.yk.blog.core.dto.BlogBaseDTO;
import com.yk.blog.domain.dto.Blog;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author yikang
 * @date 2018/9/11
 */
@Component
public class BlogReqFactory{

    public Blog createBlogByDto(BlogBaseDTO blogBaseDTO) {
        Blog result = new Blog();
        result.setId(blogBaseDTO.getId());
        result.setTitle(blogBaseDTO.getTitle());
        result.setContent(blogBaseDTO.getContent());
        result.setCreateTime(new Date(System.currentTimeMillis()));
        result.setUserId(blogBaseDTO.getUserId());
        return result;
    }
}

