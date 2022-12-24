package com.ocean.common.security.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ocean.common.core.constant.Constants;
import com.ocean.common.core.dto.Result;
import com.ocean.common.core.exception.GlobalErrorCode;
import com.ocean.common.security.util.SecurityMessageSourceUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 资源服务器异常处理
 *
 * @author ocean
 * @date 2022/10/16
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding(Constants.UTF8);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        Result<String> result = new Result<>();
        result.setCode(GlobalErrorCode.UNAUTHORIZED.getCode());
        result.setMsg(authException.getMessage());
        result.setData(authException.getMessage());

        if (authException instanceof CredentialsExpiredException) {
            result.setMsg(SecurityMessageSourceUtils.getAccessor().getMessage("AbstractUserDetailsAuthenticationProvider.credentialsExpired", authException.getMessage()));
        }
        if (authException instanceof UsernameNotFoundException) {
            result.setMsg(SecurityMessageSourceUtils.getAccessor().getMessage("AbstractUserDetailsAuthenticationProvider.noopBindAccount", authException.getMessage()));
        }
        if (authException instanceof BadCredentialsException) {
            result.setMsg(SecurityMessageSourceUtils.getAccessor().getMessage("AbstractUserDetailsAuthenticationProvider.badClientCredentials", authException.getMessage()));
        }
        if (authException instanceof InsufficientAuthenticationException) {
            response.setStatus(HttpStatus.FAILED_DEPENDENCY.value());
            result.setMsg(SecurityMessageSourceUtils.getAccessor().getMessage("AbstractAccessDecisionManager.expireToken", authException.getMessage()));
        }
        if (authException instanceof UsernameNotFoundException) {
            result.setMsg(SecurityMessageSourceUtils.getAccessor().getMessage("AbstractUserDetailsAuthenticationProvider.noopBindAccount", authException.getMessage()));
        }

        PrintWriter printWriter = response.getWriter();
        printWriter.append(objectMapper.writeValueAsString(result));
    }
}
