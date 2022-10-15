package com.ocean.admin.api.enums;

import lombok.Getter;

/**
 * 租户状态
 *
 * @author ocean
 * @date 2022/10/15
 */
@Getter
public enum TenantStatus {
    /**
     * 正常的
     */
    NORMAL(1);

    private Integer value;

    TenantStatus(Integer value) {
        this.value = value;
    }
}
