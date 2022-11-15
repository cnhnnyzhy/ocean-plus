package com.ocean.common.security.handler;

import cn.hutool.http.HttpUtil;
import com.ocean.common.core.util.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 表单登录失败处理逻辑
 *
 * @author ocean
 * @date 2022/11/15
 */
@Slf4j
public class FormAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.debug("表单登录失败：{}", exception.getLocalizedMessage());
        String url = HttpUtil.encodeParams(String.format("/token/login?error=%s", exception.getMessage()), StandardCharsets.UTF_8);
        WebUtils.getResponse().sendRedirect(url);
    }
}
