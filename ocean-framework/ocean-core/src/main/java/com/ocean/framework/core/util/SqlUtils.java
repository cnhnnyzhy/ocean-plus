package com.ocean.framework.core.util;

import com.ocean.framework.core.exception.ParameterException;

/**
 * Sql工具类
 *
 * @author ocean
 * @date 2024/5/1
 */
public class SqlUtils {
    /**
     * 仅支持字母、数字、下划线、空格、逗号、小数点（支持多个字段排序）
     */
    public static final String SQL_PATTERN = "[a-zA-Z0-9_\\ \\,\\.]+";

    /**
     * 检查字符，防止注入绕过
     */
    public static String escapeOrderBySql(String value) {
        if (StringUtils.isNotBlank(value) && !isValidOrderBySql(value)) {
            throw new ParameterException("参数不符合规范，不能进行查询");
        }
        return value;
    }

    /**
     * 验证 order by 语法是否符合规范
     */
    public static boolean isValidOrderBySql(String value) {
        return value.matches(SQL_PATTERN);
    }

}
