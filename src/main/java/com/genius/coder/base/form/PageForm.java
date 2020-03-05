package com.genius.coder.base.form;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/5
 */
public class PageForm<T extends Persistable<ID>, ID extends Serializable> implements Serializable {
    private long totalElements;
    private List<BaseForm> content;

    public PageForm(Class<? extends BaseForm<T, ID>> clazz, Page<T> page) {
        this.totalElements = page.getTotalElements();
        if (!page.getContent().isEmpty()) {
            this.content = (List)page.getContent().stream().map((t) -> {
                try {
                    BaseForm<T, ID> form = (BaseForm)clazz.newInstance();
                    return form.toForm(t);
                } catch (IllegalAccessException | InstantiationException var3) {
                    var3.printStackTrace();
                    return new BaseForm();
                }
            }).collect(Collectors.toList());
        }

    }

    public long getTotalElements() {
        return this.totalElements;
    }

    public List<BaseForm> getContent() {
        return this.content;
    }
}
