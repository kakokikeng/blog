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

    @Autowired
    EmailUtils emailUtils;

    @Override
    public Result generateVerifyCode(String email) {

        try (Jedis jedis = jedisPool.getResource()) {
            String times = jedis.get(Utils.generatePrefix(Constant.EMAIL_WITH_COUNT + email));
            if (times != null) {
                if (Integer.parseInt(times) > Constant.MAX_TIMES_PER_MIN) {
                    return GenericResultUtils.genericNormalResult(false, ErrorMessages.REQUEST_TOO_FREQUENTLY.message);
                }
                jedis.setex(Utils.generatePrefix(Constant.EMAIL_WITH_COUNT + email),Constant.MINUTE,String.valueOf(Integer.parseInt(times) + 1));
            } else {
                jedis.setex(Utils.generatePrefix(Constant.EMAIL_WITH_COUNT + email),Constant.MINUTE,Constant.VALUE_1);
            }
            String verifyCode = Utils.generateVerifyCode(Constant.VERIFY_CODE_LENGTH);
            jedis.setex(Utils.generatePrefix(Constant.EMAIL_WITH_VERIFY_CODE + email), Constant.TIME_OUT_SECONDS, verifyCode);
            emailUtils.sendEmail(email, verifyCode);
        }
        return GenericResultUtils.genericNormalResult(true);
    }

    @Override
    public Result validation(String email, String verifyCode) {
        try (Jedis jedis = jedisPool.getResource()) {
            String trueCode = jedis.get(Utils.generatePrefix(Constant.EMAIL_WITH_VERIFY_CODE + email));
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
