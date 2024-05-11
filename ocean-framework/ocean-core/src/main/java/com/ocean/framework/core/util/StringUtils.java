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

    /**
     * 是否以指定字符串开头，忽略大小写
     *
     * @param str    被监测字符串
     * @param prefix 开头字符串
     * @return 是否以指定字符串开头
     */
    public static boolean startWithIgnoreCase(CharSequence str, CharSequence prefix) {
        return StrUtil.startWithIgnoreCase(str, prefix);
    }
}
