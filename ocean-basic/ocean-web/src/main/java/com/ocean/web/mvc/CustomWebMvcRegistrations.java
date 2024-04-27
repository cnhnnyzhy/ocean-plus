package com.ocean.web.mvc;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * 自定义的WebMvcRegistrations
 *
 * @author ocean
 * @date 2024/4/24
 */
//@SpringBootConfiguration
public class CustomWebMvcRegistrations implements WebMvcRegistrations {
    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new CustomApiRequestMappingHandlerMapping();
    }
}
