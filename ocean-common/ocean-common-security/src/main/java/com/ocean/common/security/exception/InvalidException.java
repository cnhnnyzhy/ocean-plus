package com.ocean.common.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ocean.common.security.component.Auth2ExceptionSerializer;

/**
 * Invalid异常
 *
 * @author ocean
 * @date 2022/10/22
 */
@JsonSerialize(using = Auth2ExceptionSerializer.class)
public class InvalidException extends CustomAuth2Exception {
    public InvalidException(String msg, Throwable t) {
        super(msg, t);
    }

    @Override
    public String getOAuth2ErrorCode() {
        return "invalid_exception";
    }

    @Override
    public int getHttpErrorCode() {
        return 426;
    }
}
