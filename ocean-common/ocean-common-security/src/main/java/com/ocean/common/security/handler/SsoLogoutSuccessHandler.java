package com.ocean.common.security.handler;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * sso退出成功处理类
 *
 * @author ocean
 * @date 2022/11/15
 */
public class SsoLogoutSuccessHandler implements LogoutSuccessHandler {
    private static final String REDIRECT_URL = "redirect_url";

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String redirectUrl = request.getParameter(REDIRECT_URL);
        String referer = request.getHeader(HttpHeaders.REFERER);
        // 如果请求参数中包含回调地址，则跳转至回调地址，否则跳转到referer
        if (StringUtils.isNotBlank(redirectUrl)) {
            response.sendRedirect(redirectUrl);
        } else if (StringUtils.isNotBlank(referer)) {
            response.sendRedirect(referer);
        }
    }
}
