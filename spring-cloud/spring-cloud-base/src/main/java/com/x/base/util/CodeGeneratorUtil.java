package com.x.base.util;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Arrays;

/**mybatis plus 生成工具类
 * @author hp
 */
public class CodeGeneratorUtil {

    private CodeGeneratorUtil() {}

    /**自动生成类
     * @param serviceName 修改服务名以及数据表名
     * @param dbName 数据库账号
     * @param dbPassWord 数据库密码
     * @param tableArray 生成的表
     * @param isDto 默认生成entity，需要生成DTO修改此变量 一般情况下要先生成 DTO类 然后修改此参数再生成 PO 类。默认false
     */
    public static void generator(String serviceName,String dbName,String dbPassWord,String[] tableArray,Boolean isDto){
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 选择 freemarker 引擎，默认 Velocity
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setFileOverride(true);
        //生成路径
        gc.setOutputDir(System.getProperty("user.dir") + "/spring-cloud-base/src/main/java");
        gc.setAuthor("itcast");
        gc.setOpen(false);
        gc.setSwagger2(false);
        gc.setServiceName("%sService");
        gc.setMapperName("%sMapper");
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);

        if (isDto) {
            gc.setSwagger2(true);
            gc.setEntityName("%sDTO");
        }
        mpg.setGlobalConfig(gc);

        // 数据库配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setUrl("jdbc:mysql://localhost:3307/xc_" + serviceName
                + "?serverTimezone=UTC&useUnicode=true&useSSL=false&characterEncoding=utf8");
//		dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername(dbName);
        dsc.setPassword(dbPassWord);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(serviceName);
        pc.setParent("com.x");

        pc.setServiceImpl("service.impl");
        pc.setXml("mapper");
        pc.setEntity("model.po");
        mpg.setPackageInfo(pc);


        // 设置模板
        TemplateConfig tc = new TemplateConfig();
        mpg.setTemplate(tc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setInclude(tableArray);
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        // Boolean类型字段是否移除is前缀处理
        strategy.setEntityBooleanColumnRemoveIsPrefix(true);
        strategy.setRestControllerStyle(true);

        // 自动填充字段配置
        strategy.setTableFillList(Arrays.asList(
                new TableFill("create_date", FieldFill.INSERT),
                new TableFill("change_date", FieldFill.INSERT_UPDATE),
                new TableFill("modify_date", FieldFill.UPDATE)
        ));
        mpg.setStrategy(strategy);
        mpg.execute();
    }
}
