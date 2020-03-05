package com.genius.coder.education.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@PropertySource("classpath:/weixin.properties")
@ConfigurationProperties(prefix = "wx")
@Component
@Getter
@Setter
public class WeChatProperties {

    private String appID;

    private String appSecret;

    private String token;

    private WxPayProperties pay = new WxPayProperties();
}
