package com.camel.interviewer;

import com.camel.interviewer.config.WxConstants;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@MapperScan("com.camel.interviewer.mapper")
@EnableConfigurationProperties({WxConstants.class})
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringcloudInterviewerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudInterviewerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        LoggerFactory.getLogger(this.getClass()).info("SpringCloud 资源服务-访员中心启动完成...");
    }
}
