package com.genius.coder.base.dao;

import com.genius.coder.base.query.BaseQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/5
 */
@NoRepositoryBean
public interface BaseRepository<T, ID> extends PagingAndSortingRepository<T, ID> {
    Page<T> findAll(BaseQuery baseQuery);

    long count(BaseQuery baseQuery);
}
