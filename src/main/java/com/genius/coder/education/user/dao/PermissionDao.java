package com.genius.coder.education.user.dao;

import com.genius.coder.base.dao.BaseRepository;
import com.genius.coder.education.user.domain.Permission;
import org.springframework.stereotype.Repository;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/12
 */
@Repository
public interface PermissionDao extends BaseRepository<Permission,String> {
}
