package com.ocean.framework.core.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Response with batch page record to return,
 * usually use in page query
 * <p/>
 * Created by ocean on 2020/06/30.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageVO<T> extends VO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "总条数")
    private Integer totalCount;

    @ApiModelProperty(value = "记录列表")
    private List<T> records;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }
}
