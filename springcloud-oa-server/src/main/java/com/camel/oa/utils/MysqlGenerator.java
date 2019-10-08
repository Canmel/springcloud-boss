package com.camel.oa.utils;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

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
    public static void main(String[] args) {
        String path = new MysqlGenerator().getClass().getResource("/").getPath();
        path = path.substring(1, path.length() - 15);
        path += "src/main/java";

        com.baomidou.mybatisplus.generator.config.GlobalConfig config = new com.baomidou.mybatisplus.generator.config.GlobalConfig();
        String dbUrl = "jdbc:mysql://47.106.234.117:3306/dw_service?useSSL=false&allowMultiQueries=true&serverTimezone=GMT%2B8";
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl(dbUrl)
                .setUsername("root")
                .setPassword("Ldj1654167*")
                .setDriverName("com.mysql.cj.jdbc.Driver");
        StrategyConfig strategyConfig = new StrategyConfig();
        // 指定表名
        strategyConfig.setInclude(new String[] {"traffic"});
        strategyConfig
                .setCapitalMode(true)
                .setEntityLombokModel(false)
                .setDbColumnUnderline(true)
                .setNaming(NamingStrategy.underline_to_camel);
        config.setActiveRecord(false)
                .setEnableCache(false)
                // 这里就直接输出到项目里面，不用再复制进来
                .setOutputDir(path)
                .setFileOverride(true)
                .setServiceName("%sService");
        new AutoGenerator().setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(
                        new PackageConfig()
                                .setParent("com.camel.system")
                                .setController("controller")
                                .setEntity("model")
                ).execute();
    }
}
