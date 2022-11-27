package com.ocean.common.data.resolver;

import com.ocean.common.core.util.KeyStrResolver;
import com.ocean.common.core.util.TenantContextHolder;

/**
 * 租户字符串处理
 *
 * @author ocean
 * @date 2022/11/26
 */
public class TenantKeyStrResolver implements KeyStrResolver {
    /**
     * 传入字符串增加 租户编号:in
     *
     * @param in    输入字符串
     * @param split 分割符
     * @return
     */
    @Override
    public String extract(String in, String split) {
        return TenantContextHolder.getTenantId() + split + in;
    }

    /**
     * 返回当前租户ID
     *
     * @return
     */
    @Override
    public String key() {
        return String.valueOf(TenantContextHolder.getTenantId());
    }
}
