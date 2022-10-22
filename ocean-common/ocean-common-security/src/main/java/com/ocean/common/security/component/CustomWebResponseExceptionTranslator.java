package com.ocean.common.security.component;

import com.ocean.common.security.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.oauth2.common.exceptions.ClientAuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import javax.security.sasl.AuthenticationException;
import java.util.Objects;

/**
 * 自定义的OAuth Server异常处理
 *
 * @author ocean
 * @date 2022/10/22
 */
@Slf4j
public class CustomWebResponseExceptionTranslator implements WebResponseExceptionTranslator {
    private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

    @Override
    public ResponseEntity translate(Exception e) {
        Throwable[] causeChain = throwableAnalyzer.determineCauseChain(e);
        Exception exception = (AuthenticationException) throwableAnalyzer.getFirstThrowableOfType(AuthenticationException.class, causeChain);
        if (Objects.nonNull(exception)) {
            return handleOAuth2Exception(new UnauthorizedException(e.getMessage(), e));
        }
        exception = (AccessDeniedException) throwableAnalyzer.getFirstThrowableOfType(AccessDeniedException.class, causeChain);
        if (Objects.nonNull(exception)) {
            return handleOAuth2Exception(new ForbiddenException(e.getMessage(), e));
        }
        exception = (InvalidGrantException) throwableAnalyzer.getFirstThrowableOfType(InvalidGrantException.class, causeChain);
        if (Objects.nonNull(exception)) {
            return handleOAuth2Exception(new InvalidException(e.getMessage(), e));
        }
        exception = (HttpRequestMethodNotSupportedException) throwableAnalyzer.getFirstThrowableOfType(HttpRequestMethodNotSupportedException.class, causeChain);
        if (Objects.nonNull(exception)) {
            return handleOAuth2Exception(new MethodNotAllowedException(e.getMessage(), e));
        }
        exception = (InvalidTokenException) throwableAnalyzer.getFirstThrowableOfType(InvalidTokenException.class, causeChain);
        if (Objects.nonNull(exception)) {
            return handleOAuth2Exception(new UnauthorizedException(e.getMessage(), e));
        }
        exception = (OAuth2Exception) throwableAnalyzer.getFirstThrowableOfType(OAuth2Exception.class, causeChain);
        if (Objects.nonNull(exception)) {
            return handleOAuth2Exception((OAuth2Exception) e);
        }
        return handleOAuth2Exception(new ServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e));
    }

    private ResponseEntity<OAuth2Exception> handleOAuth2Exception(OAuth2Exception e) {
        int httpStatus = e.getHttpErrorCode();
        if (e instanceof ClientAuthenticationException) {
            return new ResponseEntity<>(e, HttpStatus.valueOf(httpStatus));
        }
        return new ResponseEntity<>(new CustomAuth2Exception(e.getOAuth2ErrorCode(), e.getMessage()), HttpStatus.valueOf(httpStatus));
    }
}
