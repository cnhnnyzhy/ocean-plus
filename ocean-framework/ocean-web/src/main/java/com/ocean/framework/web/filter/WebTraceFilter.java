package com.ocean.framework.web.filter;

import com.ocean.framework.web.properties.TraceProperties;
import com.ocean.framework.web.util.MDCTraceUtils;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * web过滤器，生成日志链路追踪id，并赋值MDC
 *
 * @author zlt
 * @since 2020/10/14
 * <p>
 * Blog: https://zlt2000.gitee.io
 * Github: https://github.com/zlt2000
 */
@AllArgsConstructor
public class WebTraceFilter extends OncePerRequestFilter {

    private TraceProperties traceProperties;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !traceProperties.getEnabled();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        try {

            List<String> keys = new LinkedList<>();
            if (StringUtils.hasLength(traceProperties.getKeys())) {
                keys.addAll(Arrays.asList(traceProperties.getKeys().split(",")));
            }
            if (!keys.contains(MDCTraceUtils.TRACE_ID_HEADER)) {
                keys.add(0, MDCTraceUtils.TRACE_ID_HEADER);
            }

            String traceId = "";
            for (String k : keys) {
                if (StringUtils.hasLength(request.getHeader(k))) {
                    traceId = request.getHeader(k);
                    break;
                }
            }

            if (!StringUtils.hasLength(traceId)) {
                MDCTraceUtils.addTraceId();
            } else {
                MDCTraceUtils.putTraceId(traceId);
            }
            chain.doFilter(request, response);
        } finally {
            MDCTraceUtils.removeTraceId();
        }
    }
}