package com.ocean.framework.core.util;

import cn.hutool.core.util.StrUtil;

/**
 * 字符串工具类
 *
 * @author ocean
 * @date 2024/5/1
 */
public class StringUtils {
    /**
     * 判断字符串是否不为空
     *
     * @param str
     * @return
     */
    public static boolean isNotBlank(CharSequence str) {
        return !isBlank(str);
    }

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isBlank(CharSequence str) {
        return StrUtil.isBlank(str);
    }

    /**
     * 驼峰转下划线
     *
     * @param str
     * @return
     */
    public static String toUnderlineCase(CharSequence str) {
        return StrUtil.toUnderlineCase(str);
    }
}
