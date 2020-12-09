package com.camel.survey.utils;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 　　　　　　　 ┏┓    ┏┓+ +
 * 　　　　　　　┏┛┻━━━━┛┻┓ + +
 * 　　　　　　　┃        ┃ 　  代码生成器2
 * 　　　　　　　┃     ━  ┃ ++ + + +
 * 　　　　　 　████━████ ┃+
 * 　　　　　　　┃        ┃ +
 * 　　　　　　　┃   ┻    ┃
 * 　　　　　　　┃        ┃ + +
 * 　　　　　　　┗━┓   ┏━━┛
 * 　　　　　　　  ┃   ┃
 * 　　　　　　　  ┃   ┃ + + + +
 * 　　　　　　　  ┃   ┃　　　Code is far away from bug with the animal protecting
 * 　　　　　　　  ┃   ┃+ 　　　　神兽保佑,代码无bug
 * 　　　　　　　  ┃   ┃
 * 　　　　　　　  ┃   ┃　　+
 * 　　　　　　　  ┃   ┗━━━━━━━┓ + +      @author baily
 * 　　　　　　　  ┃           ┣┓
 * 　　　　　　　  ┃           ┏┛
 * 　　　　　　　  ┗┓┓┏━━━━━┳┓┏┛ + + + +
 * 　　　　　　　   ┃┫┫     ┃┫┫
 * 　　　　　　　   ┗┻┛     ┗┻┛+ + + +
 */
public class MysqlGenerator {
    public static String CODE_FACTORY_OUT_PATH = "./springcloud-survey/src/main/java/com/camel/survey";
    public static String XML_FACTORY_OUT_PATH = "./springcloud-survey/src/main/resources/";

    public static void main(String[] args) {
        String path = new MysqlGenerator().getClass().getResource("/").getPath();
        path = path.substring(1, path.length() - 15);
        path += "src/main/java";

        com.baomidou.mybatisplus.generator.config.GlobalConfig config = new com.baomidou.mybatisplus.generator.config.GlobalConfig();
        String dbUrl = "jdbc:mysql://127.0.0.1:3306/dw_survey?useSSL=false&allowMultiQueries=true&serverTimezone=GMT%2B8";
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl(dbUrl)
                .setUsername("root")
                .setPassword("1234")
                .setDriverName("com.mysql.cj.jdbc.Driver");
        StrategyConfig strategyConfig = new StrategyConfig();
        // 指定表名
        strategyConfig.setInclude(new String[] {"customer"});
        strategyConfig
                .setCapitalMode(true)
                .setEntityLombokModel(false)
                .setDbColumnUnderline(true)
                .setNaming(NamingStrategy.underline_to_camel)
                .setSuperEntityClass("com.camel.core.entity.BasePaginationEntity")
                .setSuperControllerClass("com.camel.core.controller.BaseCommonController");
        config.setActiveRecord(false)
                .setEnableCache(false)
                // 这里就直接输出到项目里面，不用再复制进来
                .setOutputDir(path)
                .setFileOverride(true)
                .setAuthor("baily")
                .setBaseResultMap(true)
                .setBaseColumnList(true)
                .setServiceName("%sService");
        new AutoGenerator().setGlobalConfig(config)
                .setCfg(customerConfig())
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(
                        new PackageConfig()
                                .setParent("com.camel.survey")
                                .setController("controller")
                                .setEntity("model")
                ).execute();
    }

    private static InjectionConfig customerConfig() {
        InjectionConfig config = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> conf = new HashMap<>(16);
                conf.put("superControllerClassPackage", "superControllerClassPackage");
                conf.put("superControllerClass", "superControllerClass");
                this.setMap(conf);
            }
        };

        String templatePath = "/templates/template-controller.java.vm";
        List<FileOutConfig> files = new ArrayList<>();
        files.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String expand = CODE_FACTORY_OUT_PATH + "/" + "controller";
                String entityFile = String.format((expand + File.separator + "%s" + ".java"), tableInfo.getControllerName());
                return entityFile;
            }
        });

        files.add(new FileOutConfig("/templates/template-model.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String expand = CODE_FACTORY_OUT_PATH + "/" + "model";
                String entityFile = String.format((expand + File.separator + "%s" + ".java"), tableInfo.getEntityName());
                return entityFile;
            }
        });

        files.add(new FileOutConfig("/templates/template-service.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String expand = CODE_FACTORY_OUT_PATH + "/" + "service";
                String entityFile = String.format((expand + File.separator + "%s" + ".java"), tableInfo.getServiceName());
                return entityFile;
            }
        });

        files.add(new FileOutConfig("/templates/template-service-impl.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String expand = CODE_FACTORY_OUT_PATH + "/" + "service/impl";
                String entityFile = String.format((expand + File.separator + "%s" + ".java"), tableInfo.getServiceImplName());
                return entityFile;
            }
        });

        files.add(new FileOutConfig("/templates/template-mapper.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String expand = CODE_FACTORY_OUT_PATH + "/" + "mapper";
                String entityFile = String.format((expand + File.separator + "%s" + ".java"), tableInfo.getMapperName());
                return entityFile;
            }
        });

        files.add(new FileOutConfig("/templates/template-mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String expand = XML_FACTORY_OUT_PATH + "/" + "mapper";
                String entityFile = String.format((expand + File.separator + "%s" + ".xml"), tableInfo.getMapperName());
                return entityFile;
            }
        });

        config.setFileOutConfigList(files);
        return config;
    }
}
