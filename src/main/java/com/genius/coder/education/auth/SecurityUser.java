package com.genius.coder.education.auth;

import com.genius.coder.education.user.domain.AdminUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2019/11/17
 */
public class SecurityUser extends AdminUser implements UserDetails {
    public SecurityUser(AdminUser user) {
        if (user != null) {
            this.setPassword(user.getPassword());
            this.setPhoneNo(user.getPhoneNo());
            this.setName(user.getName());
            this.setId(user.getId());
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        String username = this.getUsername();
        if (username != null) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(username);
            authorities.add(authority);
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.getPhoneNo();
    }

    //账户是否未过期,过期无法验证
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
