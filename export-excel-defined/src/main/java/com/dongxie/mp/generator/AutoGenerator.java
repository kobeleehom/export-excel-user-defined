package com.dongxie.mp.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/**
 * 自动构建代码入口 <br>
 *
 * @author dong.xie <br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2020-02-20 <br>
 * @see AutoGenerator <br>
 */
public class AutoGenerator {

    public static void main(String[] args) {

        ResourceBundle resource = ResourceBundle.getBundle("myBatisPlus");
        String outPutDir = resource.getString("outputDir");
        Boolean fileOverride = false;
        if ("true".equals(resource.getString("fileOverride"))) {
            fileOverride = true;
        }
        String url = resource.getString("url");
        String driverClass = resource.getString("driverClass");
        String userName = resource.getString("userName");
        String passWord = resource.getString("passWord");
        String setParent = resource.getString("setParent");
        String mapperPath = resource.getString("mapperPath");

        // 代码生成器
        com.baomidou.mybatisplus.generator.AutoGenerator mpg = new com.baomidou.mybatisplus.generator.AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        // 生成的文件输出到哪
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + outPutDir);
        // 默认主键生成策略为uuid
        gc.setIdType(IdType.AUTO);
        gc.setOpen(false);
        gc.setFileOverride(fileOverride);
        gc.setAuthor(resource.getString("author"));
        gc.setBaseColumnList(true);
        gc.setBaseResultMap(true);

        gc.setEntityName("%sEntity");
        mpg.setGlobalConfig(gc);
        // gc.setIdType(IdType.UUID);
        // 是否开启Swagger2
        // gc.setSwagger2(true);
        // gc.setXmlName("%sDaoMapper");

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setUrl(url);
        dsc.setDriverName(driverClass);
        dsc.setUsername(userName);
        dsc.setPassword(passWord);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(setParent);
        // pc.setMapper("mapper")
        // .setService("service")
        // .setController("controller")
        // .setEntity("entity");
        mpg.setPackageInfo(pc);

        // 自定义配置
        if (StringUtils.isNotEmpty(mapperPath)) {
            InjectionConfig cfg = new InjectionConfig() {
                @Override
                public void initMap() {
                    // to do nothing
                }
            };
            List<FileOutConfig> focList = new ArrayList<>();
            // mapper.xml
            focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    // 自定义输入文件名称
                    return projectPath + mapperPath + tableInfo.getMapperName() + "Mapper" + StringPool.DOT_XML;
                }
            });
            cfg.setFileOutConfigList(focList);
            mpg.setCfg(cfg);
        }

        // 模板配置
        TemplateConfig tc = new TemplateConfig();
        tc.setController("/templates/controller.java").setEntity("/templates/entity.java")
            .setMapper("/templates/mapper.java").setService("/templates/service.java")
            .setServiceImpl("/templates/serviceImpl.java").setXml(null);
        mpg.setTemplate(tc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 下划线到驼峰的命名方式
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // 使用lombok
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        if ("true".equals(resource.getString("isInputTable"))) {
            strategy.setInclude(scanner("表名"));
        }
        strategy.setControllerMappingHyphenStyle(true);
        // 继承自定义的controller，service，impl，dao
        if ("true".equals(resource.getString("needBase"))) {
            strategy.setSuperControllerClass("com.dongxie.mp.generator.base.BaseController");
            strategy.setSuperServiceClass("com.dongxie.mp.generator.base.BaseService");
            strategy.setSuperServiceImplClass("com.dongxie.mp.generator.base.BaseServiceImpl");
            strategy.setSuperMapperClass("com.dongxie.mp.generator.base.BaseDao");
        }
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.setStrategy(strategy);
        mpg.execute();
    }

    /**
     * 获取控制台上的内容
     *
     * @param tip 控制台输入的内容
     * @return
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("请输入" + tip + ":");
        System.out.println(stringBuilder.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                // 输入的不是空就返回
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "! ");
    }

}
