package com.ocean.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.function.ConverterFileName;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

/**
 * @Description:
 * @Author: yang.zhang
 * @Date: 2022/10/5 00:15
 */
public class MyBatisPlusGenerator {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/ocean_admin?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "123456";
        String dir = "/Users/ocean/Documents/temp/CodeGen/";
        String projectName = "ocean-admin";
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("ocean") // 设置作者
//                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(dir + "/" + projectName); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.ocean.admin") // 设置父包名
                            .moduleName("system") // 设置父包模块名
                            .service("repository")
                            .serviceImpl("repository.impl")
                            .pathInfo(Collections.singletonMap(OutputFile.xml, dir + "/" + projectName + "/mappers")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder
//                            .addInclude("t_simple") // 设置需要生成的表名
                            .addTablePrefix("t_", "c_") // 设置过滤表前缀
                            .entityBuilder().enableLombok()
                            .serviceBuilder().convertServiceFileName(new ConverterFileName() {
                                @Override
                                public @NotNull
                                String convert(String entityName) {
                                    return entityName + "Repository";
                                }
                            })
                            .convertServiceImplFileName(new ConverterFileName() {
                                @Override
                                public @NotNull
                                String convert(String entityName) {
                                    return entityName + "RepositoryImpl";
                                }
                            });
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }
}
