import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * @Description:
 * @Author: yang.zhang
 * @Date: 2022/4/18 14:45
 */
public class CodeGenerator {

    public static void main(String[] args) {
        String url = "jdbc:mysql://rm-2zemd90f78ycck9sl.mysql.rds.aliyuncs.com:3306/dev_csd_tags?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
        String username = "dev_csd_tags";
        String password = "dev_csd_tags@dsmfkemreg#Dsf1";
        String rootPath = "/Users/ocean/Documents/temp/CodeGen/";
        String projectName = "ocean-ddd";
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("yang.zhang") // 设置作者
//                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(rootPath + projectName); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.ocean.ddd") // 设置父包名
//                            .moduleName("tag") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, rootPath + projectName + "/mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("tag", "tag_category", "tag_used_record") // 设置需要生成的表名
                            .addTablePrefix("t_", "c_", "marketing_")
                            .entityBuilder().enableLombok()
                            .formatFileName("%sDO")
//                            .idType(IdType.AUTO)
                    //.logicDeleteColumnName("is_deleted")
                    ; // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}