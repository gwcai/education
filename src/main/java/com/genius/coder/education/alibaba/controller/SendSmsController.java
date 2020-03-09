package com.genius.coder.education.alibaba.controller;

import com.genius.coder.base.form.BaseDataResponse;
import com.genius.coder.education.alibaba.service.SendSmsService;
import com.genius.coder.education.redis.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sms")
@Api(value = "发送短信接口", tags = {"发送短信接口"})
public class SendSmsController {

    private final SendSmsService sendSmsService;

    private final RedisService redisService;

    public SendSmsController(SendSmsService sendSmsService, RedisService redisService) {
        this.sendSmsService = sendSmsService;
        this.redisService = redisService;
    }

    @GetMapping("code/{phoneNum}")
    @ApiOperation("发送验证码接口")
    @ApiParam(name = "phoneNum", required = true, value = "接收验证码的手机号")
    public BaseDataResponse sendVerificationCode(@PathVariable String phoneNum) {
        String code = RandomStringUtils.randomNumeric(6);
        boolean success = sendSmsService.sendVerificationCode(phoneNum, code);
        if (success) {
            redisService.setex(phoneNum, code);
            return BaseDataResponse.ok().msg("发送成功!");
        }
        return BaseDataResponse.fail().msg("发送失败!");
    }

}
