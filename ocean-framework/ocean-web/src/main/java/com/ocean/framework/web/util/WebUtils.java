package com.ocean.framework.web.util;

import com.ocean.framework.core.constant.SecurityConstant;
import com.ocean.framework.web.constant.WebConstants;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * @author yongzheng.xu
 * @since 2021/6/8 14:27
 */
public class WebUtils {

    public static void setLoginUserId(ServletRequest request, Long userId) {
        request.setAttribute(SecurityConstant.USER_ID_HEADER, userId);
    }

    /**
     * 获得当前用户的编号，从请求中
     *
     * @param request 请求
     * @return 用户编号
     */
    public static Long getLoginUserId(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        return (Long) request.getAttribute(SecurityConstant.USER_ID_HEADER);
    }

    /**
     * 用户的类型
     *
     * @param request 请求
     * @return 用户类型
     */
    public static Integer getLoginUserCh(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        return (Integer) request.getAttribute(SecurityConstant.USER_CH_HEADER);
    }


    public static Long getLoginUserId() {
        HttpServletRequest request = getRequest();
        return getLoginUserId(request);
    }

    public static void setResult(ServletRequest request, Object result) {
        request.setAttribute(WebConstants.COMMON_RESULT_KEY, result);
    }

    public static Object getResult(ServletRequest request) {
        return request.getAttribute(WebConstants.COMMON_RESULT_KEY);
    }

    private static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (!(requestAttributes instanceof ServletRequestAttributes)) {
            return null;
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        return servletRequestAttributes.getRequest();
    }
}