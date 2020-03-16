package com.genius.coder.education.user.domain;

import com.genius.coder.base.domain.BaseDomain;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/12
 */
@Entity(name="admin_role")
@Data
public class Role extends BaseDomain<AdminUser,String> {
    private String zhName;
    private String name;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,targetEntity = Permission.class)
    @JoinColumn(name = "role_id")
    private List<Permission> permissions;

    @ManyToMany(targetEntity = AdminUser.class)
    private List<AdminUser> users;
}
