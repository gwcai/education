package com.genius.coder.education.basicInfo.query;

import com.genius.coder.base.query.BaseQuery;
import com.genius.coder.education.basicInfo.domain.Classes;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/9
 */
public class ClassesQuery extends BaseQuery<Classes> {
    @Override
    public Class<Classes> entityClassType() {
        return Classes.class;
    }
}
