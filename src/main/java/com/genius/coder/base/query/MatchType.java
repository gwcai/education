package com.genius.coder.base.query;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/6
 */
public enum MatchType {
    eq,
    like,
    notLike,
    in,
    greaterNum,
    greaterOrEqualNum,
    lessNum,
    lessOrEqualNum,
    notEq,
    greaterDate,
    lessDate,
    Null,
    startWith,
    endWith,
    NotNull,
    inDay,
    inDayUseOnLocalDate,
    inDayUseOnLocalDateTime,
    inDate,
    isMember,
    isTrueOrFalse;

    private MatchType() {
    }
}
