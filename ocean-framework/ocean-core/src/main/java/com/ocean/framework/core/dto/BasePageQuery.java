package com.ocean.framework.core.dto;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ocean.framework.core.exception.ParameterException;
import com.ocean.framework.core.text.StringPool;
import com.ocean.framework.core.util.CollectionUtils;
import com.ocean.framework.core.util.SqlUtils;
import com.ocean.framework.core.util.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * BasePageQuery
 *
 * @author ocean
 * @date 2024/4/18
 */
@Data
public abstract class BasePageQuery extends Query {
    protected static final int DEFAULT_PAGE_SIZE = 10;
    protected static final int DEFAULT_PAGE_NO = 1;
    protected static final int DEFAULT_OFFSET = 0;
    protected static final int DEFAULT_LIMIT = DEFAULT_PAGE_SIZE;
    private static final String ASC = "asc";
    private static final String DESC = "desc";

    @ApiModelProperty(value = "排序字段", notes = "多个以逗号分隔")
    private String orderBy;
    @ApiModelProperty(value = "排序类型（asc：升序，desc：降序）", notes = "多个以逗号分隔，必须与排序字段个数一致")
    private String orderType = ASC;
    @ApiModelProperty(value = "是否需要查询总数", notes = "true|false")
    private boolean needTotal = true;

    protected BasePageQuery() {
    }

    public <T> Page<T> buildPage() {
        Page<T> page = generatePage();
        List<OrderItem> orderItems = buildOrderItemList();
        if (CollectionUtils.isNotEmpty(orderItems)) {
            page.addOrder(orderItems);
        }
        return page;
    }

    protected abstract <T> Page<T> generatePage();

    private List<OrderItem> buildOrderItemList() {
        if (orderBy == null || orderBy.trim().length() == 0) {
            return null;
        }
        orderBy = SqlUtils.escapeOrderBySql(orderBy);

        String[] orderByArr = orderBy.split(StringPool.COMMA);
        String[] orderTypeArr = StringUtils.isNotBlank(orderType) ? orderType.split(StringPool.COMMA) : new String[]{ASC};
        if (orderTypeArr.length > 1 && orderTypeArr.length != orderByArr.length) {
            throw new ParameterException("排序参数有误");
        }

        List<OrderItem> list = new ArrayList<>();
        // 每个字段各自排序
        for (int i = 0; i < orderByArr.length; i++) {
            String orderByStr = orderByArr[i].trim();
            if (StrUtil.isBlank(orderByStr)) {
                throw new ParameterException("排序字段有误");
            }
            String orderTypeStr = (orderTypeArr.length == 1 ? orderTypeArr[0] : orderTypeArr[i]).trim();
            if (ASC.equalsIgnoreCase(orderTypeStr)) {
                list.add(OrderItem.asc(orderByStr));
            } else if (DESC.equalsIgnoreCase(orderTypeStr)) {
                list.add(OrderItem.desc(orderByStr));
            } else {
                throw new ParameterException("排序参数无法识别");
            }
        }
        return list;
    }
}
