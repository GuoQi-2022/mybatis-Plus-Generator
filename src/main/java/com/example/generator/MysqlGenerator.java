package com.example.generator;

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
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;
import com.example.generator.Utils.ConstVal;

/**
 * @author gq
 * @date 2020/11/6 14:24
 */
public class MysqlGenerator {
    //全局配置
    /* ***************************项目地址（绝对地址，如：D:\\PROJECT\\Demo）*********************************** */
    static String projectPath = "D:\\StudayWorkspaces\\mybatis-Plus-Generator";
    final static String outputDir = projectPath + "\\src\\main";

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
        /**
         * 使用Freemarker引擎模板，默认的是Velocity引擎模板 FreemarkerTemplateEngine VelocityTemplateEngine
         */
        FastAutoGenerator
                .create(getDataSource())
                .globalConfig(MysqlGenerator::setGlobalConfig)
                .packageConfig(MysqlGenerator::setPackageConfig)
                .strategyConfig(MysqlGenerator::setStrategyConfig)
                .strategyConfig(MysqlGenerator::setEntityConfig)
                .strategyConfig(MysqlGenerator::setControllerConfig)
                .strategyConfig(MysqlGenerator::setServiceConfig)
                .strategyConfig(MysqlGenerator::setMapperConfig)
                .injectionConfig(MysqlGenerator::setInjectionConfig)
                .templateConfig(MysqlGenerator::setTemplateConfig)
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

    /**
     * 注入配置设置
     */
    private static void setInjectionConfig(InjectionConfig.Builder builder) {
        builder
                .beforeOutputFile((tableInfo, objectMap) ->
                        System.out.println("tableInfo: " + tableInfo.getEntityName() + " objectMap: " + objectMap.size()))
                .fileOverride();
    }

    /**
     * Mapper设置
     */
    private static void setMapperConfig(StrategyConfig.Builder builder) {
        builder
                .mapperBuilder()
                .fileOverride()
                .superClass(ConstVal.SUPER_MAPPER_CLASS)
                .enableMapperAnnotation()
                .formatMapperFileName("%sMapper")
                .formatXmlFileName("%sMapper");
    }

    /**
     * Service设置
     */
    private static void setServiceConfig(StrategyConfig.Builder builder) {
        builder
                .serviceBuilder()
                .fileOverride()
                .formatServiceFileName("%sService")
                .superServiceClass(ConstVal.SUPER_SERVICE_CLASS)
                .superServiceImplClass(ConstVal.SUPER_SERVICE_IMPL_CLASS)
                .formatServiceImplFileName("%sServiceImpl");
    }

    /**
     * Controller设置
     */
    private static void setControllerConfig(StrategyConfig.Builder builder) {
        builder
                .controllerBuilder()
                .fileOverride()
                .enableRestStyle();
    }

    /**
     * Entity设置
     */
    private static void setEntityConfig(StrategyConfig.Builder builder) {
        builder
                .entityBuilder()
                .fileOverride()
                .naming(NamingStrategy.underline_to_camel)
                .columnNaming(NamingStrategy.underline_to_camel)
                .disableSerialVersionUID()
                .enableLombok()
                .addTableFills(new Column("create_time", FieldFill.INSERT))
                .addTableFills(new Property("updateTime", FieldFill.INSERT_UPDATE))
                .idType(IdType.AUTO)
                .formatFileName("%sEntity");
    }

    /**
     * 路径配置
     */
    private static void setPackageConfig(PackageConfig.Builder builder) {
        builder
                .parent("com.example.mybatisG")
                .entity("entity")
                .service("service")
                .serviceImpl("service.impl")
                .mapper("mapper")
                .xml("mapper.xml")
                .controller("controller")
                .pathInfo(Collections.singletonMap(OutputFile.xml, outputDir + "\\resources\\mapper"));
    }

    /**
     * 结果文件路径设置
     */
    private static void setGlobalConfig(GlobalConfig.Builder builder) {
        builder
                .outputDir(outputDir + "\\java")
                .author("Gq")
                .disableOpenDir()//禁止打开输出目录（禁止生成后打开文件夹）
                .dateType(DateType.TIME_PACK)
                .commentDate("yyyy-MM-dd");
    }

    /**
     * 模板类型设置
     */
    private static void setTemplateConfig(TemplateConfig.Builder builder) {
        builder
                .disable(TemplateType.ENTITY)
                .entity(ConstVal.TEMPLATE_ENTITY_JAVA)
                .service(ConstVal.TEMPLATE_SERVICE)
                .serviceImpl(ConstVal.TEMPLATE_SERVICE_IMPL)
                .mapper(ConstVal.TEMPLATE_MAPPER)
                .xml(ConstVal.TEMPLATE_XML)
                .controller(ConstVal.TEMPLATE_CONTROLLER);
    }

    /**
     * 表设置
     */
    private static void setStrategyConfig(StrategyConfig.Builder builder) {
        builder
                .addInclude(scanner()
                .split(",")) // 设置需要生成的表名
            //  .addInclude("tb_yx_history_record") // 设置需要生成的表名
                .addTablePrefix("tb_yx_", "TB_YX_");
    }

    /**
     * 数据源配置
     */
    private static DataSourceConfig.Builder getDataSource() {
        return new DataSourceConfig.Builder(
            "jdbc:mysql://localhost:3306/testDemo?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&serverTimezone=UTC",
            "root", "123456");
    }
}
