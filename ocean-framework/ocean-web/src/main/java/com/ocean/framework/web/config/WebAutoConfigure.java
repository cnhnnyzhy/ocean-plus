package com.ocean.framework.web.config;

import com.ocean.framework.web.filter.FilterOrder;
import com.ocean.framework.web.filter.RequestCacheFilter;
import com.ocean.framework.web.filter.RequestLogFilter;
import com.ocean.framework.web.filter.WebTraceFilter;
import com.ocean.framework.web.handler.RequestLogPositionHandler;
import com.ocean.framework.web.properties.ReqLogProperties;
import com.ocean.framework.web.properties.TraceProperties;
import com.ocean.framework.web.properties.WebProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.PathMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import javax.servlet.Filter;


/**
 * Web 自动化配置
 *
 * @author Administrator
 */
@Configuration
@EnableConfigurationProperties({WebProperties.class, ReqLogProperties.class, TraceProperties.class})
public class WebAutoConfigure implements WebMvcConfigurer {

    @Resource
    private WebProperties webProperties;

    @Resource
    private ReqLogProperties reqLogProperties;

    @Resource
    private TraceProperties traceProperties;

//    @Override
//    public void configurePathMatch(PathMatchConfigurer configurer) {
//        // 设置 API 前缀，仅仅匹配 controller 包下的
//        configurer.addPathPrefix(webProperties.getApiPrefix(), clazz ->
//                clazz.isAnnotationPresent(RestController.class)
//                        && clazz.getPackage().getName().startsWith(webProperties.getControllerPackage()));
//    }

    /**
     * 创建 CorsFilter Bean，解决跨域问题
     */
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterBean() {
        // 创建 CorsConfiguration 对象
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(false);
        // 设置访问源地址
        config.addAllowedOrigin("*");
        // 设置访问源请求头
        config.addAllowedHeader("*");
        // 设置访问源请求方法
        config.addAllowedMethod("*");
        // 创建 UrlBasedCorsConfigurationSource 对象
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 对接口配置跨域设置
        source.registerCorsConfiguration("/**", config);
        return createFilterBean(new CorsFilter(source), Integer.MIN_VALUE);
    }

    /**
     * 默认日志辅助处理组件
     *
     * @return
     */
    @Bean
    public RequestLogPositionHandler defaultPositionHandler() {
        return new RequestLogPositionHandler() {
        };
    }

    /**
     * 创建 RequestLogFilter Bean，记录 API 请求日志
     */
    @Bean
    public FilterRegistrationBean<RequestLogFilter> reqLogFilter(RequestLogPositionHandler positionHandler, PathMatcher pathMatcher) {
        RequestLogFilter filter = new RequestLogFilter(webProperties, reqLogProperties, positionHandler, pathMatcher);
        return createFilterBean(filter, FilterOrder.REQUEST_LOG_ORDER);
    }

    @Bean
    public FilterRegistrationBean<WebTraceFilter> webTraceFilter() {
        return createFilterBean(new WebTraceFilter(traceProperties), FilterOrder.TRACE_ORDER);
    }

    @Bean
    public FilterRegistrationBean<RequestCacheFilter> requestCacheFilter() {
        return createFilterBean(new RequestCacheFilter(), FilterOrder.REQUEST_CACHE_ORDER);
    }


    private static <T extends Filter> FilterRegistrationBean<T> createFilterBean(T filter, Integer order) {
        FilterRegistrationBean<T> bean = new FilterRegistrationBean<>(filter);
        bean.setOrder(order);
        return bean;
    }

}