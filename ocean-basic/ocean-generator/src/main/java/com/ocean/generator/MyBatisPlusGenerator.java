package com.ocean.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * mybatis-plus-generator 代码自动生成
 * 参考地址：https://baomidou.com/pages/981406/
 *
 * @author ocean
 * @date 2022/4/8
 */
@AllArgsConstructor
public class MyBatisPlusGenerator {
    private String author;
    private String rootPath;
    private String url;
    private String username;
    private String password;
    private String projectName;
    private String basePackage;
    private String moduleName;
    private List<String> tableNames;

    public void generate() {
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    // 设置作者
                    builder.author(author)
                            // 开启 swagger 模式
//                            .enableSwagger()
                            // 覆盖已生成文件
                            .fileOverride()
                            // 指定输出目录
                            .outputDir(rootPath + projectName);
                })
                .packageConfig(builder -> {
                    builder
                            // 设置父包名
                            .parent(basePackage)
                            // 设置父包模块名
                            .moduleName(moduleName)
                            // 设置mapperXml生成路径
                            .pathInfo(Collections.singletonMap(OutputFile.xml, rootPath + projectName + "/mapper"));
                })
                .strategyConfig(builder -> {
                    builder
                            // 设置需要生成的表名
                            .addInclude(tableNames.toArray(new String[0]))
                            // 设置过滤表前缀
                            .addTablePrefix("t_", "c_", "marketing_")
                            .entityBuilder().enableLombok()
                    ;
                })
                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

    public static void main(String[] args) {
        MyBatisPlusGenerator daoGeneratorUtil = null;

        daoGeneratorUtil = new MyBatisPlusGenerator("ocean", "/Users/ocean/Documents/temp/CodeGen/",
                "jdbc:mysql://localhost:3306/ocean_admin?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8",
                "root", "123456",
                "ocean-admin-srv", "com.ocean.admin", "tenant",
                Lists.newArrayList("t_sys_tenant"));
        daoGeneratorUtil.generate();
    }

}
