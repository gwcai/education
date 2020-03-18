package com.genius.coder.education.common;

import com.genius.coder.education.user.form.AdminUserDetail;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/5
 */
public class AdminUserAware implements AuditorAware<String> {
    @Override
    @Nonnull
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2Authentication) {
            return Optional.of(((AdminUserDetail) ((OAuth2Authentication) authentication).getUserAuthentication().getPrincipal()).getId());
        }
        return Optional.empty();
    }
}