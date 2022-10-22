package com.ocean.common.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ocean.common.security.component.Auth2ExceptionSerializer;
import org.springframework.http.HttpStatus;

/**
 * Token无效异常
 *
 * @author ocean
 * @date 2022/10/22
 */
@JsonSerialize(using = Auth2ExceptionSerializer.class)
public class TokenInvalidException extends CustomAuth2Exception {
    public TokenInvalidException(String msg, Throwable t) {
        super(msg, t);
    }

    @Override
    public String getOAuth2ErrorCode() {
        return "invalid_token";
    }

    @Override
    public int getHttpErrorCode() {
        return HttpStatus.FAILED_DEPENDENCY.value();
    }
}
