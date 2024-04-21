package com.ocean.common.dto;

import java.util.Collection;

/**
 * Response with batch page record to return,
 * usually use in page query
 * <p/>
 * Created by ocean on 2020/06/30.
 */
public class PageResponse<T> extends Response {

    private static final long serialVersionUID = 1L;

    private Integer totalCount;

    private Integer pageSize;

    private Integer pageIndex;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getTotalPages() {
        if (this.totalCount == null || this.pageSize == null) {
            return null;
        }
        return this.totalCount % this.pageSize == 0 ? this.totalCount
                / this.pageSize : (this.totalCount / this.pageSize) + 1;
    }

    public boolean isEmpty() {
        Object data = getData();
        if (data == null) {
            return true;
        }
        if (data instanceof Collection) {
            return ((Collection<?>) data).isEmpty();
        }
        return false;
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    @Override
    public String toString() {
        return "PageResponse[" +
                "success=" + isSuccess() +
                ", code=" + getCode() +
                ", message=" + getMessage() +
                ", totalCount=" + totalCount +
                ", pageSize=" + pageSize +
                ", pageIndex=" + pageIndex +
                ']';
    }

    public static <T> PageResponse<T> buildSuccess(T data, int totalCount, int pageSize, int pageIndex) {
        PageResponse response = (PageResponse) Response.buildSuccess(data);
        response.setTotalCount(totalCount);
        response.setPageIndex(pageIndex);
        response.setPageSize(pageSize);
        return response;
    }

    public static <T> PageResponse<T> buildSuccess(T data, int pageSize, int pageIndex) {
        PageResponse response = (PageResponse) Response.buildSuccess(data);
        response.setPageIndex(pageIndex);
        response.setPageSize(pageSize);
        return response;
    }

    public static PageResponse buildFailure(String code, String message) {
        return (PageResponse) Response.buildFailure(code, message);
    }
}
