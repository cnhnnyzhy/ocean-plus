package com.ocean.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Ocean授权认证服务
 *
 * @author ocean
 * @date 2022-10-15
 */
@EnableFeignClients(basePackages = "com.ocean")
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.ocean")
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
