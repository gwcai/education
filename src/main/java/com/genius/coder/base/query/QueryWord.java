package com.genius.coder.base.query;

import javax.persistence.criteria.Predicate;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/6
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryWord {
    String value() default "";

    MatchType matchType() default MatchType.eq;

    Predicate.BooleanOperator booleanOperator() default Predicate.BooleanOperator.AND;
}

