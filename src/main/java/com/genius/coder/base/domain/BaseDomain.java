package com.genius.coder.base.domain;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.util.StringUtils;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/5
 */
@MappedSuperclass
public class BaseDomain<U extends Serializable,ID> implements BaseAuditable<U, ID> {
    private static final long serialVersionUID = 1L;
    @Id
    protected ID id;
    @CreatedBy
    protected U createdBy;
    protected String createdUserName;
    @CreatedDate
    protected LocalDateTime createdDate;
    @LastModifiedBy
    protected U lastModifiedBy;
    @LastModifiedDate
    protected LocalDateTime lastModifiedDate;

    public BaseDomain() {
    }

    public Optional<U> getCreatedBy() {
        return Optional.ofNullable(this.createdBy);
    }

    public void setCreatedBy(U createdBy) {
        this.createdBy = createdBy;
    }

    public Optional<LocalDateTime> getCreatedDate() {
        return Optional.ofNullable(this.createdDate);
    }

    public void setCreatedDate(LocalDateTime creationDate) {
        this.createdDate = creationDate;
    }

    public Optional<U> getLastModifiedBy() {
        return Optional.ofNullable(this.lastModifiedBy);
    }

    public void setLastModifiedBy(U lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Optional<LocalDateTime> getLastModifiedDate() {
        return Optional.ofNullable(this.lastModifiedDate);
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getCreatedUserName() {
        return this.createdUserName;
    }

    public void setCreatedUserName(String createdUserName) {
        this.createdUserName = createdUserName;
    }

    public void setId(ID s) {
        this.id = s;
    }

    public ID getId() {
        return this.id;
    }

    public boolean isNew() {
        return StringUtils.isEmpty(this.id);
    }
}
