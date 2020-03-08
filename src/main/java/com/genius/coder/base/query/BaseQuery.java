package com.genius.coder.base.query;

import org.springframework.data.domain.Pageable;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/5
 */
public interface BaseQuery<T> {
    Pageable pageable();
    Class<T> entityClassType();
}