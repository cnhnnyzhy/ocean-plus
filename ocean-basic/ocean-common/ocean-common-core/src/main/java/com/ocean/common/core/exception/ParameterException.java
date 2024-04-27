package com.ocean.common.core.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @Description: 参数异常
 * @Author: yang.zhang
 * @Date: 2022/10/4 22:00
 */
@Getter
@EqualsAndHashCode(callSuper = true)
public class ParameterException extends BizException {
    public ParameterException(String message) {
        super(GlobalErrorCode.PARAMS_ERROR.getCode(), message);
    }
}
