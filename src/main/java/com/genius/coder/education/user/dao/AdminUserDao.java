package com.genius.coder.education.user.dao;

import com.genius.coder.base.dao.BaseRepository;
import com.genius.coder.education.user.domain.AdminUser;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/5
 */
@Repository
public interface AdminUserDao extends BaseRepository<AdminUser,String> {

    AdminUser findByUnionid(String unionid);
    AdminUser findByUserName(String userName);

    //Set<String> findUserIdsConnectedTo(String providerId, Set<String> providerUserIds);
}
