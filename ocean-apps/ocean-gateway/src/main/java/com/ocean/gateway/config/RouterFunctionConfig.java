package com.ocean.gateway.config;

import com.ocean.gateway.handler.ImageCodeCheckHandler;
import com.ocean.gateway.handler.ImageCodeCreateHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

/**
 * 路由配置
 *
 * @author ocean
 * @date 2023/1/10
 */
@Slf4j
@Configuration
@AllArgsConstructor
public class RouterFunctionConfig {

    private final ImageCodeCreateHandler imageCodeCreateHandler;

    private final ImageCodeCheckHandler imageCodeCheckHandler;

    @Bean
    public RouterFunction routerFunction() {
        return RouterFunctions
                .route(RequestPredicates.path("/code").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
                        imageCodeCreateHandler)
                .andRoute(RequestPredicates.POST("/code/check").and(RequestPredicates.accept(MediaType.ALL)),
                        imageCodeCheckHandler);
    }
}
