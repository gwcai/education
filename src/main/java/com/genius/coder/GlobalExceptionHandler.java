package com.genius.coder;

import com.genius.coder.base.form.BaseDataResponse;
import com.genius.coder.base.mvc.BaseWebException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/5
 */
@RestControllerAdvice("com.genius.coder")
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BaseDataResponse defaultErrorHandler(HttpServletRequest req, Exception e) {
        log.error(e.getMessage());
        e.printStackTrace();
        if (e instanceof InsufficientAuthenticationException) {
            return BaseDataResponse.fail().msg("登录超时,请重新登录!");
        } else if (e instanceof BadCredentialsException) {
            return BaseDataResponse.fail().msg("账号或密码有误!");
        } else if (e instanceof AuthenticationException) {
            return BaseDataResponse.fail().msg("对不起，您无权访问此资源!");
        } else if (e instanceof BaseWebException) {
            return BaseDataResponse.fail().msg(e.getMessage());
        }
        return BaseDataResponse.fail().detail(e.getMessage()).msg("系统发生错误!");
    }
}
