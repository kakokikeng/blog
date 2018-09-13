package com.yk.blog.core.service.impl;

import com.yk.blog.core.constant.Constant;
import com.yk.blog.core.dto.GenericResult;
import com.yk.blog.core.dto.Result;
import com.yk.blog.core.dto.BlogReqDTO;
import com.yk.blog.core.dto.BlogRespDTO;
import com.yk.blog.core.factories.BlogReqFactory;
import com.yk.blog.core.factories.BlogRespFactory;
import com.yk.blog.core.service.BlogService;
import com.yk.blog.core.service.CountService;
import com.yk.blog.core.service.UserService;
import com.yk.blog.core.constant.ErrorMessages;
import com.yk.blog.core.utils.GenericResultUtils;
import com.yk.blog.core.utils.Utils;
import com.yk.blog.data.dao.BlogMapper;
import com.yk.blog.domain.dto.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;

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
    JedisPool jedisPool;

    @Autowired
    UserService userService;

    @Autowired
    BlogRespFactory blogRespFactory;

    @Autowired
    BlogReqFactory blogReqFactory;

    @Override
    public void updateBlogCommentCount(int id, int count) {
        blogMapper.updateBlogCommentCount(id,count);
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


    @Override
    public Result deleteBlog(String userId, int blogId) {
        if (!userService.existUser(userId)) {
            return wrongUserIdResult();
        }
        int count = blogMapper.deleteBlog(userId, blogId);
        if(count > 0){
            try(Jedis jedis = jedisPool.getResource()){
                jedis.srem(Utils.generatePrefix(Constant.EXIST_BLOG),String.valueOf(blogId));
                jedis.hdel(Utils.generatePrefix(Constant.BLOG_READ_COUNT),String.valueOf(blogId));
                jedis.hdel(Utils.generatePrefix(Constant.BLOG_COMMENT_COUNT),String.valueOf(blogId));
            }
            countService.updateBlogCount(userId,-1);
        }
        return generateResultWithCount(count);
    }

    @Override
    public Result updateBlog(BlogReqDTO blogReqDTO) {
        if (!userService.existUser(blogReqDTO.getUserId())) {
            return wrongUserIdResult();
        }
        Blog updateBlog = blogReqFactory.createBlogByDto(blogReqDTO);
        int count = blogMapper.updateBlog(updateBlog);
        return generateResultWithCount(count);
    }

    @Override
    public Result createBlog(BlogReqDTO blogReqDTO) {
        if (!userService.existUser(blogReqDTO.getUserId())) {
            return wrongUserIdResult();
        }
        Blog blog = blogReqFactory.createBlogByDto(blogReqDTO);
        int count = blogMapper.createBlog(blog);
        if(count > 0){
            try(Jedis jedis = jedisPool.getResource()){
                jedis.sadd(Utils.generatePrefix(Constant.EXIST_BLOG),String.valueOf(blog.getId()));
            }
            countService.updateBlogCount(blogReqDTO.getUserId(),1);
        }
        return generateResultWithCount(count);
    }
}
