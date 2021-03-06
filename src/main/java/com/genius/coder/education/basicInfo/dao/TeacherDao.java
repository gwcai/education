package com.genius.coder.education.basicInfo.dao;

import com.genius.coder.base.dao.BaseRepository;
import com.genius.coder.education.basicInfo.domain.Teacher;
import org.springframework.stereotype.Repository;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/9
 */
@Repository
public interface TeacherDao extends BaseRepository<Teacher,String> {
}
