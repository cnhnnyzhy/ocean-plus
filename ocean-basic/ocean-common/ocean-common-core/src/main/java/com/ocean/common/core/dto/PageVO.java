package com.ocean.common.core.dto;

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

    private Integer totalCount;

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
