package com.genius.coder.education.basicInfo.query;

import com.genius.coder.base.query.BaseQuery;
import com.genius.coder.education.basicInfo.domain.Teacher;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/9
 */
public class TeacherQuery extends BaseQuery<Teacher> {
    @Override
    public Class<Teacher> entityClassType() {
        return Teacher.class;
    }
}
