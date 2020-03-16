package com.genius.coder.education.util;

import com.genius.coder.education.user.form.AdminUserForm;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/12
 */
public class LoginUtil {
    public static AdminUserForm getPassword(AdminUserForm userForm){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pwd = encoder.encode(userForm.getPassword());
        //String salt = BCrypt.gensalt();
        //String pwd = BCrypt.hashpw(userForm.getPassword(),salt);
       // userForm.setSalt(salt);
        userForm.setPassword(pwd);
        return userForm;
    }
}
