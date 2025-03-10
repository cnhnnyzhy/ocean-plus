package com.ocean.framework.web.filter;

import com.ocean.framework.core.dto.Result;
import com.ocean.framework.core.util.JacksonUtils;
import com.ocean.framework.core.util.ServletUtils;
import com.ocean.framework.core.util.StringUtils;
import com.ocean.framework.web.handler.RequestLogPositionHandler;
import com.ocean.framework.web.model.AccessLogModel;
import com.ocean.framework.web.properties.ReqLogProperties;
import com.ocean.framework.web.properties.WebProperties;
import com.ocean.framework.web.util.WebUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

/**
 * API 访问日志 Filter
 *
 * @author Administrator
 */
@Slf4j
@RequiredArgsConstructor
public class RequestLogFilter extends OncePerRequestFilter {
    private final String RSP_SIMPLE = "simple";
    private final String RSP_NORMAL = "normal";
    private final WebProperties webProperties;
    private final ReqLogProperties reqProperties;
    private final RequestLogPositionHandler logPositionHandler;

    /**
     * 路径匹配器
     */
    private final PathMatcher pathMatcher;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        // 如果关闭，则不过滤
        if (!reqProperties.getEnabled()) {
            return true;
        }
        //// 如果匹配到无需过滤，则不过滤
        //String uri = request.getRequestURI();
        //boolean prefixMatch = request.getRequestURI().startsWith(webProperties.getApiPrefix());
        //boolean exclude = reqProperties.getExcludeUrls().stream().anyMatch(excludeUrl -> pathMatcher.match(excludeUrl, uri));
        //if (!prefixMatch || exclude) {
        //    return true;
        //}
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        AccessLogModel accessLog = buildAccessLog(request);
        try {
            //请求前置处理
            logPositionHandler.preDoRequestHandle(request, response);
            // 继续过滤器
            chain.doFilter(request, response);
            // 正常执行，记录日志
            log(accessLog, request);
        } catch (Exception ex) {
            // 异常执行，记录日志
            log(accessLog, request, ex);
            throw ex;
        }
    }

    private void log(AccessLogModel accessLog, HttpServletRequest request) {
        log(accessLog, request, null);
    }

    private void log(AccessLogModel accessLog, HttpServletRequest request, Throwable e) {
        if (Objects.isNull(accessLog)) {
            return;
        }
        computeDuration(accessLog);
        Optional.ofNullable(reqProperties.getRsp()).ifPresent(rsp -> {
            Optional.ofNullable(rsp.getEnabled()).ifPresent(enabled -> {
                if (!enabled) {
                    return;
                }
                // 设置访问结果
                Optional.ofNullable(WebUtils.getResult(request)).ifPresent(result -> {
                    if (RSP_NORMAL.equals(rsp.getLogType())) {
                        accessLog.setResult(result);
                    } else {
                        try {
                            Result<?> ret = JacksonUtils.toObject(JacksonUtils.toJsonStr(result), Result.class);
                            ret.setData(null);
                            accessLog.setResult(ret);
                        } catch (Exception ex) {
                            log.error("API response data is not json:[" + result + "]", ex);
                        }
                    }
                });
            });
        });
        if (e != null) {
            accessLog.setExceptionStackTrace(getExceptionStackTrace(e));
        }
        log.info("API access log:[{}]", JacksonUtils.toJsonStr(accessLog));
    }

    private String getExceptionStackTrace(Throwable ex) {
        if (ex != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            return sw.toString();
        }
        return null;
    }

    private void computeDuration(AccessLogModel accessLog) {
        accessLog.setEnd(new Date());
        long diff = accessLog.getEnd().getTime() - accessLog.getBegin().getTime();
        accessLog.setDuration((int) diff);
    }

    private AccessLogModel buildAccessLog(HttpServletRequest request) {
        if (!reqProperties.getEnabled()) {
            return null;
        }
        // 获得开始时间
        Date beginTime = new Date();
        // 提前获得参数，避免 XssFilter 过滤处理
        Map<String, String> queryParams = ServletUtils.getParamMap(request);
        String body = ServletUtils.isJsonRequest(request) ? ServletUtils.getBody(request) : null;

        AccessLogModel accessLog = new AccessLogModel();
        try {
            // 处理用户信息
            accessLog.setUid(WebUtils.getLoginUserId(request));
            accessLog.setUtype(WebUtils.getLoginUserCh(request));

            // 设置其它字段
            accessLog.setUrl(request.getRequestURI());
            String handler = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
            accessLog.setUrlMatch(handler);

            Map<String, String> headerMap = new HashMap<>(16);
            Optional.ofNullable(reqProperties.getHeader()).ifPresent(headerConfig -> {
                if (headerConfig.getEnabled()) {
                    if (StringUtils.isNotBlank(reqProperties.getHeader().getKeys())) {
                        Map<String, String> headers = ServletUtils.getHeaderMap(request);
                        if (reqProperties.getHeader().getKeys().contains("all")) {
                            headers.keySet().forEach(item -> {
                                headerMap.put(item, headers.get(item));
                            });
                        } else {
                            Arrays.stream(reqProperties.getHeader().getKeys().split(",")).forEach(item -> {
                                headerMap.put(item, headers.get(item));
                            });
                        }
                    }
                }
            });

            Map<String, Object> requestParams = new HashMap<>(3);
            requestParams.put("header", headerMap);
            requestParams.put("query", queryParams);
            requestParams.put("body", body);

            accessLog.setArgs(requestParams);
            accessLog.setMethod(request.getMethod());
            accessLog.setClientIP(ServletUtils.getClientIP(request));
            // 持续时间
            accessLog.setBegin(beginTime);
        } catch (Throwable th) {
            log.error("[RequestLogFilter][url({}) log({}) 发生异常]", request.getRequestURI(), accessLog, th);
        }
        return accessLog;
    }
}