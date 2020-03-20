package com.genius.coder.education.auth;

import com.genius.coder.education.user.service.AdminUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

/**认证服务配置
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/20
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private AdminUserDetailService userDetailsService;

    @Autowired
    public AuthorizationServerConfig(AdminUserDetailService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.userDetailsService(userDetailsService);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()//配置内存中，也可以是数据库
                .withClient("education")//clientId
                .secret("education-secret")
                .accessTokenValiditySeconds(24*3600)//token有效时间  秒
                .authorizedGrantTypes("refresh_token", "password", "authorization_code")//token模式
                .scopes("all")//限制允许的权限配置

                .and()//下面配置第二个应用   （不知道动态的是怎么配置的，那就不能使用内存模式，应该使用数据库模式来吧）
                .withClient("weChat")
                .scopes("weChat")
                .accessTokenValiditySeconds(24*3600)
                .scopes("all");
    }
}
