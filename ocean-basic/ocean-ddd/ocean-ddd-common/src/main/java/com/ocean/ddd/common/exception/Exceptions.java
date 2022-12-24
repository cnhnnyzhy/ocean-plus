package com.ocean.ddd.common.exception;

/**
 * @Description: 异常生成类
 * @Author: yang.zhang
 * @Date: 2022/7/12 16:26
 */
public final class Exceptions {
    private Exceptions() {
    }

    /**
     * 生成参数错误异常
     *
     * @param message
     * @return
     */
    public static BizException generateParamErrorException(String message) {
        return new BizException(GlobalErrorCode.PARAM_ERROR.getCode(), message);
    }

    /**
     * 生成远程调用错误异常
     *
     * @param message
     * @return
     */
    public static BizException generateRemoteCallErrorException(String message) {
        return new BizException(GlobalErrorCode.REMOTE_CALL_ERROR.getCode(), message);
    }
}
