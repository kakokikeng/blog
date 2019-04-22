package com.yk.blog.core.service.impl;

import com.yk.blog.core.constant.Constant;
import com.yk.blog.core.dto.GenericResult;
import com.yk.blog.core.dto.Result;
import com.yk.blog.core.dto.UserRespDTO;
import com.yk.blog.core.service.AuthorityService;
import com.yk.blog.core.service.CountService;
import com.yk.blog.core.service.FollowerService;
import com.yk.blog.core.service.UserService;
import com.yk.blog.core.constant.ErrorMessages;
import com.yk.blog.core.utils.GenericResultUtils;
import com.yk.blog.core.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;

import static com.yk.blog.core.constant.Constant.*;
import static com.yk.blog.core.utils.GenericResultUtils.generateResultWithCount;
import static com.yk.blog.core.utils.UserUtils.getIdList;
import static com.yk.blog.core.utils.Utils.generatePrefix;

/**
 * @author yikang
 * @date 2018/9/3
 */
@Service
public class FollowerServiceImpl implements FollowerService {

    @Autowired
    JedisPool jedisPool;

    @Autowired
    AuthorityService authorityService;

    @Autowired
    UserService userService;

    @Autowired
    CountService countService;

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Result follow(String followedId, String token,boolean messagePush) {
        if (!authorityService.verifyToken(token)) {
            return GenericResultUtils.genericNormalResult(false, ErrorMessages.TOKEN_NOT_AVAILABLE.message);
        }
        if (!userService.existUser(followedId)) {
            return GenericResultUtils.genericNormalResult(Boolean.FALSE, ErrorMessages.WRONG_USER_ID.message);
        }
        try (Jedis jedis = jedisPool.getResource()) {
            String followId = jedis.hget(Utils.generatePrefix(Constant.TOKEN_WITH_USER_ID),token);
            if (followId.equals(followedId)) {
                return GenericResultUtils.genericNormalResult(false, ErrorMessages.CAN_NOT_FOLLOW_YOURSELF.message);
            }
            long count = jedis.sadd(generatePrefix(FOLLOWED + followId), followedId);
            jedis.sadd(generatePrefix(FOLLOWER + followedId), followId);
            if (count > 0) {
                if (countService.updateFans(followedId) > 0 && countService.updateFollows(followId) > 0
                ) {
                    if(messagePush){
                        //todo 加入消息推送队列
                    }

                    //todo 生成消息中心实体存入数据库


                    return generateResultWithCount(count);
                } else {
                    throw new RuntimeException(ErrorMessages.ERROR_INCREASE_FANS.message);
                }
            } else {
                return GenericResultUtils.genericNormalResult(false, ErrorMessages.ALREADY_FOLLOWED.message);
            }
        }
    }

    @Override
    public Result unfollow(String followedId, String token) {
        if (!authorityService.verifyToken(token)) {
            return GenericResultUtils.genericNormalResult(false, ErrorMessages.TOKEN_NOT_AVAILABLE.message);
        }
        if (!userService.existUser(followedId)) {
            return GenericResultUtils.genericNormalResult(Boolean.FALSE, ErrorMessages.WRONG_USER_ID.message);
        }
        try (Jedis jedis = jedisPool.getResource()) {
            String followId = jedis.hget(Utils.generatePrefix(Constant.TOKEN_WITH_USER_ID),token);
            long count = jedis.srem(generatePrefix(FOLLOWED + followId), followedId);
            jedis.srem(generatePrefix(FOLLOWER + followedId), followId);
            if (count > 0) {
                countService.updateFans(followedId);
                countService.updateFollows(followId);
                //todo 取消消息推送
                //todo 生成消息中心实体存入数据库
                return generateResultWithCount(count);
            } else {
                return GenericResultUtils.genericNormalResult(Boolean.FALSE, ErrorMessages.NOT_FOLLOWED.message);
            }
        }
    }

    @Override
    public GenericResult<List<UserRespDTO>> getFollowers(String userId) {
        if (!userService.existUser(userId)) {
            return GenericResultUtils.genericFailureResult(ErrorMessages.WRONG_USER_ID.message);
        }
        List<UserRespDTO> data = new ArrayList<>();
        try (Jedis jedis = jedisPool.getResource()) {
            List<String> userIdList = new ArrayList<>(jedis.smembers(generatePrefix(FOLLOWER + userId)));
            if (userIdList.size() > 0) {
                data = userService.getUserListByIdList(userIdList);
            }
        }
        return GenericResultUtils.genericResult(Boolean.TRUE, data);
    }

    @Override
    public GenericResult<List<UserRespDTO>> getFolloweders(String userId) {
        if (!userService.existUser(userId)) {
            return GenericResultUtils.genericFailureResult(ErrorMessages.WRONG_USER_ID.message);
        }
        List<UserRespDTO> data = new ArrayList<>();
        try (Jedis jedis = jedisPool.getResource()) {
            List<String> userIdList = new ArrayList<>(jedis.smembers(generatePrefix(FOLLOWED + userId)));
            if (userIdList.size() > 0) {
                data = userService.getUserListByIdList(userIdList);
            }
        }
        return GenericResultUtils.genericResult(Boolean.TRUE, data);
    }
}
