package com.genius.coder.base.query;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/6
 */
public abstract class BaseQueryImpl<T> implements BaseQuery<T> {
    @ApiModelProperty(
            hidden = true
    )
    protected Pageable pageable;
    protected Sort DEFAULT_SORT;
    protected static final int DEFAULT_PAGE_SIZE = 10;
    @ApiModelProperty("起始页码")
    protected int index;
    @ApiModelProperty("每页数量")
    protected int size;

    public BaseQueryImpl() {
        this(1, 10);
    }

    public BaseQueryImpl(int index, int size) {
        this.DEFAULT_SORT = Sort.by(Sort.Direction.DESC, "id");
        this.index = index;
        this.size = size;
    }

    public Pageable pageable() {
        return this.pageable != null ? this.pageable : this.getPageable(this.DEFAULT_SORT);
    }

    private Pageable getPageable(Sort sort) {
        if (this.pageable == null) {
            this.pageable = PageRequest.of(this.index < 1 ? 0 : this.index - 1, this.size, sort);
        }

        return this.pageable;
    }

    @ApiModelProperty(
            hidden = true
    )
    public void pageSort(Sort sort) {
        this.pageable = PageRequest.of(this.index < 1 ? 0 : this.index - 1, this.size, sort);
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
        this.afterSetProperties();
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
        this.afterSetProperties();
    }

    private void afterSetProperties() {
        if (this.pageable != null) {
            this.pageable = PageRequest.of(this.index < 1 ? 0 : this.index - 1, this.size, this.pageable.getSort());
        }

    }

    public abstract Class<T> entityclassType();
}
