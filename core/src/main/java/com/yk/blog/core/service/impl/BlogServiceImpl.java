package com.yk.blog.core.service.impl;

import com.t4f.gaea.dto.GenericResult;
import com.t4f.gaea.dto.Result;
import com.yk.blog.core.dto.BlogReqDTO;
import com.yk.blog.core.dto.BlogRespDTO;
import com.yk.blog.core.service.BlogService;
import com.yk.blog.core.service.UserService;
import com.yk.blog.core.utils.ErrorMessages;
import com.yk.blog.core.utils.GenericResultUtils;
import com.yk.blog.core.utils.UserUtils;
import com.yk.blog.data.dao.BlogMapper;
import com.yk.blog.domain.dto.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yk.blog.core.utils.UserUtils.wrongUserIdGenericResult;
import static com.yk.blog.core.utils.UserUtils.wrongUserIdResult;

/**
 * @author yikang
 * @date 2018/9/3
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    BlogMapper blogMapper;

    @Autowired
    UserService userService;

    @Override
    public GenericResult<List<BlogRespDTO>> getBlogsByUserId(String userId) {
        if (!userService.existUser(userId)) {
            return wrongUserIdGenericResult();
        }
        List<Blog> blogList = blogMapper.getBlogsByUserId(userId);
        List<BlogRespDTO> data = new ArrayList<>();
        if (blogList != null) {
            for (Blog blog : blogList) {
                data.add(new BlogRespDTO(blog));
            }
        }
        return GenericResultUtils.genericResult(true, data);
    }

    @Override
    public GenericResult<BlogRespDTO> getBlogByUserIdAndBlogId(String userId, String blogId) {
        if (!userService.existUser(userId)) {
            return wrongUserIdGenericResult();
        }
        Blog blog = blogMapper.getBlogByUserIdAndBlogId(userId, blogId);
        if (blog != null) {
            return GenericResultUtils.genericResult(true, new BlogRespDTO(blog));
        } else {
            return GenericResultUtils.genericFailureResult(ErrorMessages.BLOG_NOT_EXIST.message, ErrorMessages.BLOG_NOT_EXIST.code);
        }
    }

    @Override
    public Result increaseLikeCount(String userId, int blogId) {
        if (!userService.existUser(userId)) {
            return wrongUserIdResult();
        }
        int count = blogMapper.increaseLikeCount(userId, blogId);
        if (count > 0) {
            return GenericResultUtils.genericNormalResult(true);
        } else {
            return GenericResultUtils.genericNormalResult(false);
        }
    }

    @Override
    public Result deleteBlog(String userId, int blogId) {
        if (!userService.existUser(userId)) {
            return wrongUserIdResult();
        }
        int count = blogMapper.deleteBlog(userId, blogId);
        if (count > 0) {
            return GenericResultUtils.genericNormalResult(true);
        } else {
            return GenericResultUtils.genericNormalResult(false);
        }
    }

    @Override
    public Result updateBlog(String userId, int blogId, BlogReqDTO blog) {
        if (!userService.existUser(userId)) {
            return wrongUserIdResult();
        }
        Blog updateBlog = genericBlog(blog,false);
        int count = blogMapper.updateBlog(userId, blogId, updateBlog);
        if (count > 0) {
            return GenericResultUtils.genericNormalResult(true);
        } else {
            return GenericResultUtils.genericNormalResult(false);
        }
    }

    private Blog genericBlog(BlogReqDTO blogReqDTO, boolean isInsert) {
        Blog result = new Blog();
        result.setTitle(result.getTitle());
        result.setContent(result.getContent());
        result.setId(result.getId());
        if(isInsert){
            result.setCreateTime(System.currentTimeMillis());
            result.setUserId(blogReqDTO.getUserId());
        }
        return result;
    }


    @Override
    public Result createBlog(String userId, BlogReqDTO blogReqDTO) {
        if (!userService.existUser(userId)) {
            return wrongUserIdResult();
        }
        Blog blog = genericBlog(blogReqDTO,true);
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("blog",blog);
        int count = blogMapper.createBlog(map);
        if (count > 0) {
            return GenericResultUtils.genericNormalResult(true);
        } else {
            return GenericResultUtils.genericNormalResult(false);
        }
    }
}
