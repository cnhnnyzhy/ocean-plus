package com.ocean.common.core.dto;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * PageQuery
 *
 * @author ocean
 * @date 2024/4/18
 */
public abstract class PageQuery extends BasePageQuery {
    private int pageSize = DEFAULT_PAGE_SIZE;
    private int pageNo = DEFAULT_PAGE_NO;

    public PageQuery() {
    }

    @Override
    protected <T> Page<T> generatePage() {
        return new Page<>(getPageNo(), getPageSize(), isNeedTotalCount());
    }

    public int getPageNo() {
        return this.pageNo <= 0 ? DEFAULT_PAGE_NO : this.pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        if (this.pageSize <= 0) {
            this.pageSize = DEFAULT_PAGE_SIZE;
        }
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        if (pageSize <= 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        this.pageSize = pageSize;
    }
}
