package com.ocean.common.core.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Web工具类
 *
 * @author ocean
 * @date 2022/11/15
 */
@Slf4j
@UtilityClass
public class WebUtils extends org.springframework.web.util.WebUtils {

    /**
     * 获取 HttpServletRequest
     *
     * @return {HttpServletRequest}
     */
    public HttpServletRequest getRequest() {
        try {
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取 HttpServletResponse
     *
     * @return
     */
    public HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }
}
