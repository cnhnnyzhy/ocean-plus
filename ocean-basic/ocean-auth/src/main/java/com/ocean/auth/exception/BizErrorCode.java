package com.ocean.auth.exception;

import com.ocean.common.core.exception.ErrorCode;
import com.ocean.common.core.exception.GlobalErrorCode;

/**
 * 业务异常错误码
 *
 * @author ocean
 * @date 2022/10/15
 */
public interface BizErrorCode extends GlobalErrorCode {
    /**
     * token为空
     */
    ErrorCode TOKEN_IS_NULL = new ErrorCode(700001, "Token为空");
    /**
     * AccessToken为空
     */
    ErrorCode ACCESS_TOKEN_IS_NULL = new ErrorCode(700002, "AccessToken为空");


}
