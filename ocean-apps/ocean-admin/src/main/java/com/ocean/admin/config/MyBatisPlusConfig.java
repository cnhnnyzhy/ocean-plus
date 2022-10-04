package com.ocean.admin.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: MyBatisPlus配置
 * @Author: yang.zhang
 * @Date: 2022/10/4 22:56
 */
@MapperScan(basePackages = {"com.ocean.admin.system.mapper"})
@Configuration
public class MyBatisPlusConfig {
}
