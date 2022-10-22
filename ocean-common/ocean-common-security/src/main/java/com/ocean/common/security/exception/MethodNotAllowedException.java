package com.ocean.common.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ocean.common.security.component.Auth2ExceptionSerializer;
import org.springframework.http.HttpStatus;

/**
 * MethodNotAllowed异常
 *
 * @author ocean
 * @date 2022/10/22
 */
@JsonSerialize(using = Auth2ExceptionSerializer.class)
public class MethodNotAllowedException extends CustomAuth2Exception {
    public MethodNotAllowedException(String msg, Throwable t) {
        super(msg, t);
    }

    @Override
    public String getOAuth2ErrorCode() {
        return "method_not_allowed";
    }

    @Override
    public int getHttpErrorCode() {
        return HttpStatus.METHOD_NOT_ALLOWED.value();
    }
}
