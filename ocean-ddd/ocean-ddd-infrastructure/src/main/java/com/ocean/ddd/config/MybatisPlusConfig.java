package com.ocean.ddd.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: MyBatisPlus配置类
 * @Author: yang.zhang
 * @Date: 2022/4/18 20:21
 */
@MapperScan(basePackages = "com.ocean.ddd.repository.mapper")
@Configuration
public class MybatisPlusConfig {
}
