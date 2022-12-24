package com.ocean.ddd.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description: 启动类
 * @Author: yang.zhang
 * @Date: 2022/7/11 20:04
 */
@SpringBootApplication(scanBasePackages = "com.ocean.ddd")
public class DDDApplication {
    public static void main(String[] args) {
        SpringApplication.run(DDDApplication.class, args);
    }
}
