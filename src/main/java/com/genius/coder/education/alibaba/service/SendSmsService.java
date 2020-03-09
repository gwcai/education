package com.genius.coder.education.alibaba.service;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genius.coder.education.properties.AliProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import java.io.IOException;

/**
 * @author lqb
 * 利用阿里云短信服务，发送手机短信
 */
@Slf4j
@Service
public class SendSmsService {

    private final ObjectMapper objectMapper;

    private final AliProperties aliProperties;

    public SendSmsService(ObjectMapper objectMapper, AliProperties aliProperties) {
        this.objectMapper = objectMapper;
        this.aliProperties = aliProperties;
    }

    /**
     * 发送验证码短信
     *
     * @param phoneNum 接收验证码的手机号
     * @param code     验证码
     * @return true发送成功，false发送失败
     */
    public boolean sendVerificationCode(@Nonnull String phoneNum, @Nonnull String code) {
        Assert.notNull(phoneNum, "手机号不能为空!");
        Assert.notNull(code, "验证码不能空!");
        DefaultProfile profile = DefaultProfile.getProfile("default", aliProperties.getAccessKeyId(), aliProperties.getSecret());
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysConnectTimeout(2000);
        request.setSysReadTimeout(2000);
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + code + "\"}");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNum);
        request.putQueryParameter("SignName", aliProperties.getSignName());
        request.putQueryParameter("TemplateCode", aliProperties.getTemplateCode());
        try {
            CommonResponse response = client.getCommonResponse(request);
            JsonNode jsonNode = objectMapper.readTree(response.getData());
            String dataCode = jsonNode.get("Code").asText();
            boolean success = "OK".equals(dataCode);
            if (!success) {
                log.error("验证码发送失败：手机号：{}，异常：{}", new Object[]{phoneNum, jsonNode});
            }
            return success;
        } catch (ClientException | IOException e) {
            log.error("验证码发送失败：手机号：{}，异常{}", new Object[]{phoneNum, e});
            return false;
        }
    }
}
