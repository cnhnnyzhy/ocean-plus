package com.ocean.framework.core.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @Description: 系统异常
 * @Author: ocean
 * @Date: 2022/10/4 22:00
 */
@Getter
@EqualsAndHashCode(callSuper = true)
public class SystemException extends BizException {
    public SystemException(String message) {
        super(GlobalErrorCode.FAILED.getCode(), message);
    }
}
