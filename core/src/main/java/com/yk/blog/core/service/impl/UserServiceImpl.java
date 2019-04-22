package com.yk.blog.core.service.impl;

import com.yk.blog.core.constant.Constant;
import com.yk.blog.core.constant.ErrorMessages;
import com.yk.blog.core.dto.*;
import com.yk.blog.core.service.UserService;
import com.yk.blog.core.utils.GenericResultUtils;
import com.yk.blog.core.utils.UserUtils;
import com.yk.blog.core.utils.Utils;
import com.yk.blog.data.dao.UserMapper;
import com.yk.blog.domain.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author yikang
 * @date 2018/9/3
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    JedisPool jedisPool;

    @Override
    public UserRespDTO getLoginUserInfo(String token) {
        try(Jedis jedis = jedisPool.getResource()){
            String email = jedis.hget(Utils.generatePrefix(Constant.TOKEN_WITH_EMAIL),token);
            return getUserMessageByEmail(email);
        }
    }

    @Override
    public UserRespDTO getUserMessageByEmail(String email) {
        User user = userMapper.getUserByEmail(email);
        return new UserRespDTO(user);
    }

    @Override
    public boolean existUser(String userId) {
        if (userId.isEmpty() || userMapper.existUser(userId) == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String getUserIdByEmail(String email) {
        return userMapper.getUserIdByEmail(email);
    }

    @Override
    public Result modifyPasswd(String email, String oldPasswd, String newPasswd) {
        try (Jedis jedis = jedisPool.getResource()) {
            String oldMD5 = Utils.generateMd5(oldPasswd);
            String newMD5 = Utils.generateMd5(newPasswd);
            if (oldMD5.equals(jedis.hget(Utils.generatePrefix(Constant.EMAIL_WITH_PASSWORD), email))) {
                int count = userMapper.updatePasswd(email, newMD5);
                if (count > 0) {
                    jedis.hset(Utils.generatePrefix(Constant.EMAIL_WITH_PASSWORD), email, newMD5);
                }
                return GenericResultUtils.generateResultWithCount(count, ErrorMessages.UPDATE_FAILED.message);
            } else {
                return GenericResultUtils.genericNormalResult(false, ErrorMessages.ERROR_PASSWORD.message);
            }
        }
    }

    @Override
    public Result modifyPasswd(String email, String newPasswd) {
        try (Jedis jedis = jedisPool.getResource()) {
            String newMD5 = Utils.generateMd5(newPasswd);
            int count = userMapper.updatePasswd(email, newMD5);
            if (count > 0) {
                jedis.hset(Utils.generatePrefix(Constant.EMAIL_WITH_PASSWORD), email, newMD5);
            }
            return GenericResultUtils.generateResultWithCount(count, ErrorMessages.UPDATE_FAILED.message);
        }
    }

    @Override
    public int updateFans(String userId, int fansCount) {
        return userMapper.updateFans(userId, fansCount);
    }

    @Override
    public int updateFollows(String userId, int followCount) {
        return userMapper.updateFollows(userId,followCount);
    }

    @Override
    public int updateBlogCount(String userId, int updateCount) {
        return userMapper.updateBlogCountByUserId(userId, updateCount);
    }

    @Override
    public boolean existUsers(List<String> userIds) {
        if (userIds == null || userIds.size() == 0) {
            return true;
        }
        Set<String> set = new HashSet<>();
        for (String userId : userIds) {
            set.add(userId);
        }
        List<String> list = userMapper.existUsers(userIds);
        if (list.size() != set.size()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public GenericResult<List<UserRespDTO>> getUsers(List<String> userIds) {
        return GenericResultUtils.genericResult(true, getUserListByIdList(userIds));
    }

    @Override
    public List<UserRespDTO> getUserListByIdList(List<String> userIds) {
        List<UserRespDTO> result = new ArrayList<>();
        if (userIds == null || userIds.size() == 0) {
            return result;
        }
        List<User> list = userMapper.getUserListByIdList(userIds);
        result = UserUtils.changeUserListToDTOList(list);
        return result;
    }

    @Override
    public Result updateUser(String userId, UserReqDTO userReqDTO) {
        int count = userMapper.updateUser(userId, userReqDTO.changeToUser(userId));
        return GenericResultUtils.generateResultWithCount(count);
    }

    @Override
    public Result deleteUser(String email, String passwd) {

        try (Jedis jedis = jedisPool.getResource()) {
            if (Utils.generateMd5(passwd).equals(jedis.hget(Utils.generatePrefix(Constant.EMAIL_WITH_PASSWORD), email))) {
                int count = userMapper.deleteUser(email);
                if (count > 0) {
                    jedis.hdel(Utils.generatePrefix(Constant.EMAIL_WITH_PASSWORD), email);
                }
                return GenericResultUtils.generateResultWithCount(count);
            } else {
                return GenericResultUtils.genericNormalResult(false, ErrorMessages.ERROR_PASSWORD.message);
            }
        }

    }

    @Override
    public Result createUser(UserReqDTO userReqDTO) {
        try (Jedis jedis = jedisPool.getResource()) {
            User user = userReqDTO.changeToUser();
            if (jedis.hget(Utils.generatePrefix(Constant.EMAIL_WITH_PASSWORD), user.getEmail()) == null) {
                int count = userMapper.insertUser(user);
                if (count > 0) {
                    jedis.hset(Utils.generatePrefix(Constant.EMAIL_WITH_PASSWORD), user.getEmail(), user.getPasswd());
                }
                return GenericResultUtils.generateResultWithCount(count);
            } else {
                return GenericResultUtils.genericNormalResult(false, ErrorMessages.EMAIL_ALREADY_USED.message);
            }

        }

    }
}
