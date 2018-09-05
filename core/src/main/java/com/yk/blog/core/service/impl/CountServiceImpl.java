package com.yk.blog.core.service.impl;

import com.t4f.gaea.dto.Result;
import com.yk.blog.core.service.CountService;
import com.yk.blog.core.service.UserService;
import com.yk.blog.core.utils.GenericResultUtils;
import com.yk.blog.data.dao.BlogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import static com.yk.blog.core.utils.GenericResultUtils.generateResultWithCount;
import static com.yk.blog.core.utils.UserUtils.wrongUserIdResult;

/**
 * @author yikang
 * @date 2018/9/4
 */
@Service
public class CountServiceImpl implements CountService {

    @Autowired
    BlogMapper blogMapper;

    @Autowired
    JedisPool jedisPool;

    @Autowired
    UserService userService;

    @Override
    public Result increaseLikeCount(String userId, int blogId) {
        if (!userService.existUser(userId)) {
            return wrongUserIdResult();
        }
        int count = blogMapper.increaseLikeCount(userId, blogId);
        return generateResultWithCount(count);
    }

    @Override
    public Result increaseReadCount(int blogId) {

        try (Jedis jedis = jedisPool.getResource()) {
            jedis.incrBy(String.valueOf(blogId), 1);
        }

        return null;
    }

    @Override
    public int increaseFans(String userId) {
        return 0;
    }

    @Override
    public int getReadCount(int blogId) {
        return 0;
    }
}
