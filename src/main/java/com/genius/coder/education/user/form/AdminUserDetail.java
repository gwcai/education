package com.genius.coder.education.user.form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.genius.coder.education.user.enums.LoginTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/5
 */
@NoArgsConstructor
@Data
public class AdminUserDetail implements UserDetails, Serializable {
    private String username;
    private String password;
    private String name;
    private String id;
    private Collection<? extends GrantedAuthority> authorities;
    private List<String> roles;

    public AdminUserDetail(String id,String userName,String name,List<String> authorities ){
        this.id = id;
        this.username = userName;
        this.roles = authorities;
        this.name = name;
        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = new HashSet<>();
        if(CollectionUtils.isNotEmpty(authorities)){
            for(String code : authorities){
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(code);
                simpleGrantedAuthorities.add(simpleGrantedAuthority);
            }
        }
        this.authorities = simpleGrantedAuthorities;
    }

    public AdminUserDetail(AdminUserForm user) {
        Assert.notNull(user, "用户信息不能为空");
        if(user.getLoginType() == LoginTypeEnum.password){
            this.username = user.getUserName();
        }else {
            this.username = user.getPhoneNum();
        }
        if(StringUtils.isNotBlank(user.getPassword())) {
            this.password = user.getPassword();
        }
        this.id = user.getId();
        this.name = user.getName();
        this.roles = new ArrayList<>();
        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = new HashSet<>();
        for(RoleForm form : user.getRoles()) {
            if (form.getName() != null) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(form.getName());
                simpleGrantedAuthorities.add(authority);
                this.roles.add(form.getName());
            }
        }
        this.authorities = simpleGrantedAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
