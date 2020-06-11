package com.camel.attendance.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsUtils;

import javax.servlet.http.HttpServletResponse;

/**
 *                   _ooOoo_
 *                  o8888888o   资源服务配置
 *                  88" . "88
 *                  (| -_- |)
 *                  O\  =  /O
 *               ____/`---'\____
 *             .'  \\|     |//  `.
 *            /  \\|||  :  |||//  \
 *           /  _||||| -:- |||||-  \
 *           |   | \\\  -  /// |   |
 *           | \_|  ''\---/''  |   |
 *           \  .-\__  `-`  ___/-. /
 *         ___`. .'  /--.--\  `. . __
 *      ."" '<  `.___\_<|>_/___.'  >'"".
 *     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 *     \  \ `-.   \_ __\ /__ _/   .-` /  /
 *======`-.____`-.___\_____/___.-`____.-'======
 *                   `=---='
 *^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *            佛祖保佑       永无BUG
 * @author baily
 * @since 2019-7-1
 **/
@Configuration
@EnableWebSecurity
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MyResourceServerConfigurerAdapter extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .and().authorizeRequests().mvcMatchers( "/", "/index.html", "", "/error.html").permitAll()
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .cors().disable()
                .cors()
                .and()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest)
                .permitAll()
                .and()
                .httpBasic();
    }


}
