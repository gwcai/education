package com.genius.coder.education.properties;

import lombok.Data;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2019/8/22
 */
@Data
public class AliPayProperties {
    private String appId;
    private String privateKey;
    private String publicKey;
    private String gatewayUrl;
    private String appGatewayUrl;
    private String returnUrl;
    private String notifyUrl;
    private String sign;
    private String signType;
    private String charset;
    private String method;
    private String version;
    private String format = "json";

}
