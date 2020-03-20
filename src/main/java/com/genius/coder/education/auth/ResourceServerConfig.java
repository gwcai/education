package com.genius.coder.education.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**资源服务配置
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/20
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 所以在我们的app登录的时候我们只要提交的action，不要跳转到登录页
        http.formLogin()
                //登录页面
                .loginPage("/login")
                //登录提交action，app会用到
                // 用户名登录地址
                .loginProcessingUrl("/auth/token")
                //成功处理器 返回Token
                .successHandler(loginSuccessHandler())
                //失败处理器
                .failureHandler(loginFailureHandler());

        http
                // 手机验证码登录
                .apply(SmsCodeAuthenticationSecurityConfig)
                .and()
                .authorizeRequests()
                //手机验证码登录地址
                .antMatchers("/mobile/token", "/email/token")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/login",
                        "/api/**",
                        "/**/*.js",
                        "/**/*.css",
                        "/**/*.jpg",
                        "/**/*.png",
                        "/**/*.woff2",
                        "/wx/**")
                .permitAll()//以上的请求都不需要认证
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable();
    }

    @Bean
    public SavedRequestAwareAuthenticationSuccessHandler loginSuccessHandler(){
        return (request,response,authentication) ->{
            @Autowired
            private ObjectMapper objectMapper;

            @Autowired
            private ClientDetailsService clientDetailsService;

            @Autowired
            private AuthorizationServerTokenServices authorizationServerTokenServices;

            /*
             * (non-Javadoc)
             *
             * @see org.springframework.security.web.authentication.
             * AuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.
             * HttpServletRequest, javax.servlet.http.HttpServletResponse,
             * org.springframework.security.core.Authentication)
             */
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request,
                                                HttpServletResponse response,
                                                Authentication authentication) throws IOException, ServletException {
                String header = request.getHeader("Authorization");
                String name = authentication.getName();
//        String password = (String) authentication.getCredentials();
                if (header == null || !header.startsWith("Basic ")) {
                    throw new UnapprovedClientAuthenticationException("请求头中无client信息");
                }

                String[] tokens = extractAndDecodeHeader(header, request);
                assert tokens.length == 2;
                String clientId = tokens[0];
                String clientSecret = tokens[1];

                ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

                if (clientDetails == null) {
                    throw new UnapprovedClientAuthenticationException("clientId对应的配置信息不存在:" + clientId);
                } else if (!StringUtils.equals(clientDetails.getClientSecret(), clientSecret)) {
                    throw new UnapprovedClientAuthenticationException("clientSecret不匹配:" + clientId);
                }

                TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientId, clientDetails.getScope(), "custom");

                OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

                OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);

                OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(objectMapper.writeValueAsString(token));
            }

            private String[] extractAndDecodeHeader(String header, HttpServletRequest request) throws IOException {

                byte[] base64Token = header.substring(6).getBytes("UTF-8");
                byte[] decoded;
                try {
                    decoded = Base64.decode(base64Token);
                } catch (IllegalArgumentException e) {
                    throw new BadCredentialsException("Failed to decode basic authentication token");
                }
                String token = new String(decoded, "UTF-8");
                int delim = token.indexOf(":");
                if (delim == -1) {
                    throw new BadCredentialsException("Invalid basic authentication token");
                }
                return new String[]{token.substring(0, delim), token.substring(delim + 1)};
            }
        }
    }

    public loginFailureHandler(){

    }
}