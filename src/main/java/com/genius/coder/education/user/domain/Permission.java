package com.genius.coder.education.user.domain;

import com.genius.coder.base.domain.BaseDomain;
import lombok.Data;

import javax.persistence.Entity;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/12
 */
@Entity(name="admin_permission")
@Data
public class Permission extends BaseDomain<AdminUser,String> {
    private String zhName;
    private String name;
}
