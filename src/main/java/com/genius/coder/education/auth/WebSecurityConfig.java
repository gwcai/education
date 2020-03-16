package com.genius.coder.education.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genius.coder.base.form.BaseDataResponse;
import com.genius.coder.education.user.dao.AdminUserDao;
import com.genius.coder.education.user.domain.AdminUser;
import com.genius.coder.education.user.form.AdminUserDetail;
import com.genius.coder.education.user.form.AdminUserForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.PrintWriter;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/9
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //@Autowired
    //VerificationCodeFilter verificationCodeFilter;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/login","/css/**","/js/**","/index.html","/img/**","/fonts/**","/favicon.ico","/user/verifyCode","/wx",
                "/doLogin","/api/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().sameOrigin();
        //http.addFilterBefore(verificationCodeFilter,UsernamePasswordAuthenticationFilter.class);
//        http.authorizeRequests().antMatchers(HttpMethod.GET).permitAll()
//                .antMatchers(HttpMethod.POST).permitAll()
//                .antMatchers("/api/**").permitAll();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .and()
                .formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/auth/login")
                .loginPage("/login")
                .successHandler(loginSuccessHandler())
                .failureHandler(loginFailureHandler())
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout","POST"))
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(logoutSuccessHandler())
                .and().csrf().disable()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager()));
        http.httpBasic().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
        auth.eraseCredentials(false);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() { //密码加密
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationFailureHandler loginFailureHandler(){
        return (req, resp, exception) -> {
            resp.setContentType("application/json;charset=utf-8");
            PrintWriter out = resp.getWriter();
            BaseDataResponse respBean = BaseDataResponse.fail().msg("登录失败!");
            if (exception instanceof LockedException) {
                respBean.setMsg("账户被锁定，请联系管理员!");
            } else if (exception instanceof CredentialsExpiredException) {
                respBean.setMsg("密码过期，请联系管理员!");
            } else if (exception instanceof AccountExpiredException) {
                respBean.setMsg("账户过期，请联系管理员!");
            } else if (exception instanceof DisabledException) {
                respBean.setMsg("账户被禁用，请联系管理员!");
            } else if (exception instanceof BadCredentialsException) {
                respBean.setMsg("用户名或者密码输入错误，请重新输入!");
            }
            out.write(new ObjectMapper().writeValueAsString(respBean));
            out.flush();
            out.close();
        };
    }

    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        return (request, response, authentication) -> {
            AdminUser userDetails = (AdminUser) authentication.getPrincipal();
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            userDetails.setPassword(null);
            BaseDataResponse ok = BaseDataResponse.ok().data(userDetails).msg("登录成功");
            String s = new ObjectMapper().writeValueAsString(ok);
            out.write(s);
            out.flush();
            out.close();
        };
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() { //登出处理
        return (request, response, authentication) -> {
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.write(new ObjectMapper().writeValueAsString(BaseDataResponse.ok().msg("注销成功!")));
            out.flush();
            out.close();
        };
    }

    @Bean
    public UserDetailsService userDetailsService() {    //用户登录实现
        return new UserDetailsService() {
            @Autowired
            private AdminUserDao userInfoDao;

            @Override
            public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
                AdminUser user = userInfoDao.findByUserName(s);
                if (user == null){
                    log.error("用户：" + s + "查找失败");
                    throw new UsernameNotFoundException("Username " + s + " not found");
                }
                return new AdminUserDetail(new AdminUserForm().toForm(user));
            }
        };
    }
}
