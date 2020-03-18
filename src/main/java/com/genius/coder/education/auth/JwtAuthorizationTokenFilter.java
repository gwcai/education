package com.genius.coder.education.auth;

import com.genius.coder.education.user.form.AdminUserDetail;
import com.genius.coder.education.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * 每次请求前检查token是否存在并有效
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/16
 */
@Component
public class JwtAuthorizationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String authToken = request.getHeader("token");
        if(StringUtils.isNotBlank(authToken)) {
            AdminUserDetail userDetail = jwtTokenUtil.getUserFromToken(authToken);
            if (null != userDetail) {
                String username = userDetail.getUsername();
                logger.info("checking authentication " + username);
                if (StringUtils.isNotBlank(username) && jwtTokenUtil.containToken(username, authToken)
                        && SecurityContextHolder.getContext().getAuthentication() == null) {
                    if (jwtTokenUtil.validateToken(authToken)) {
                        Collection<? extends GrantedAuthority> authorities = userDetail.getAuthorities();
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userDetail, null, authorities);
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        logger.info("authenticated user " + username + ", setting security context");
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    } else {
                        throw new ServiceException("token失效,请重新登录");
                    }
                }
            }
        }
        chain.doFilter(request, response);
    }
}
