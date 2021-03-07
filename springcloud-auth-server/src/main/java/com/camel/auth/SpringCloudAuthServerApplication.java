package com.camel.auth;

import com.camel.common.entity.Result;
import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

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
 * <授权服务>
 * @author baily
 * @since 1.0
 * @date 2019/7/4
 **/
@ControllerAdvice
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.camel.auth.dao")
public class SpringCloudAuthServerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudAuthServerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        LoggerFactory.getLogger(this.getClass()).info("SpringCloud OAuth2.0 授权服务启动成功...");
    }

    @ExceptionHandler(value = Exception.class)
    public Result notSignOutHandler(Exception e, HttpServletResponse response){
        response.setStatus(HttpStatus.OK.value());
        Result result = new Result();
        result.setCode(400);
        result.setMessage("无效的用户名和密码");
        return result;
    }
}
