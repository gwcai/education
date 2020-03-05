package com.genius.coder.education.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2019/8/22
 */
@PropertySource("classpath:/alibaba.properties")
@ConfigurationProperties(prefix = "ali")
@Data
@Component
public class AliProperties {

    private String accessKeyId;

    private String secret;

    private String signName;

    private String templateCode;

    private AliPayProperties aliPay = new AliPayProperties();

}
