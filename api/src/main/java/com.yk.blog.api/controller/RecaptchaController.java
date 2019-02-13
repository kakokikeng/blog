package com.yk.blog.api.controller;



import com.yk.blog.core.dto.RecaptchaDTO;
import com.yk.blog.core.dto.Result;
import com.yk.blog.core.service.RecaptchaService;
import com.yk.blog.core.utils.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 *  redis database 0
 *  前端先访问/imgValidateCode得到图片并返回一个keyId的字符串
 *  然后通过/recaptcha接口并传入RecaptchaDTO来进行验证返回值Result
 *  @Author yikang
 *  @Date 2018/7/5
*/

@RestController
@RequestMapping
public class RecaptchaController {
    @Autowired
    RecaptchaService recaptchaService;

    @Autowired
    ValidateCode validateCode;

    /**
     * 返回Result，包含success、code、message三个字段
     * 需要传入RecaptchaDTO，包含keyId和verifyCode
     *
     * @Author yikang
     * @Date 2018/7/6
     */
    //@ApiOperation("传入key及对应验证码进行验证")
    //@ApiImplicitParams(@ApiImplicitParam(name = "recaptchaDTO",value = "传入的key及对应验证码",required = true,dataType = "RecaptchaDTO"))
    @PostMapping("/validation")
    public Mono<Result> getRecaptcha(@RequestBody RecaptchaDTO recaptchaDTO) {
        return recaptchaService.validate(recaptchaDTO);
    }

    /**
     * 获取验证码的keyId
     *
     * @Author yikang
     * @Date 2018/7/6
     */
    //@ApiOperation("发送请求获取String类型的key，有效期为120s")
    //@ApiImplicitParams(@ApiImplicitParam(name = "exChars",value = "排除在外的字符",required = false))
    @GetMapping("/key")
    public Mono<String> getKeyId(@RequestParam(value = "exChars", required = false) String exChars) {
        return recaptchaService.getKey(exChars);
    }


    /**
     * 生成验证码图片 验证码
     * 带着keyId来请求
     *
     * @Author yikang
     * @Date 2018/7/6
     */
    //@ApiOperation("传入获得的key，获得验证码图片")
    //@ApiImplicitParams(@ApiImplicitParam(name = "keyId",value = "之前请求的key",dataType = "String",required = true))
    @GetMapping(value = "/imgValidateCode/{keyId}")
    public Mono<ResponseEntity<byte[]>> imageValidateCode(@PathVariable("keyId") String keyId,ServerWebExchange exchange) {
        return recaptchaService.imageValidateCode(keyId).map(b -> {
            return ResponseEntity.ok().header("content-type",MediaType.IMAGE_JPEG_VALUE).body(b);
        });
    }


}
