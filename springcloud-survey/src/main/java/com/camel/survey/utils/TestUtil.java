package com.camel.survey.utils;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * 有什么测试的类可以测试
 */
public class TestUtil {
    public static void main(String[] args) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("apiclient_cert.p12");
        classPathResource.getPath();
        System.out.println(classPathResource.getFile().getPath());
    }
}
