package com.example.springdemo.Utils;

import java.util.Collections;
import java.util.Scanner;

import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.config.builder.Controller;
import com.baomidou.mybatisplus.generator.config.builder.Entity;
import com.baomidou.mybatisplus.generator.config.builder.Mapper;
import com.baomidou.mybatisplus.generator.config.builder.Service;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;

import lombok.Builder;

/**
 * @author gq
 * @date 2020/11/6 14:24
 */
public class MysqlGenerator {
    /**
     * 运行后在控制台输入数据库中的需要生成相应代码的表的表名（多张表用“，”隔开）
     */
    public static String scanner() {
        String tip = "表名，多个英文逗号分隔";
        Scanner scanner = new Scanner(System.in);
        StringBuilder helpel = new StringBuilder();
        helpel.append("请输入" + tip + ":");
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (!StringUtils.isEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "!");
    }

    public static void main(String[] args) {

        //全局配置
        /* ***************************项目地址（绝对地址，如：D://PROJECT/Demo/）*********************************** */
        String projectPath = "D://workspaces//mybatis-Plus-Generator";
        GlobalConfig.Builder globalConfig = new GlobalConfig.Builder().outputDir(projectPath + "/src/main/java")
            .author("郭旗")
            .disableOpenDir()//禁止打开输出目录
            .fileOverride()
            .dateType(DateType.TIME_PACK)//时间类型对应策略
            .commentDate("yyyy-MM-dd");
        final String outputDir = globalConfig.build().getOutputDir();
        /* 包配置 */
        final PackageConfig.Builder packageConfig = new PackageConfig.Builder()
            .parent("com.example.mybatisG")
            .moduleName("demo")
            .entity("entity")
            .service("service")
            .serviceImpl("service.impl")
            .mapper("mapper")
            .xml("mapper.xml")
            .controller("controller")
            .pathInfo(Collections.singletonMap(OutputFile.xml, outputDir));

        final CustomFile.Builder dto = new CustomFile.Builder()
            .fileName("DTO.java")
            .templatePath("/templates/dto.java.vm")
            .packageName("dto");
        final InjectionConfig.Builder injectionConfig = new InjectionConfig.Builder()
            .beforeOutputFile((tableInfo, objectMap) -> {
                System.out.println("tableInfo: " + tableInfo.getEntityName() + " objectMap: " + objectMap.size());
            })
            .customMap(Collections.singletonMap("test", "baomidou"))
            .customFile(Collections.singletonMap("DTO.java", "/templates/dto.java.vm"))
        //            .customFile(dto.build())
        ;

        final TemplateConfig.Builder templateConfig = new TemplateConfig.Builder().disable(TemplateType.ENTITY)
            .entity(ConstVal.TEMPLATE_ENTITY_JAVA)
            .service(ConstVal.TEMPLATE_SERVICE)
            .serviceImpl(ConstVal.TEMPLATE_SERVICE_IMPL)
            .mapper(ConstVal.TEMPLATE_MAPPER)
            .xml(ConstVal.TEMPLATE_XML)
            .controller(ConstVal.TEMPLATE_CONTROLLER);

        final Entity.Builder entityConfig = new StrategyConfig.Builder().entityBuilder()
            .naming(NamingStrategy.underline_to_camel)
            .columnNaming(NamingStrategy.underline_to_camel)
            .disableSerialVersionUID()
            .enableChainModel()
            .enableLombok()
            .enableRemoveIsPrefix()//开启Boolean类型字段移除is前缀
            .enableTableFieldAnnotation()
            .enableActiveRecord()//?
            .addTableFills(new Column("create_time", FieldFill.INSERT))
            .addTableFills(new Property("updateTime", FieldFill.INSERT_UPDATE)).idType(IdType.AUTO)
            .formatFileName("%sEntity");

        final Mapper.Builder mapperConfig = new StrategyConfig.Builder()
            .mapperBuilder()
            .superClass(tk.mybatis.mapper.common.Mapper.class)
            .enableMapperAnnotation()
            .enableBaseResultMap()
            .enableBaseColumnList().formatMapperFileName("%sMapper")
            .formatXmlFileName("%sMapper");
        final StrategyConfig.Builder strategyConfig = new StrategyConfig.Builder()
//            .enableCapitalMode()
            .enableSkipView()
//          .disableSqlFilter()
//          .addInclude(scanner().split(",")) // 设置需要生成的表名
            .addInclude("tb_yx_history_record") // 设置需要生成的表名
            .addTablePrefix("tb_yx_", "TB_YX_")
            ;

        final Controller.Builder controllerConfig = new StrategyConfig.Builder()
            .controllerBuilder()
            .enableHyphenStyle()
            .enableRestStyle()
            .formatFileName("%sAction");

        final Service.Builder serviceConfig = new StrategyConfig.Builder()
            .serviceBuilder()
            .formatServiceFileName("%sService")
            .formatServiceImplFileName("%sServiceImp");

        FastAutoGenerator.create(getDataSource())
            .globalConfig(builder -> globalConfig.build())
            .packageConfig(builder -> packageConfig.build())
            .strategyConfig(builder -> strategyConfig.build())
            .strategyConfig(builder -> entityConfig.build())
            .strategyConfig(builder -> controllerConfig.build())
            .strategyConfig(builder -> serviceConfig.build())
            .strategyConfig(builder -> mapperConfig.build())
            .injectionConfig(builder -> injectionConfig.build())
            .templateConfig(builder -> templateConfig.build())
            .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板 FreemarkerTemplateEngine VelocityTemplateEngine
            .execute();
    }

    /**
     * 数据源配置
     */
    @Builder
    private static DataSourceConfig.Builder getDataSource() {
        return new DataSourceConfig.Builder(
            "jdbc:mysql://localhost:3306/testDemo?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&serverTimezone=UTC",
            "root", "123456");
    }
}
