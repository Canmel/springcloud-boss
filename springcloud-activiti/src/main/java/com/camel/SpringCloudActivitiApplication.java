package com.camel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;


// 流程
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan({"com.camel", "org.activiti"})
public class SpringCloudActivitiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudActivitiApplication.class, args);
    }
}
