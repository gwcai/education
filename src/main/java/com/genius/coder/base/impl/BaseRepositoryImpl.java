package com.genius.coder.base.impl;

import com.genius.coder.base.dao.BaseRepository;
import com.genius.coder.base.query.BaseQuery;
import com.genius.coder.base.query.MatchType;
import com.genius.coder.base.query.QueryWord;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/8
 */
public class BaseRepositoryImpl<T,ID> extends SimpleJpaRepository<T, ID> implements BaseRepository<T,ID> {
    private final EntityManager entityManager;
    public BaseRepositoryImpl(JpaEntityInformation entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    public BaseRepositoryImpl(Class domainClass, EntityManager em) {
        super(domainClass, em);
        this.entityManager = em;
    }

    public Page<T> findAll(BaseQuery baseQuery) {
        Class<T> classType = baseQuery.entityClassType();
        Specification spec = parseSpecification(classType);
        return this.findAll(spec, baseQuery.pageable());
    }

    public long count(BaseQuery baseQuery) {
        Class<T> classType = baseQuery.entityClassType();
        Specification spec = parseSpecification(classType);
        return this.count(spec);
    }

    public Specification<T> parseSpecification(Class<T> classType) {
        Field[] fields = classType.getDeclaredFields();
        return (Specification<T>) (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (Field field : fields) {
                field.setAccessible(true);
                if (null != field.getAnnotation(QueryWord.class)) {
                    Predicate predicate = parseCriteria(root, cb, field);
                    if (null != predicate && !predicates.contains(predicate)) {
                        predicates.add(predicate);
                    }
                }
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    private Predicate parseCriteria(Root<T> root, CriteriaBuilder cb, Field field) {
        field.setAccessible(true);
        QueryWord queryWord = field.getAnnotation(QueryWord.class);
        Predicate.BooleanOperator operator = queryWord.booleanOperator();
        Predicate predicate = parseExpression(root, cb, field);
        if (null == predicate)
            return null;

        if (Predicate.BooleanOperator.AND.equals(operator)) {
            return cb.and(predicate);
        } else {
            return cb.or(predicate);
        }
    }

    private Predicate parseExpression(Root<T> root, CriteriaBuilder cb, Field field) {
        QueryWord queryWord = field.getAnnotation(QueryWord.class);
        String key = StringUtils.isEmpty(queryWord.value()) ? field.getName() : queryWord.value();
        MatchType matchType = queryWord.matchType();
        try {
            Object fieldValue = field.get(this);
            if (fieldValue == null) {
                return null;
            }
            switch (matchType) {
                case eq: return cb.equal(root.get(key), fieldValue);
                case like:
                    return cb.like(root.get(key),"%" + fieldValue + "%");
                case notLike:
                    return cb.notLike(root.get(key),"%" + fieldValue + "%");
                case in:
                    return cb.in(root.get(key));
                case greaterNum: {
                    ParameterExpression<Number> param = cb.parameter(Number.class, key);
                    return cb.gt(root.get(key),param);
                }
                case greaterOrEqualNum:{
                    ParameterExpression<Number> param = cb.parameter(Number.class, key);
                    return cb.ge(root.get(key),param);
                }
                case lessNum: {
                    ParameterExpression<Number> param = cb.parameter(Number.class, key);
                    return cb.lt(root.get(key),param);
                }
                case lessOrEqualNum:{
                    ParameterExpression<Number> param = cb.parameter(Number.class, key);
                    return cb.le(root.get(key),param);
                }
                case notEq: return cb.notEqual(root.get(key),fieldValue);
                case greaterDate:{
                    ParameterExpression<Date> param = cb.parameter(Date.class, key);
                    return cb.greaterThan(root.<Date>get(key),param);
                }
                case lessDate:{
                    ParameterExpression<Date> param = cb.parameter(Date.class, key);
                    return cb.lessThan(root.<Date>get(key),param);
                }
                case greaterOrEqualDate:{
                    ParameterExpression<Date> param = cb.parameter(Date.class, key);
                    return cb.greaterThanOrEqualTo(root.<Date>get(key),param);
                }
                case lessOrEqualDate:{
                    ParameterExpression<Date> param = cb.parameter(Date.class, key);
                    return cb.lessThanOrEqualTo(root.<Date>get(key),param);
                }
                case  Null: return cb.isNull(root.get(key));
                case  startWith: return cb.like(root.get(key),fieldValue + "%");
                case  endWith: return cb.like(root.get(key),"%" + fieldValue);
                case  NotNull: return cb.isNotNull(root.get(key));
                case  isMember: return cb.isMember(fieldValue,root.get(key));
                case  isTrueOrFalse: return cb.isTrue(root.get(key));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
