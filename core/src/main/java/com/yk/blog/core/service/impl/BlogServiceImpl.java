package com.yk.blog.core.service.impl;

import com.t4f.gaea.dto.GenericResult;
import com.t4f.gaea.dto.Result;
import com.yk.blog.core.dto.BlogReqDTO;
import com.yk.blog.core.dto.BlogRespDTO;
import com.yk.blog.core.service.BlogService;
import com.yk.blog.core.service.CountService;
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

import static com.yk.blog.core.utils.GenericResultUtils.generateResultWithCount;
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
    CountService countService;

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
                BlogRespDTO tmp = new BlogRespDTO(blog);
                tmp.setReadCount(countService.getReadCount(tmp.getId()));
                data.add(tmp);
            }
        }
        return GenericResultUtils.genericResult(Boolean.TRUE, data);
    }

    @Override
    public GenericResult<BlogRespDTO> getBlogByUserIdAndBlogId(String userId, String blogId) {
        if (!userService.existUser(userId)) {
            return wrongUserIdGenericResult();
        }
        Blog blog = blogMapper.getBlogByUserIdAndBlogId(userId, blogId);
        if (blog != null) {
            BlogRespDTO tmp = new BlogRespDTO(blog);
            tmp.setReadCount(countService.getReadCount(tmp.getId()));
            return GenericResultUtils.genericResult(Boolean.TRUE, tmp);
        } else {
            return GenericResultUtils.genericFailureResult(ErrorMessages.BLOG_NOT_EXIST.message, ErrorMessages.BLOG_NOT_EXIST.code);
        }
    }


    @Override
    public Result deleteBlog(String userId, int blogId) {
        if (!userService.existUser(userId)) {
            return wrongUserIdResult();
        }
        int count = blogMapper.deleteBlog(userId, blogId);
        return generateResultWithCount(count);
    }

    @Override
    public Result updateBlog(String userId, int blogId, BlogReqDTO blog) {
        if (!userService.existUser(userId)) {
            return wrongUserIdResult();
        }
        Blog updateBlog = blog.changeToBlog(false);
        int count = blogMapper.updateBlog(userId, blogId, updateBlog);
        return generateResultWithCount(count);
    }

    @Override
    public Result createBlog(String userId, BlogReqDTO blogReqDTO) {
        if (!userService.existUser(userId)) {
            return wrongUserIdResult();
        }
        Blog blog = blogReqDTO.changeToBlog(true);
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("blog",blog);
        int count = blogMapper.createBlog(map);
        return generateResultWithCount(count);
    }
}
