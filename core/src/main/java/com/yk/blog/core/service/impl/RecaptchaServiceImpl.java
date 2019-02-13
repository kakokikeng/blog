package com.yk.blog.core.service.impl;


import com.t4f.gaea.dto.Result;
import com.yk.blog.core.service.RecaptchaService;
import com.yk.recaptcha.dto.RecaptchaDTO;
import com.yk.recaptcha.utils.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.UUID;


@Service
public class RecaptchaServiceImpl implements RecaptchaService {

    @Autowired
    private ReactiveRedisTemplate<String, String> template;

    @Autowired
    private ValidateCode validateCode;

    private String key;


    /**
     * 验证传入的RecaptchaDTO中的keyId和verifyCode是否存在redis中
     * 是否正确对应
     *
     * @Author yikang
     * @Date 2018/7/6
     */
    @Override
    public Mono<Result> validate(RecaptchaDTO recaptchaDTO) {
        return template.opsForValue()
                .get(getRedisKey(recaptchaDTO.getKey()))
                .flatMap(str ->
                {
                    if (str.equalsIgnoreCase(recaptchaDTO.getVerifyCode())) {
                        return template.opsForValue()
                                .delete(getRedisKey(recaptchaDTO.getKey()))
                                .flatMap(
                                        del -> Mono.just(getResult(true, 1))
                                );
                    } else {
                        return Mono.just(getResult(false, 2));
                    }
                }).switchIfEmpty(Mono.just(getResult(false, 3)))
                .onErrorReturn(getResult(false, 3));
    }


    private Result getResult(boolean success, int code) {
        Result result = new Result();
        result.setSuccess(success);
        switch (code) {
            case 1:
                result.setMessage("验证通过");
                break;
            case 2:
                result.setMessage("验证码错误");
                break;
            case 3:
                result.setMessage("验证码已过期或不存在");
                break;
        }
        return result;
    }


    @Override
    public Mono<String> getKey(String exChars) {
        key = UUID.randomUUID().toString();
        return validateCode.generateTextCode(exChars)
                .flatMap(str -> {
                    return template.opsForValue().set(getRedisKey(key), str, Duration.ofMinutes(2))
                            .then(Mono.just(key));
                }).onErrorReturn("ERROR");
    }

    /**
     * 传入之前获得的keyId返回对应的验证码图片
     *
     * @Author yikang
     * @Date 2018/7/6
     */
    @Override
    public Mono<byte[]> imageValidateCode(String keyId) {
        return template.opsForValue()
                .get(getRedisKey(keyId))
                .flatMap(s -> {
                    return validateCode.imageByte(s);
                }).onErrorReturn(new byte[0]);
    }

    private String getRedisKey(String key) {
        return "Recaptcha:" + key;
    }
}
