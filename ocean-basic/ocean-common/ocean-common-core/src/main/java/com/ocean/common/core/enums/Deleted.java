package com.ocean.common.core.enums;

import lombok.Getter;

/**
 * 删除状态
 *
 * @author ocean
 * @date 2022/10/15
 */
@Getter
public enum Deleted {
    /**
     * 未删除
     */
    N(0),
    /**
     * 已删除
     */
    Y(1);

    private Integer value;

    Deleted(Integer value) {
        this.value = value;
    }
}
