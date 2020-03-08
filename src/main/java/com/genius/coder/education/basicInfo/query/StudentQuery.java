package com.genius.coder.education.basicInfo.query;

import com.genius.coder.base.query.BaseQuery;
import com.genius.coder.base.query.MatchType;
import com.genius.coder.base.query.QueryWord;
import com.genius.coder.education.basicInfo.domain.Student;
import lombok.Data;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/8
 */
@Data
public class StudentQuery extends BaseQuery<Student> {
    @QueryWord(matchType = MatchType.like)
    private String name;
    @QueryWord(matchType = MatchType.like)
    private String idNo;

    @Override
    public Class<Student> entityClassType() {
        return Student.class;
    }
}
