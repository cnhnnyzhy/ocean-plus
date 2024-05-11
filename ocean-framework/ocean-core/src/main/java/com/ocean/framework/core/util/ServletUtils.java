package com.ocean.framework.core.util;

import cn.hutool.extra.servlet.ServletUtil;
import org.springframework.http.MediaType;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Servlet工具类
 *
 * @author ocean
 * @date 2024/5/12
 */
public class ServletUtils {
    /**
     * 获得所有请求参数
     *
     * @param request 请求对象{@link ServletRequest}
     * @return Map
     */
    public static Map<String, String> getParamMap(ServletRequest request) {
        return ServletUtil.getParamMap(request);
    }

    public static boolean isJsonRequest(ServletRequest request) {
        return StringUtils.startWithIgnoreCase(request.getContentType(), MediaType.APPLICATION_JSON_VALUE);
    }

    /**
     * 获取请求体<br>
     * 调用该方法后，getParam方法将失效
     *
     * @param request {@link ServletRequest}
     * @return 获得请求体
     * @since 4.0.2
     */
    public static String getBody(ServletRequest request) {
        return ServletUtil.getBody(request);
    }

    /**
     * 获取请求所有的头（header）信息
     *
     * @param request 请求对象{@link HttpServletRequest}
     * @return header值
     * @since 4.6.2
     */
    public static Map<String, String> getHeaderMap(HttpServletRequest request) {
        return ServletUtil.getHeaderMap(request);
    }

    /**
     * 获取客户端IP
     *
     * <p>
     * 默认检测的Header:
     *
     * <pre>
     * 1、X-Forwarded-For
     * 2、X-Real-IP
     * 3、Proxy-Client-IP
     * 4、WL-Proxy-Client-IP
     * </pre>
     *
     * <p>
     * otherHeaderNames参数用于自定义检测的Header<br>
     * 需要注意的是，使用此方法获取的客户IP地址必须在Http服务器（例如Nginx）中配置头信息，否则容易造成IP伪造。
     * </p>
     *
     * @param request          请求对象{@link HttpServletRequest}
     * @param otherHeaderNames 其他自定义头文件，通常在Http服务器（例如Nginx）中配置
     * @return IP地址
     */
    public static String getClientIP(HttpServletRequest request, String... otherHeaderNames) {
        return ServletUtil.getClientIP(request, otherHeaderNames);
    }
}
