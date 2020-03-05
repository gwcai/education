package com.genius.coder.base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/5
 */
public class BaseController {
    @Autowired
    protected MessageSource messageSource;

    public BaseController() {
    }

    public MessageSource getMessageSource() {
        return this.messageSource;
    }

    protected String getMessage(String key) {
        try {
            return this.messageSource.getMessage(key, (Object[])null, (Locale)null);
        } catch (NoSuchMessageException var3) {
            return "";
        }
    }
}