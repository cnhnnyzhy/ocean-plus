package com.ocean.common.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ocean.common.security.component.Auth2ExceptionSerializer;
import org.springframework.http.HttpStatus;

/**
 * 内部服务异常
 *
 * @author ocean
 * @date 2022/10/22
 */
@JsonSerialize(using = Auth2ExceptionSerializer.class)
public class ServerErrorException extends CustomAuth2Exception {
    public ServerErrorException(String msg, Throwable t) {
        super(msg, t);
    }

    @Override
    public String getOAuth2ErrorCode() {
        return "server_error";
    }

    @Override
    public int getHttpErrorCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
}
