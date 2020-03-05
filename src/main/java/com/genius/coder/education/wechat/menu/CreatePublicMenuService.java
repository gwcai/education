package com.genius.coder.education.wechat.menu;

import com.fasterxml.jackson.databind.JsonNode;
import com.genius.coder.education.properties.WeChatProperties;
import com.genius.coder.education.redis.RedisService;
import com.genius.coder.education.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.genius.coder.education.wechat.server.util.AccessTokenUtil.WECHAT_TOKEN_KEY;


@Component
@Slf4j
public class CreatePublicMenuService {

    private final static String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";

    private final RedisService redisService;

    private final WeChatProperties weChatProperties;

    public CreatePublicMenuService(RedisService redisService, WeChatProperties weixinProperties) {
        this.redisService = redisService;
        this.weChatProperties = weixinProperties;
    }

    public boolean createMenu() {
        String accesss_t = redisService.get(WECHAT_TOKEN_KEY);
        InputStream menuJsonStream = CreatePublicMenuService.class.getClassLoader().getResourceAsStream("menu.json");
        try {
            assert menuJsonStream != null;
            byte[] bytes = new byte[menuJsonStream.available()];
            IOUtils.readFully(menuJsonStream, bytes);
            String menuJson = new String(bytes, StandardCharsets.UTF_8);
            menuJson = menuJson.replaceAll("appID", weChatProperties.getAppID());
            JsonNode jsonNode = HttpClientUtil.post(CREATE_MENU_URL + accesss_t, menuJson);
            String string = jsonNode.get("errcode").asText();
            return "0".equals(string);
        } catch (IOException e) {
            log.error("创建公众号菜单失败：", e);
            return false;
        } finally {
            try {
                menuJsonStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
