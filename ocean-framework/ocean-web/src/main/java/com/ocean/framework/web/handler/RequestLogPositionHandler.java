package com.ocean.framework.web.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * RequestLogPositionHandler, 日志过滤器执行时，业务逻辑执行前进行调用。 调用方可以进行前置处理。
 *
 * @author xong.xu
 * @since 2021/6/8 15:23
 */
public interface RequestLogPositionHandler {

    /**
     * 在请求链条执行之前，进行处理。
     */
    default void preDoRequestHandle(HttpServletRequest request, HttpServletResponse response) {

    }

}