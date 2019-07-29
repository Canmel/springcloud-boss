package com.camel.activiti.config;

import com.camel.interceptor.MyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(new MyInterceptor());
        registration.excludePathPatterns("/error", "/lib/**", "/js/**", "/", "", "/index.html",
                "/css/**", "/img/**", "/images/**", "/editor-app/**", "/diagram-viewer/**");
    }
}
