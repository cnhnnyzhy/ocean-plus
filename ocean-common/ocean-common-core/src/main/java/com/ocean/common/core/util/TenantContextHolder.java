package com.ocean.common.core.util;

import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.experimental.UtilityClass;

/**
 * 租户工具类
 *
 * @author ocean
 * @date 2022/10/16
 */
@UtilityClass
public class TenantContextHolder {
    private final ThreadLocal<Long> THREAD_LOCAL_TENANT = new TransmittableThreadLocal<>();
    private final ThreadLocal<Boolean> THREAD_LOCAL_TENANT_SKIP_FLAG = new TransmittableThreadLocal<>();

    /**
     * 设置租户ID
     *
     * @param tenantId
     */
    public void setTenantId(Long tenantId) {
        THREAD_LOCAL_TENANT.set(tenantId);
    }

    /**
     * 设置租户跳过
     */
    public void setTenantSkip() {
        THREAD_LOCAL_TENANT_SKIP_FLAG.set(Boolean.TRUE);
    }

    /**
     * 获取租户id
     *
     * @return
     */
    public Long getTenantId() {
        return THREAD_LOCAL_TENANT.get();
    }

    /**
     * 获取是否跳过租户过滤的标识
     *
     * @return
     */
    public boolean isTenantSkip() {
        return THREAD_LOCAL_TENANT_SKIP_FLAG.get() != null && THREAD_LOCAL_TENANT_SKIP_FLAG.get();
    }

    public void clear() {
        THREAD_LOCAL_TENANT.remove();
        THREAD_LOCAL_TENANT_SKIP_FLAG.remove();
    }

}
