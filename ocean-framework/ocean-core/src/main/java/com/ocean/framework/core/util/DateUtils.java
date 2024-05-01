package com.ocean.framework.core.util;

import cn.hutool.core.date.DateUtil;

import java.util.Date;

/**
 * 日期工具类
 *
 * @author ocean
 * @date 2024/5/1
 */
public class DateUtils {

    /**
     * 将特定格式的日期转换为Date对象
     *
     * @param dateStr 特定格式的日期
     * @param format  格式，例如yyyy-MM-dd
     * @return 日期对象
     */
    public static Date parse(CharSequence dateStr, String format) {
        return DateUtil.parse(dateStr, format);
    }

    /**
     * 解析日期时间字符串，格式支持：
     * yyyy-MM-dd HH:mm:ss
     * yyyy/MM/dd HH:mm:ss
     * yyyy.MM.dd HH:mm:ss
     * yyyy年MM月dd日 HH:mm:ss
     *
     * @param dateString – 标准形式的时间字符串
     * @return 日期对象
     */
    public static Date parseDateTime(CharSequence dateString) {
        return DateUtil.parseDateTime(dateString);
    }
}
