package com.camel.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsUtils;

/**
 *                   _ooOoo_
 *                  o8888888o   Security 配置
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
 *
 * @author baily
 **/
@Configuration
@EnableWebSecurity
@Order(99)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "/webjars/**", "/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and().headers().frameOptions().disable()
                .and()
                .cors().disable()
                .cors()
                .and()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest)
                .permitAll()
                .and()
                .csrf().disable();
    }
}
