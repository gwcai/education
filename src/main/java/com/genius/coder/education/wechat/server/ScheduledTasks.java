package com.genius.coder.education.wechat.server;

import com.fasterxml.jackson.databind.JsonNode;
import com.genius.coder.education.properties.WeChatProperties;
import com.genius.coder.education.redis.RedisService;
import com.genius.coder.education.util.HttpsClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.genius.coder.education.wechat.server.util.AccessTokenUtil.WECHAT_JSAPI_TICKET;
import static com.genius.coder.education.wechat.server.util.AccessTokenUtil.WECHAT_TOKEN_KEY;
import static org.springframework.security.oauth2.common.OAuth2AccessToken.ACCESS_TOKEN;

/**
 * 定时缓存微信API accessToken
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/5
 */
@Component
@EnableScheduling
@Slf4j
public class ScheduledTasks {

    /**
     * 定时刷新周期，默认为100分钟，微信token和jsapi ticket是两小时过期
     */
    private final static long SCHEDULED_TIME = 100 * 60 * 1000;

    private final static String GET_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=$appid&secret=$secret";

    private final static String GET_JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

    private final RedisService redisService;

    private final WeChatProperties weChatProperties;

    public ScheduledTasks(WeChatProperties weixinProperties, RedisService redisService) {
        this.weChatProperties = weixinProperties;
        this.redisService = redisService;
    }

    //100分钟
    @Scheduled(fixedDelay = SCHEDULED_TIME)
    public void refreshApiToken() {
        try {
            JsonNode jsonNode = HttpsClientUtil.get(GET_TOKEN_URL.replace("$appid", weChatProperties.getAppID())
                    .replace("$secret", weChatProperties.getAppSecret()));
            JsonNode resultNode = jsonNode.get(ACCESS_TOKEN);
            if (resultNode == null) {
                log.error("获取微信api accessToken失败：{}", jsonNode.toString());
                return;
            }
            String accessToken = resultNode.asText();
            redisService.setex(WECHAT_TOKEN_KEY, accessToken, 60 * 120);
            log.info("刷新微信api access_token成功!");

            //获取jsapi票据
            getJSAPITicket(accessToken);
        } catch (Exception e) {
            log.error("获取微信api accessToken失败：", e);
        }
    }

    private void getJSAPITicket(String accessToken) {
        try {
            JsonNode jsonNode = HttpsClientUtil.get(GET_JSAPI_TICKET_URL.replace("ACCESS_TOKEN", accessToken));
            int errorCode = jsonNode.get("errcode").asInt();
            if (0 != errorCode) {
                log.error("获取JS临时票据失败：{}", jsonNode.get("errmsg"));
                return;
            }
            String ticket = jsonNode.get("ticket").asText();
            redisService.setex(WECHAT_JSAPI_TICKET, ticket, 60 * 120);
            log.info("刷新js api ticket成功!");
        } catch (Exception e) {
            log.error("获取JS临时票据异常：{}", e.getMessage());
        }
    }
}
