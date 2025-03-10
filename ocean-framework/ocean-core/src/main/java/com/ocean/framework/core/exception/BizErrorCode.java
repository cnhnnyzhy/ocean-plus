package com.ocean.framework.core.exception;

/**
 * @Description: 业务错误码
 * @Author: yang.zhang
 * @Date: 2025/02/23 14:52
 */
public interface BizErrorCode extends GlobalErrorCode {
    /**
     * 模块编码
     */
    String MODULE_CODE = "F011";

    /**
     * 错误码
     */
    ErrorCode NOT_FOUND = ErrorCode.of(MODULE_CODE, "0001", "NOT_FOUND");
}
