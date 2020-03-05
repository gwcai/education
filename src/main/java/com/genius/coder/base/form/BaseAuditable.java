package com.genius.coder.base.form;

import org.springframework.data.domain.Auditable;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/5
 */
public interface BaseAuditable<U, ID> extends Auditable<U, ID, LocalDateTime>, Serializable {
    String getCreatedUserName();

    void setCreatedUserName(String createdUserName);

    void setId(ID id);
}
