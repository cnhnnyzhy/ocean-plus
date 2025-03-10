package com.ocean.framework.core.exception;

/**
 * @Description: 参数异常
 * @Author: yang.zhang
 * @Date: 2022/10/4 22:00
 */
public class ParameterException extends BizException {
    private static final long serialVersionUID = 1672712023810654841L;

    public ParameterException(String message) {
        super(GlobalErrorCode.PARAM_ERROR.getCode(), message);
    }
}
