package com.genius.coder.education.basicInfo.query;

import com.genius.coder.base.query.BaseQuery;
import com.genius.coder.education.basicInfo.domain.CardInfo;
import lombok.Data;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/9
 */
@Data
public class CardInfoQuery extends BaseQuery<CardInfo> {
    @Override
    public Class<CardInfo> entityClassType() {
        return CardInfo.class;
    }
}
