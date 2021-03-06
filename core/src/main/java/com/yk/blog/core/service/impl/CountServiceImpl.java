package com.yk.blog.core.service.impl;

import com.yk.blog.core.constant.Constant;
import com.yk.blog.core.constant.ErrorMessages;
import com.yk.blog.core.dto.Result;
import com.yk.blog.core.service.AuthorityService;
import com.yk.blog.core.service.CountService;
import com.yk.blog.core.service.RecordService;
import com.yk.blog.core.service.UserService;
import com.yk.blog.core.utils.GenericResultUtils;
import com.yk.blog.core.utils.Utils;
import com.yk.blog.data.dao.BlogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

import static com.yk.blog.core.utils.GenericResultUtils.generateResultWithCount;
import static com.yk.blog.core.utils.GenericResultUtils.genericNormalResult;

/**
 * @author yikang
 * @date 2018/9/4
 */

@Service
public class CountServiceImpl implements CountService {

    @Autowired
    BlogMapper blogMapper;

    @Autowired
    AuthorityService authorityService;

    @Autowired
    JedisPool jedisPool;

    @Autowired
    UserService userService;

    @Autowired
    RecordService recordService;


    @Override
    public Result increaseLikeCount(int blogId,String token) {
        if(!authorityService.verifyToken(token)){
            return GenericResultUtils.genericNormalResult(false,ErrorMessages.TOKEN_NOT_AVAILABLE.message);
        }
        try (Jedis jedis = jedisPool.getResource()) {
            String userId = jedis.hget(Utils.generatePrefix(Constant.TOKEN_WITH_USER_ID),token);
            recordService.insertRecord(userId,blogId,Constant.SCORE_LIKE_OR_COMMENT);
            boolean liked = jedis.sismember(Utils.generatePrefix(Constant.BLOG_LIKED_RECORD + userId), String.valueOf(blogId));
            if (!liked) {
                if(!jedis.sismember(Utils.generatePrefix(Constant.EXIST_BLOG),String.valueOf(blogId))){
                    return GenericResultUtils.genericNormalResult(false,ErrorMessages.BLOG_NOT_EXIST.message);
                }
                jedis.sadd(Utils.generatePrefix(Constant.BLOG_LIKED_RECORD + userId), String.valueOf(blogId));
                long count = jedis.hincrBy(Utils.generatePrefix(Constant.BLOG_LIKED_COUNT), String.valueOf(blogId), 1);
                int n = blogMapper.updateLikeCount(blogId, (int) count);
                String message = n == 0 ? ErrorMessages.UPDATE_FAILED.message : null;
                return generateResultWithCount(n, message);
            } else {
                return genericNormalResult(false, ErrorMessages.BLOG_ALREADY_LIKED.message);
            }
        }
    }

    @Override
    public Result increaseReadCount(int blogId, String token) {

        try (Jedis jedis = jedisPool.getResource()) {
            if (token != null) {
                String userId = jedis.hget(Utils.generatePrefix(Constant.TOKEN_WITH_USER_ID), token);
                recordService.insertRecord(userId,blogId,Constant.SCORE_READ);
            }
            if(!jedis.sismember(Utils.generatePrefix(Constant.EXIST_BLOG),String.valueOf(blogId))){
                return GenericResultUtils.genericNormalResult(false,ErrorMessages.BLOG_NOT_EXIST.message);
            }
            jedis.hincrBy(Utils.generatePrefix(Constant.BLOG_READ_COUNT), String.valueOf(blogId), 1);
        }

        return genericNormalResult(true);
    }

    /**
     *  每天凌晨2点更新，只有一台服务器更新
     *  @Author yikang
     *  @Date 2018/9/13
    */
    @Scheduled(cron = "0 0 2 * * ?")
    public void cronJob() {
        System.out.println("定时任务开始");
        try (Jedis jedis = jedisPool.getResource()) {
            String updateTime = jedis.get(Utils.generatePrefix(Constant.LATEST_UPDATE_TIME));
            if(updateTime != null){
                long time = Long.parseLong(updateTime);
                //一小时内有其他服务器更新过则直接返回
                if(System.currentTimeMillis() - time < Constant.ONE_HOUR){
                    System.out.println("一小时内更新过");
                    return;
                }
            }
            //更新最近一次的更新时间
            jedis.set(Utils.generatePrefix(Constant.LATEST_UPDATE_TIME),String.valueOf(System.currentTimeMillis()));
            Map<String, String> map = jedis.hgetAll(Utils.generatePrefix(Constant.BLOG_READ_COUNT));
            if(map == null || map.size() == 0){
                System.out.println("map is null...");
                return ;
            }
            Map<String,Object> m = new HashMap<>(1);
            m.put("map",map);
            System.out.println("update...");
            blogMapper.updateReadCountByMap(m);
        }

    }

    @Override
    public int updateBlogCount(String userId, int updateCount) {
        return userService.updateBlogCount(userId, updateCount);
    }

    @Override
    public int updateFans(String userId) {
        try (Jedis jedis = jedisPool.getResource()) {
            long fansCount = jedis.scard(Utils.generatePrefix(Constant.FOLLOWER + userId));
            return userService.updateFans(userId, (int) fansCount);
        }
    }

    @Override
    public int updateFollows(String followId) {
        try (Jedis jedis = jedisPool.getResource()) {
            long followCount = jedis.scard(Utils.generatePrefix(Constant.FOLLOWED + followId));
            return userService.updateFollows(followId, (int) followCount);
        }
    }


    @Override
    public int getReadCount(int blogId) {
        try (Jedis jedis = jedisPool.getResource()) {
            String readCount = jedis.hget(Utils.generatePrefix(Constant.BLOG_READ_COUNT), String.valueOf(blogId));
            if (readCount != null) {
                return Integer.parseInt(readCount);
            } else {
                return 0;
            }
        }
    }
}
