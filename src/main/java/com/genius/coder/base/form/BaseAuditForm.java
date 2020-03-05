package com.genius.coder.base.form;

import lombok.Data;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/5
 */
@Data
public class BaseAuditForm<T extends Persistable<ID>, ID extends Serializable, AdminUserForm extends Serializable> extends BaseForm<T, ID> {
    protected AdminUserForm createdBy;
    protected String createdUserName;
    protected LocalDateTime createdDate;
    protected AdminUserForm lastModifiedBy;
    protected LocalDateTime lastModifiedDate;
}
