package com.ocean.common.core.util;

/**
 * 字符串处理
 *
 * @author ocean
 * @date 2022/11/26
 */
public interface KeyStrResolver {
    /**
     * 字符串加工
     *
     * @param in    输入字符串
     * @param split 分割符
     * @return 输出字符串
     */
    String extract(String in, String split);

    /**
     * 字符串获取
     *
     * @return 模块返回字符串
     */
    String key();
}
