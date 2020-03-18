package com.genius.coder.education.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genius.coder.base.form.BaseDataResponse;
import com.genius.coder.education.user.form.AdminUserDetail;
import com.genius.coder.education.util.JwtUtil;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/16
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        super.setFilterProcessesUrl("/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        // 从输入流中获取到登录的信息
        try {
            AdminUserDetail loginUser = new ObjectMapper().readValue(request.getInputStream(), AdminUserDetail.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword())
            );
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 成功验证后调用的方法
    // 如果验证成功，就生成token并返回
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        AdminUserDetail userDetails = (AdminUserDetail) authResult.getPrincipal();
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();

        final JwtUtil jwtUtil = new JwtUtil();
        String token = jwtUtil.createToken(userDetails);
        // 返回创建成功的token
        // 但是这里创建的token只是单纯的token
        // 按照jwt的规定，最后请求的时候应该是 `Bearer token`
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        String tokenStr = JwtUtil.TOKEN_PREFIX + token;
        response.setHeader("token",tokenStr);

        userDetails.setPassword(null);
        BaseDataResponse ok = BaseDataResponse.ok().token(token).data(userDetails).msg("登录成功");
        String s = new ObjectMapper().writeValueAsString(ok);
        out.write(s);
        out.flush();
        out.close();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        //response.getWriter().write("authentication failed, reason: " + failed.getMessage());

        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        BaseDataResponse respBean = BaseDataResponse.fail().msg("登录失败!");
        if (failed instanceof LockedException) {
            respBean.setMsg("账户被锁定，请联系管理员!");
        } else if (failed instanceof CredentialsExpiredException) {
            respBean.setMsg("密码过期，请联系管理员!");
        } else if (failed instanceof AccountExpiredException) {
            respBean.setMsg("账户过期，请联系管理员!");
        } else if (failed instanceof DisabledException) {
            respBean.setMsg("账户被禁用，请联系管理员!");
        } else if (failed instanceof BadCredentialsException) {
            respBean.setMsg("用户名或者密码输入错误，请重新输入!");
        }
        out.write(new ObjectMapper().writeValueAsString(respBean));
        out.flush();
        out.close();
    }
}
