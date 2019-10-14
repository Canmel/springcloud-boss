package com.camel.system;

import com.camel.system.config.SysUserCacheConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 *
 *                 ___====-_  _-====___
 *           _--^^^#####//      \\#####^^^--_
 *        _-^##########// (    ) \\##########^-_
 *       -############//  |\^^/|  \\############-
 *     _/############//   (@::@)   \\############\_
 *    /#############((     \\//     ))#############\
 *   -###############\\    (oo)    //###############-
 *  -#################\\  / VV \  //#################-
 * -###################\\/      \//###################-
 *_#/|##########/\######(   /\   )######/\##########|\#_
 *|/ |#/\#/\#/\/  \#/\##\  |  |  /##/\#/  \/\#/\#/\#| \|
 *`  |/  V  V  `   V  \#\| |  | |/#/  V   '  V  V  \|  '
 *   `   `  `      `   / | |  | | \   '      '  '   '
 *                    (  | |  | |  )
 *                   __\ | |  | | /__
 *                  (vvv(VVV)(VVV)vvv)
 * 系统服务
 * @author baily
 * @since 1.0
 * @date 2019/7/4
 **/
@MapperScan("com.camel.system.mapper")
@SpringBootApplication
@EnableDiscoveryClient
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringCloudSystemApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudSystemApplication.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
        LoggerFactory.getLogger(this.getClass()).info("SpringCloud 资源服务-系统服务启动完成...");
        sysUserCacheConfig.initSysUsers();
    }

    @Autowired
    private SysUserCacheConfig sysUserCacheConfig;
}
