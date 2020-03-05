package com.genius.coder.education.user.dao;

import com.genius.coder.base.dao.BaseRepository;
import com.genius.coder.education.user.domain.AdminUser;
import org.springframework.stereotype.Repository;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/5
 */
@Repository
public interface AdminUserDao extends BaseRepository<AdminUser,String> {

    AdminUser findByUnionid(String unionid);

}
