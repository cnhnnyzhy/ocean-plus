package com.ocean.common.core.dto;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ocean.common.core.exception.ParameterException;
import com.ocean.common.core.util.SqlUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * BasePageQuery
 *
 * @author ocean
 * @date 2024/4/18
 */
public abstract class BasePageQuery extends Query {
    protected static final int DEFAULT_PAGE_SIZE = 10;
    protected static final int DEFAULT_PAGE_NO = 1;
    protected static final int DEFAULT_OFFSET = 0;
    protected static final int DEFAULT_LIMIT = DEFAULT_PAGE_SIZE;
    private static final String ASC = "asc";
    private static final String DESC = "desc";


    private String orderBy;
    private String orderAsc = ASC;
    private boolean needTotalCount = true;

    protected BasePageQuery() {
    }

    public String getOrderBy() {
        return this.orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderAsc() {
        return orderAsc;
    }

    public void setOrderAsc(String orderAsc) {
        this.orderAsc = orderAsc;
    }

    public boolean isNeedTotalCount() {
        return this.needTotalCount;
    }

    public void setNeedTotalCount(boolean needTotalCount) {
        this.needTotalCount = needTotalCount;
    }

    public <T> Page<T> build() {
        Page<T> page = generatePage();
        List<OrderItem> orderItems = buildOrderItemList();
        if (CollUtil.isNotEmpty(orderItems)) {
            page.addOrder(orderItems);
        }
        return page;
    }

    protected abstract <T> Page<T> generatePage();

    private List<OrderItem> buildOrderItemList() {
        if (orderBy == null || orderBy.trim().length() == 0) {
            return null;
        }
        orderBy = SqlUtil.escapeOrderBySql(orderBy);

        String[] orderByArr = orderBy.split(StrUtil.COMMA);
        String[] orderAscArr = StrUtil.isNotBlank(orderAsc) ? orderAsc.split(StrUtil.COMMA) : new String[]{ASC};
        if (orderAscArr.length > 1 && orderAscArr.length != orderByArr.length) {
            throw new ParameterException("排序参数有误");
        }

        List<OrderItem> list = new ArrayList<>();
        // 每个字段各自排序
        for (int i = 0; i < orderByArr.length; i++) {
            String orderByStr = orderByArr[i].trim();
            if (StrUtil.isBlank(orderByStr)) {
                throw new ParameterException("排序字段有误");
            }
            String isAscStr = (orderAscArr.length == 1 ? orderAscArr[0] : orderAscArr[i]).trim();
            if (ASC.equalsIgnoreCase(isAscStr)) {
                list.add(OrderItem.asc(orderByStr));
            } else if (DESC.equalsIgnoreCase(isAscStr)) {
                list.add(OrderItem.desc(orderByStr));
            } else {
                throw new ParameterException("排序参数无法识别");
            }
        }
        return list;
    }
}
