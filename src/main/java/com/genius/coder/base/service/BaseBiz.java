package com.genius.coder.base.service;

import com.genius.coder.base.dao.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Persistable;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/5
 */
@NoRepositoryBean
public class BaseBiz<T extends Persistable, ID extends Serializable> {
    @Autowired
    protected BaseRepository<T, ID> dao;

    public BaseBiz() {
    }

    public <S extends T> void save(S entity) {
    }

    public <S extends T> void save(Iterable<S> entities) {
    }

    public <S extends T> void saveAndFlush(S entites) {
    }

    public void delete(ID id) {
    }

    public void delete(T entity) {
    }

    public void delete(Iterable<? extends T> entities) {
    }
}
