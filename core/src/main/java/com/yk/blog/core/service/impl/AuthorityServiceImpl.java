package com.yk.blog.core.service.impl;

import com.yk.blog.core.constant.Constant;
import com.yk.blog.core.constant.ErrorMessages;
import com.yk.blog.core.dto.GenericResult;
import com.yk.blog.core.dto.LoginReqDTO;
import com.yk.blog.core.dto.Token;
import com.yk.blog.core.dto.Result;
import com.yk.blog.core.service.AuthorityService;
import com.yk.blog.core.service.UserService;
import com.yk.blog.core.utils.GenericResultUtils;
import com.yk.blog.core.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yikang
 * @date 2018/9/13
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    JedisPool jedisPool;

    @Autowired
    UserService userService;

    //TODO 返回的token之后的每次请求都需要让浏览器带过来 否则跳转登录 修改数据库的操作全部需要带token

    @Override
    public GenericResult<Token> login(LoginReqDTO loginReqDTO) {
        if (Utils.noLoginInformation(loginReqDTO)) {
            return GenericResultUtils.genericFailureResult(ErrorMessages.ERROR_LOGIN_INFORMATION.message, ErrorMessages.ERROR_LOGIN_INFORMATION.code);
        }
        GenericResult<Token> result = new GenericResult<>();
        try (Jedis jedis = jedisPool.getResource()) {
            if (loginReqDTO.getPasswd().equals(jedis.hget(Utils.generatePrefix(Constant.EMAIL_WITH_PASSWORD), loginReqDTO.getEmail()))) {
                String token = Utils.generateToken(loginReqDTO.getEmail(), loginReqDTO.getPasswd());
                jedis.hset(Utils.generatePrefix(Constant.TOKEN_WITH_TIMESTAMP), token, String.valueOf(System.currentTimeMillis()));
                String userId = userService.getUserIdByEmail(loginReqDTO.getEmail());
                jedis.hset(Utils.generatePrefix(Constant.TOKEN_WITH_USER_ID),token,userId);
                result.setData(new Token(token));
                return result;
            } else {
                return GenericResultUtils.genericFailureResult(ErrorMessages.ERROR_LOGIN_INFORMATION.message, ErrorMessages.ERROR_LOGIN_INFORMATION.code);
            }
        }
    }

    @Override
    public boolean verifyToken(String token) {
        if (token != null) {
            try(Jedis jedis = jedisPool.getResource()){
                if (jedis.hexists(Utils.generatePrefix(Constant.TOKEN_WITH_TIMESTAMP), token)) {
                    //同时刷新过期时间,即更新时间戳为当前时间
                    jedis.hset(Utils.generatePrefix(Constant.TOKEN_WITH_TIMESTAMP), token, String.valueOf(System.currentTimeMillis()));
                    return true;
                } else {
                    return false;
                }
            }
        }else {
            return false;
        }
    }

    /**
     * 定时任务清除过期的token
     *
     * @Author yikang
     * @Date 2018/9/13
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void clearTimeoutToken() {
        try (Jedis jedis = jedisPool.getResource()) {
            String cursor = "0";
            ScanResult<Map.Entry<String, String>> scanResult = null;
            List<String> timeoutStrings = new ArrayList<>();
            do {
                scanResult = jedis.hscan(Utils.generatePrefix(Constant.TOKEN_WITH_TIMESTAMP), scanResult == null ? cursor : scanResult.getStringCursor());
                List<Map.Entry<String, String>> tokenWithTimestamp = scanResult.getResult();
                for (int i = 0; i < tokenWithTimestamp.size(); i++) {
                    if (System.currentTimeMillis() - Long.parseLong(tokenWithTimestamp.get(i).getValue()) > Constant.TOKEN_TIMED_OUT) {
                        timeoutStrings.add(tokenWithTimestamp.get(i).getKey());
                    }
                }
            } while (!cursor.equals(scanResult.getStringCursor()));

            String[] timeouts = timeoutStrings.toArray(new String[timeoutStrings.size()]);
            jedis.hdel(Utils.generatePrefix(Constant.TOKEN_WITH_TIMESTAMP), timeouts);
            jedis.hdel(Utils.generatePrefix(Constant.TOKEN_WITH_USER_ID), timeouts);
        }
    }

    @Override
    public Result logout(String token) {


        return null;
    }
}
