package com.genius.coder.education.user.service;

import com.genius.coder.education.user.domain.AdminUser;
import com.genius.coder.education.user.form.AdminUserDetail;
import com.genius.coder.education.user.form.AdminUserForm;
import com.genius.coder.education.user.dao.AdminUserDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/5
 */
@Service
public class AdminUserDetailService implements UserDetailsService {

    private final AdminUserDao adminUserDao;

    public AdminUserDetailService(AdminUserDao adminUserDao) {
        this.adminUserDao = adminUserDao;
    }

    @Override
    public UserDetails loadUserByUsername(String unionid) throws UsernameNotFoundException {
        AdminUser user = adminUserDao.findByUnionid(unionid);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在！");
        }
        return new AdminUserDetail(new AdminUserForm().toForm(user));
    }

}
