package com.ocean.framework.core.dto;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;

/**
 * OffsetPageQuery
 *
 * @author ocean
 * @date 2024/4/18
 */
public abstract class OffsetPageQuery extends BasePageQuery {
    @ApiModelProperty(name = "每次查询限制大小", notes = "默认10条")
    private int limit = DEFAULT_LIMIT;
    @ApiModelProperty(name = "开始查询位置", notes = "默认0")
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
