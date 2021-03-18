package com.camel.survey.utils;

import com.camel.survey.model.ZsReport;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 有什么测试的类可以测试
 */
public class TestUtil {
    public static void main(String[] args) throws IOException {
        ZsReport report = new ZsReport();
        report.setWorkDate(new Date());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        System.out.println(simpleDateFormat.format(report.getWorkDate()));
    }
}
