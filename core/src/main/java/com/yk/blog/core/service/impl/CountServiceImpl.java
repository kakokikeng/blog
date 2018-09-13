package com.yk.blog.core.service.impl;

import com.yk.blog.core.dto.Result;
import com.yk.blog.core.service.CountService;
import com.yk.blog.core.service.UserService;
import com.yk.blog.core.constant.Constant;
import com.yk.blog.core.constant.ErrorMessages;
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
        try (Jedis jedis = jedisPool.getResource()) {
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
    public Result increaseReadCount(int blogId) {

        try (Jedis jedis = jedisPool.getResource()) {
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
    @Scheduled(cron = "0/30 * * * * ?")
    public void cronJob() {
        System.out.println("定时任务开始");
        try (Jedis jedis = jedisPool.getResource()) {
            String updateTime = jedis.get(Constant.LATEST_UPDATE_TIME);
            if(updateTime != null){
                long time = Long.parseLong(updateTime);
                //一小时内有其他服务器更新过则直接返回
                if(System.currentTimeMillis() - time < Constant.ONE_HOUR){
                    System.out.println("一小时内更新过");
                    return;
                }
            }
            Map<String, String> map = jedis.hgetAll(Utils.generatePrefix(Constant.BLOG_READ_COUNT));
            if(map == null || map.size() == 0){
                System.out.println("map is null...");
                return ;
            }
            Map<String,Object> m = new HashMap<>();
            m.put("map",map);
            System.out.println("update...");
            blogMapper.updateReadCountByMap(m);
            //更新最近一次的更新时间
            jedis.set(Constant.LATEST_UPDATE_TIME,String.valueOf(System.currentTimeMillis()));
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
