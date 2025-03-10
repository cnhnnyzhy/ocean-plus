package com.ocean.framework.core.exception;

/**
 * @Description: 系统异常
 * @Author: ocean
 * @Date: 2022/10/4 22:00
 */
public class SystemException extends BizException {
    private static final long serialVersionUID = -4095851957987789655L;

    public SystemException(String message) {
        super(GlobalErrorCode.FAILED.getCode(), message);
    }
}
