package com.ocean.framework.web.filter;

/**
 * 过滤器顺序, 值越小越靠前
 *
 * @author ocean
 * @since 2021/6/8 22:25
 */
public interface FilterOrder {
    Integer XSS_ORDER = -100;
    Integer TENANT_ORDER = -90;
    Integer CACHE_TRACE_ORDER = -70;
    Integer REQUEST_CACHE_ORDER = -50;
    Integer REQUEST_LOG_ORDER = -1;
    Integer TRACE_ORDER = Integer.MIN_VALUE + 500;

}