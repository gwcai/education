package com.genius.coder.education.wechat.server.util;

import lombok.experimental.UtilityClass;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/5
 */
@UtilityClass
public class AccessTokenUtil {

    /**
     * wechat api token name
     */
    private static final String ACCESS_TOKEN = "access_token";

    /**
     * wechat api token cache key
     */
    public static final String WECHAT_TOKEN_KEY = "wx_" + ACCESS_TOKEN;

    /***
     * wechat js api ticket cache key
     */
    public static final String WECHAT_JSAPI_TICKET = "wx_jsapi_ticket";


}
