package com.yk.blog.core.service.impl;

import com.t4f.gaea.dto.Result;
import com.yk.blog.core.constant.Constant;
import com.yk.blog.core.constant.ErrorMessages;
import com.yk.blog.core.service.VerifyService;
import com.yk.blog.core.utils.EmailUtils;
import com.yk.blog.core.utils.GenericResultUtils;
import com.yk.blog.core.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


/**
 * @author yikang
 * @date 2018/9/11
 */
@Service
public class VerifyServiceImpl implements VerifyService {

    @Autowired
    JedisPool jedisPool;

    @Override
    public Result generateVerifyCode(String email) {
        String verifyCode = Utils.generateVerifyCode(Constant.VERIFY_CODE_LENGTH);
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.setex(email, Constant.TIME_OUT_SECONDS, verifyCode);
        }
        EmailUtils emailUtils = new EmailUtils();
        emailUtils.sendEmail(email, verifyCode);
        return GenericResultUtils.genericNormalResult(true);
    }

    @Override
    public Result validation(String email, String verifyCode) {
        try (Jedis jedis = jedisPool.getResource()) {
            String trueCode = jedis.get(email);
            if (trueCode == null) {
                return GenericResultUtils.genericNormalResult(false, ErrorMessages.VERIFY_CODE_TIME_OUT.message);
            } else if (!trueCode.equalsIgnoreCase(verifyCode)) {
                return GenericResultUtils.genericNormalResult(false, ErrorMessages.VERIFY_CODE_ERROR.message);
            } else {
                jedis.expire(email, Constant.EXPIRE_NOW);
                return GenericResultUtils.genericNormalResult(true);
            }
        }
    }
}
