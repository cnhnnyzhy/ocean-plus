package com.ocean.common.core.exception;

/**
 * @Description: 异常工具类
 * @Author: yang.zhang
 * @Date: 2022/10/4 22:06
 */
public final class Exceptions {
    private Exceptions() {
    }

    /**
     * 创建参数错误异常
     *
     * @param message
     * @return
     */
    public static BizException createParamsErrorException(String message) {
        return new BizException(GlobalErrorCode.PARAMS_ERROR, message);
    }

    /**
     * 创建系统异常
     *
     * @param message
     * @return
     */
    public static BizException createSystemException(String message) {
        return new BizException(GlobalErrorCode.FAILED, message);
    }
}
