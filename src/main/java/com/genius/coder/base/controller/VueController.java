package com.genius.coder.base.controller;

import com.genius.coder.base.query.BaseQuery;
import com.genius.coder.base.form.BaseDataResponse;
import com.genius.coder.base.form.BaseForm;
import com.genius.coder.base.form.PageForm;
import com.genius.coder.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/5
 */
public abstract class VueController<T extends Persistable<ID>, ID extends Serializable> extends BaseController {
    @Autowired
    protected BaseService<T, ID> service;

    public VueController() {
    }

    public BaseForm<T, ID> add(BaseForm<T, ID> form) {
        T t = this.service.save(form.toEntity());
        return this.getForm(t);
    }

    public BaseForm<T, ID> detail(ID id) {
        T t = this.service.findOneOrElseThrow(id);
        return this.getForm(t);
    }

    public BaseForm<T, ID> edit(ID id) {
        T t = this.service.findOneOrElseThrow(id);
        return this.getForm(t);
    }

    public BaseForm<T, ID> edit(BaseForm<T, ID> form) {
        T t = form.toEntity();
        t = this.service.save(t);
        return this.getForm(t);
    }

    public BaseDataResponse delete(ID id) {
        this.service.delete(id);
        return BaseDataResponse.ok().msg("删除成功");
    }

    public <Q extends BaseQuery> PageForm<T, ID> list(Q query, Class<? extends BaseForm<T, ID>> formClass) {
        Page<T> page = this.service.findAll(query);
        return new PageForm(formClass, page);
    }

    protected abstract BaseForm<T, ID> getForm(T t);
}

