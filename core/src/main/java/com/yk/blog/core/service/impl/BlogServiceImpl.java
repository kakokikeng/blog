package com.yk.blog.core.service.impl;

import com.t4f.gaea.dto.GenericResult;
import com.t4f.gaea.dto.Result;
import com.yk.blog.core.dto.BlogReqDTO;
import com.yk.blog.core.dto.BlogRespDTO;
import com.yk.blog.core.service.BlogService;
import com.yk.blog.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yikang
 * @date 2018/9/3
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    UserService userService;

    @Override
    public GenericResult<List<BlogRespDTO>> getBlogsByUserId(String userId) {

        GenericResult<List<BlogRespDTO>> result = new GenericResult<>();
        if(userId.isEmpty() || !userService.existUser(userId)){
            result.setSuccess(false);
            return result;
        }
        List<BlogRespDTO> blogList = new ArrayList<>();

        return result;
    }

    @Override
    public GenericResult<BlogRespDTO> getBlogByUserIdAndBlogId(String userId, String blogId) {
        return null;
    }

    @Override
    public Result increaseLikeCount(String userId, int blogId) {
        return null;
    }

    @Override
    public Result deleteBlog(String userId, int blogId) {
        return null;
    }

    @Override
    public Result updateBlog(String userId, int blogId, BlogReqDTO blog) {
        return null;
    }

    @Override
    public Result createBlog(String userId, BlogReqDTO blog) {
        return null;
    }
}
