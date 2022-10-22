package com.ocean.common.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ocean.common.security.component.Auth2ExceptionSerializer;
import lombok.Getter;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * 自定义的OAuth2Exception
 *
 * @author ocean
 * @date 2022/10/22
 */
@JsonSerialize(using = Auth2ExceptionSerializer.class)
public class CustomAuth2Exception extends OAuth2Exception {
    @Getter
    private String errorCode;

    public CustomAuth2Exception(String errorCode, String msg, Throwable t) {
        super(msg, t);
        this.errorCode = errorCode;
    }

    public CustomAuth2Exception(String errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }

    public CustomAuth2Exception(String msg, Throwable t) {
        super(msg, t);
    }
}
