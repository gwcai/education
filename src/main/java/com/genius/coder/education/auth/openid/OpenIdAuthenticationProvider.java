package com.genius.coder.education.auth.openid;

import com.genius.coder.education.user.dao.AdminUserDao;
import com.genius.coder.education.user.service.AdminUserDetailService;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/20
 */
@Data
public class OpenIdAuthenticationProvider implements AuthenticationProvider {

    private AdminUserDetailService userService;

    private AdminUserDao userDao;

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.security.authentication.AuthenticationProvider#
     * authenticate(org.springframework.security.core.Authentication)
     */
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        OpenIdAuthenticationToken authenticationToken = (OpenIdAuthenticationToken) authentication;

        Set<String> providerUserIds = new HashSet<String>();
        providerUserIds.add((String) authenticationToken.getPrincipal());
        //Set<String> userIds = userDao.findUserIdsConnectedTo(authenticationToken.getProviderId(), providerUserIds);

//        if(CollectionUtils.isEmpty(userIds) || userIds.size() != 1) {
//            throw new InternalAuthenticationServiceException("无法获取用户信息");
//        }
//
//        String userId = userIds.iterator().next();

//        UserDetails user = userService.loadUserByUsername(userId);
//
//        if (user == null) {
//            throw new InternalAuthenticationServiceException("无法获取用户信息");
//        }
//
//        OpenIdAuthenticationToken authenticationResult = new OpenIdAuthenticationToken(user, user.getAuthorities());
//
//        authenticationResult.setDetails(authenticationToken.getDetails());

//        return authenticationResult;
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.security.authentication.AuthenticationProvider#
     * supports(java.lang.Class)
     */
    public boolean supports(Class<?> authentication) {
        return OpenIdAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
