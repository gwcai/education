package com.genius.coder.education.wechat.server.controller;

import com.genius.coder.base.form.BaseDataResponse;
import com.genius.coder.education.properties.WeChatProperties;
import com.genius.coder.education.redis.RedisService;
import com.genius.coder.education.wechat.menu.CreatePublicMenuService;
import com.genius.coder.education.wechat.server.util.AesException;
import com.genius.coder.education.wechat.server.util.SHA1;
import com.genius.coder.education.wechat.server.util.Sign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import static com.genius.coder.education.wechat.server.util.AccessTokenUtil.WECHAT_JSAPI_TICKET;


/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/5
 */
@RestController
@RequestMapping({"/"})
@Api(tags = "微信相关接口")
public class WeChatController {
    private final RedisService redisService;
    private final WeChatProperties weChatProperties;
    private final CreatePublicMenuService createPublicMenuService;

    public WeChatController(WeChatProperties weChatProperties, RedisService redisService, CreatePublicMenuService createPublicMenuService) {
        this.weChatProperties = weChatProperties;
        this.redisService = redisService;
        this.createPublicMenuService = createPublicMenuService;
    }

    /**
     * 微信Token验证
     *
     * @param signature 微信加密签名
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @param echostr   随机字符串
     */
    @GetMapping("wx")
    public String getToken(String signature, String timestamp, String nonce, String echostr) {
        String jiami = "";
        try {
            jiami = SHA1.getSHA1(weChatProperties.getToken(), timestamp, nonce, "");//这里是对三个参数进行加密
        } catch (AesException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return e.getMessage();
        }
        if (jiami.equals(signature)) {
            return echostr;
        }
        return jiami;
    }

    /***
     * 获取js api ticket
     * @return
     */
    @PostMapping("wx/signature")
    @ApiOperation("获取微信API访问ticket")
    public Map<String, String> getJSAPISignature(@RequestBody String url) throws UnsupportedEncodingException {
        url = URLDecoder.decode(url, "utf-8");
        String ticket = redisService.get(WECHAT_JSAPI_TICKET);
        Map<String, String> map = Sign.sign(ticket, url);
        map.put("appID", weChatProperties.getAppID());
        return map;
    }

    /**
     * 创建公众号菜单
     *
     * @return
     */
    @PostMapping("wx/menu")
    public BaseDataResponse createMenu() {
        boolean menu = createPublicMenuService.createMenu();
        return BaseDataResponse.ok().data(menu);
    }

    @GetMapping("MP_verify_UUsC6xtZKDN4eFsK.txt")
    public String wechatTxt() {
        return "UUsC6xtZKDN4eFsK";
    }
}
