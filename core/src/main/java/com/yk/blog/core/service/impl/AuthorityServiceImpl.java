package com.yk.blog.core.service.impl;

import com.yk.blog.core.constant.Constant;
import com.yk.blog.core.constant.ErrorMessages;
import com.yk.blog.core.dto.GenericResult;
import com.yk.blog.core.dto.LoginReqDTO;
import com.yk.blog.core.dto.LoginRespDTO;
import com.yk.blog.core.dto.Result;
import com.yk.blog.core.service.AuthorityService;
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

    //TODO 返回的token需要让浏览器带过来 cookie

    @Override
    public GenericResult<LoginRespDTO> login(LoginReqDTO loginReqDTO) {
        if (Utils.noLoginInformation(loginReqDTO)) {
            return GenericResultUtils.genericFailureResult(ErrorMessages.ERROR_LOGIN_INFORMATION.message, ErrorMessages.ERROR_LOGIN_INFORMATION.code);
        }
        GenericResult<LoginRespDTO> result = new GenericResult<>();
        try (Jedis jedis = jedisPool.getResource()) {
            if (loginReqDTO.getToken() != null) {
                if (jedis.hexists(Utils.generatePrefix(Constant.LOGIN_TOKEN_WITH_TIMESTAMP), loginReqDTO.getToken())) {
                    return GenericResultUtils.genericResult(true);
                } else {
                    return GenericResultUtils.genericFailureResult(ErrorMessages.TOKEN_NOT_AVAILABLE.message, ErrorMessages.TOKEN_NOT_AVAILABLE.code);
                }
            } else {
                if (loginReqDTO.getPasswd().equals(jedis.hget(Utils.generatePrefix(Constant.EMAIL_WITH_PASSWORD), loginReqDTO.getEmail()))) {
                    String token = Utils.generateToken(loginReqDTO.getEmail(), loginReqDTO.getPasswd());
                    jedis.hset(Utils.generatePrefix(Constant.LOGIN_TOKEN_WITH_TIMESTAMP), token, String.valueOf(System.currentTimeMillis()));
                    result.setData(new LoginRespDTO(token));
                    return result;
                } else {
                    return GenericResultUtils.genericFailureResult(ErrorMessages.ERROR_LOGIN_INFORMATION.message, ErrorMessages.ERROR_LOGIN_INFORMATION.code);
                }
            }
        }
    }

    /**
     *  定时任务清除过期的token
     *  @Author yikang
     *  @Date 2018/9/13
    */
    @Scheduled(cron = "0 0 0 * * ?")
    public void clearTimeoutToken() {
        try (Jedis jedis = jedisPool.getResource()) {
            String cursor = "0";
            ScanResult<Map.Entry<String, String>> scanResult = null;
            List<String> timeoutStrings = new ArrayList<>();
            do {
                scanResult = jedis.hscan(Utils.generatePrefix(Constant.LOGIN_TOKEN_WITH_TIMESTAMP), scanResult == null ? cursor : scanResult.getStringCursor());
                List<Map.Entry<String, String>> tokenWithTimestamp = scanResult.getResult();
                for (int i = 0; i < tokenWithTimestamp.size(); i++) {
                    if (System.currentTimeMillis() - Long.parseLong(tokenWithTimestamp.get(i).getValue()) > Constant.TOKEN_TIMED_OUT) {
                        timeoutStrings.add(tokenWithTimestamp.get(i).getKey());
                    }
                }
            } while (!cursor.equals(scanResult.getStringCursor()));

            String[] timeouts = timeoutStrings.toArray(new String[timeoutStrings.size()]);
            jedis.hdel(Utils.generatePrefix(Constant.LOGIN_TOKEN_WITH_TIMESTAMP), timeouts);
        }
    }

    @Override
    public Result logout(String token) {


        return null;
    }
}
