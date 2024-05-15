package com.ocean.framework.web.handler;

import com.ocean.framework.core.dto.Result;
import com.ocean.framework.web.properties.ReqLogProperties;
import com.ocean.framework.web.util.WebUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 全局响应结果（ResponseBody）处理器
 * <p>
 * <p>
 * 目前，GlobalResponseBodyHandler 的主要作用是，记录 Controller 的返回结果，
 * 方便 {@link com.ocean.framework.web.filter.RequestLogFilter} 记录访问日志
 *
 * @author Administrator
 */
@ControllerAdvice
public class GlobalResponseBodyHandler implements ResponseBodyAdvice {


    @Resource
    private ReqLogProperties reqLogProperties;


    @Override
    @SuppressWarnings("NullableProblems") // 避免 IDEA 警告
    public boolean supports(MethodParameter returnType, Class converterType) {
        if (returnType.getMethod() == null) {
            return false;
        }
        // 只拦截返回结果为 Result 类型
        return returnType.getMethod().getReturnType() == Result.class;
    }

    @Override
    @SuppressWarnings("NullableProblems") // 避免 IDEA 警告
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        Optional.ofNullable(reqLogProperties.getRsp()).ifPresent(rsp -> {
            if (rsp.getEnabled()) {
                // 记录 Controller 结果
                WebUtils.setResult(((ServletServerHttpRequest) request).getServletRequest(), body);
            }
        });
        return body;
    }

}
