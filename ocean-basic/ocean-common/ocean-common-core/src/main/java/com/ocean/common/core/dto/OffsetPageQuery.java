package com.ocean.common.core.dto;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * OffsetPageQuery
 *
 * @author ocean
 * @date 2024/4/18
 */
public abstract class OffsetPageQuery extends BasePageQuery {
    private int limit = DEFAULT_LIMIT;
    private int offset = DEFAULT_OFFSET;

    public OffsetPageQuery() {
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit <= 0 ? DEFAULT_LIMIT : limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset < 0 ? DEFAULT_OFFSET : offset;
    }

    @Override
    protected <T> Page<T> generatePage() {
        return new Page<>(getOffset() / getLimit() + 1, getLimit(), isNeedTotalCount());
    }
}
