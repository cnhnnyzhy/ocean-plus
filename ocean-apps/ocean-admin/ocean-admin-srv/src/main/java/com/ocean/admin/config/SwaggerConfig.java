package com.ocean.admin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * Swagger配置
 *
 * @author ocean
 * @date 2022/12/25
 */
@Configuration
@EnableSwagger2WebMvc
//@Profile({"dev", "test"})
public class SwaggerConfig {
    @Value("${spring.profiles.active:dev}")
    private String profile;

    /**
     * docket
     *
     * @return Docket
     **/
    @Bean
    public Docket docket() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                // api标题
                .title("ocean-admin")
                // api描述
                .description("系统管理")
                // 版本号
                .version("1.0.0")
                // 本API负责人的联系信息
                .contact(new Contact("Ocean", "https://www.ocean.com/", "ocean@163.com"))
                .build();
        // 文档类型（swagger2）
        return new Docket(DocumentationType.SWAGGER_2)
                .host("test".equals(profile) ? "" : "localhost:8080")
                // 设置包含在json ResourceListing响应中的api元信息
                .apiInfo(apiInfo)
                // 启动用于api选择的构建器
                .select()
                // 扫描接口的包
                .apis(RequestHandlerSelectors.basePackage("com.ocean.admin"))
                // 路径过滤器（扫描所有路径）
                .paths(PathSelectors.any())
                .build();
    }
}
