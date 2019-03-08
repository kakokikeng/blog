package com.yk.blog.core.service.impl;

import com.yk.blog.core.constant.Constant;
import com.yk.blog.core.constant.ErrorMessages;
import com.yk.blog.core.dto.BlogReqDTO;
import com.yk.blog.core.dto.BlogRespDTO;
import com.yk.blog.core.dto.GenericResult;
import com.yk.blog.core.dto.Result;
import com.yk.blog.core.factories.BlogReqFactory;
import com.yk.blog.core.factories.BlogRespFactory;
import com.yk.blog.core.service.*;
import com.yk.blog.core.utils.GenericResultUtils;
import com.yk.blog.core.utils.Utils;
import com.yk.blog.data.dao.BlogMapper;
import com.yk.blog.domain.dto.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
public class BlogServiceImpl implements BlogService {

    @Autowired
    BlogMapper blogMapper;

    @Autowired
    CountService countService;

    @Autowired
    AuthorityService authorityService;

    @Autowired
    JedisPool jedisPool;

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    @Autowired
    BlogRespFactory blogRespFactory;

    @Autowired
    BlogReqFactory blogReqFactory;

    @Override
    public GenericResult<List<BlogRespDTO>> getMostInterviewedBlogList() {
        List<Blog> blogs = blogMapper.getMostInterviewedBlogList();
        List<BlogRespDTO> data = new ArrayList<>(blogs.size());
        for (Blog blog : blogs
        ) {
            data.add(new BlogRespDTO(blog));
        }
        return GenericResultUtils.genericResult(true, data);
    }

    @Override
    public void updateBlogCommentCount(int id, int count) {
        blogMapper.updateBlogCommentCount(id, count);
    }

    @Override
    public GenericResult<List<BlogRespDTO>> getBlogsByUserId(String userId) {
        if (!userService.existUser(userId)) {
            return wrongUserIdGenericResult();
        }
        List<Blog> blogList = blogMapper.getBlogsByUserId(userId);
        List<BlogRespDTO> data = new ArrayList<>();
        if (blogList != null) {
            for (Blog blog : blogList) {
                BlogRespDTO tmp = blogRespFactory.createBlogDtoByBlog(blog);
                tmp.setReadCount(countService.getReadCount(tmp.getId()));
                data.add(tmp);
            }
        }
        return GenericResultUtils.genericResult(Boolean.TRUE, data);
    }

    @Override
    public GenericResult<BlogRespDTO> getBlogById(int blogId) {

        Blog blog = blogMapper.getBlogById(blogId);
        if (blog != null) {
            BlogRespDTO tmp = blogRespFactory.createBlogDtoByBlog(blog);
            tmp.setReadCount(countService.getReadCount(tmp.getId()));
            return GenericResultUtils.genericResult(Boolean.TRUE, tmp);
        } else {
            return GenericResultUtils.genericFailureResult(ErrorMessages.BLOG_NOT_EXIST.message, ErrorMessages.BLOG_NOT_EXIST.code);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result deleteBlog(int blogId, String token) {
        if (!authorityService.verifyToken(token)) {
            return GenericResultUtils.genericNormalResult(false, ErrorMessages.TOKEN_NOT_AVAILABLE.message);
        }
        try (Jedis jedis = jedisPool.getResource()) {
            String userId = jedis.hget(Utils.generatePrefix(Constant.TOKEN_WITH_USER_ID), token);
            int count = blogMapper.deleteBlog(userId, blogId);
            if (count > 0) {
                jedis.srem(Utils.generatePrefix(Constant.EXIST_BLOG), String.valueOf(blogId));
                jedis.hdel(Utils.generatePrefix(Constant.BLOG_READ_COUNT), String.valueOf(blogId));
                jedis.hdel(Utils.generatePrefix(Constant.BLOG_COMMENT_COUNT), String.valueOf(blogId));
                jedis.hdel(Utils.generatePrefix(Constant.BLOG_LIKED_COUNT), String.valueOf(blogId));
                jedis.srem(Utils.generatePrefix(Constant.BLOG_LIKED_RECORD + userId), String.valueOf(blogId));
                commentService.deleteCommentByBlogId(blogId);
            }
            countService.updateBlogCount(userId, -1);
            return generateResultWithCount(count);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result updateBlog(BlogReqDTO blogReqDTO, String token) {
        if (!authorityService.verifyToken(token)) {
            return GenericResultUtils.genericNormalResult(false, ErrorMessages.TOKEN_NOT_AVAILABLE.message);
        }
        Blog updateBlog = blogReqFactory.createBlogByDto(blogReqDTO);
        int count = blogMapper.updateBlog(updateBlog);
        //TODO 若要推送博客，更新也需要推送
        return generateResultWithCount(count);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result createBlog(BlogReqDTO blogReqDTO, String token) {
        try (Jedis jedis = jedisPool.getResource()) {
            if (!authorityService.verifyToken(token)) {
                return GenericResultUtils.genericNormalResult(false, ErrorMessages.TOKEN_NOT_AVAILABLE.message);
            }
            String userId = jedis.hget(Utils.generatePrefix(Constant.TOKEN_WITH_USER_ID),token);
            Blog blog = blogReqFactory.createBlogByDto(blogReqDTO);
            blog.setUserId(userId);
            int count = blogMapper.createBlog(blog);
            if (count > 0) {
                jedis.sadd(Utils.generatePrefix(Constant.EXIST_BLOG), String.valueOf(blog.getId()));
                countService.updateBlogCount(userId, 1);
            }
            return generateResultWithCount(count);
        }
    }
}
