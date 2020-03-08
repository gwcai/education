package com.genius.coder.base.service;

import com.genius.coder.base.dao.BaseRepository;
import com.genius.coder.base.query.BaseQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/5
 */
@NoRepositoryBean
public class BaseService<T extends Persistable<ID>, ID extends Serializable> {
    @Autowired
    protected BaseRepository<T, ID> dao;
    @Autowired(
            required = false
    )
    protected BaseBiz<T, ID> biz;

    public BaseService() {
    }

    public <S extends T> S save(S entity) {
        if (entity == null) {
            throw new IllegalArgumentException("实体不能为空");
        } else {
            if (this.biz != null) {
                this.biz.save(entity);
            }

            return this.dao.save(entity);
        }
    }

    public <S extends T> Iterable<S> save(Iterable<S> entities) {
        if (this.biz != null) {
            this.biz.save(entities);
        }

        return this.dao.saveAll(entities);
    }

    public <S extends T> S saveAndFlush(S entity) {
        if (entity == null) {
            throw new IllegalArgumentException("实体不能为空");
        } else {
            if (this.biz != null) {
                this.biz.save(entity);
            }

            return this.dao.save(entity);
        }
    }

    public Optional<T> findOneWithOptional(ID id) {
        return this.dao.findById(id);
    }

    public T findOneOrElseThrow(ID id) {
        return this.dao.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("实体未找到：" + id);
        });
    }

    public void delete(ID id) {
        if (this.biz != null) {
            this.biz.delete(id);
        }

        this.dao.deleteById(id);
    }

    public void delete(T entity) {
        if (this.biz != null) {
            this.biz.delete(entity);
        }

        this.dao.delete(entity);
    }

    public void delete(Iterable<T> entities) {
        if (this.biz != null) {
            this.biz.delete(entities);
        }

        this.dao.deleteAll(entities);
    }

    public List<T> findAll() {
        Iterable<T> all = this.dao.findAll();
        return this.convert(all);
    }

    public List<T> findAll(Iterable<ID> ids) {
        return this.convert(this.dao.findAllById(ids));
    }

    public Page<T> findAll(Pageable pageable) {
        return this.dao.findAll(pageable);
    }

    public Page<T> findAll(BaseQuery baseQuery) {
        return this.dao.findAll(baseQuery);
    }

    public Number count(BaseQuery baseQuery) {
        return this.dao.count(baseQuery);
    }

    public Number count() {
        return this.dao.count();
    }

    public boolean exist(ID id) {
        return this.dao.existsById(id);
    }

    private List<T> convert(Iterable<T> iterable) {
        Iterator<T> iterator = iterable.iterator();
        ArrayList list = new ArrayList();

        while(iterator.hasNext()) {
            list.add(iterator.next());
        }

        return list;
    }
}
