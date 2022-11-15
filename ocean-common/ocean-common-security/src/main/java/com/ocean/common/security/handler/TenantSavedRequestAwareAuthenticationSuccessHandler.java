package com.ocean.common.security.handler;

import com.ocean.common.core.util.TenantContextHolder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 增强成功回调增加租户上下文避免极端情况下丢失问题
 *
 * @author ocean
 * @date 2022/11/15
 */
@NoArgsConstructor
@Slf4j
public class TenantSavedRequestAwareAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SavedRequest savedRequest = this.requestCache.getRequest(request, response);
        if (Objects.isNull(savedRequest)) {
            super.onAuthenticationSuccess(request, response, authentication);
        }

        if (isAlwaysUseDefaultTargetUrl()) {
            this.requestCache.removeRequest(request, response);
            super.onAuthenticationSuccess(request, response, authentication);
        } else {
            this.clearAuthenticationAttributes(request);
            if (Objects.nonNull(savedRequest)) {
                String targetUrl = savedRequest.getRedirectUrl() + "&TENANT-ID=" + TenantContextHolder.getTenantId();
                this.getRedirectStrategy().sendRedirect(request, response, targetUrl);
            }
        }
    }
}
