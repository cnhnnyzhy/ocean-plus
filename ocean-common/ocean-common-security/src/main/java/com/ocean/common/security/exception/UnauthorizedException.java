package com.ocean.common.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ocean.common.security.component.Auth2ExceptionSerializer;
import org.springframework.http.HttpStatus;

/**
 * 未授权异常
 *
 * @author ocean
 * @date 2022/10/22
 */
@JsonSerialize(using = Auth2ExceptionSerializer.class)
public class UnauthorizedException extends CustomAuth2Exception {
    public UnauthorizedException(String msg, Throwable t) {
        super(msg, t);
    }

    @Override
    public String getOAuth2ErrorCode() {
        return "unauthorized";
    }

    @Override
    public int getHttpErrorCode() {
        return HttpStatus.UNAUTHORIZED.value();
    }
}
