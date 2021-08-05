package com.camel.realname.controller;

import com.camel.realname.annotation.AuthIgnore;
import com.camel.realname.model.ZsCorp;
import com.camel.realname.service.ZsCorpService;
import com.camel.realname.utils.ExportWordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
public class ExportWordController {

    @Autowired
    private ZsCorpService zsCorpService;


    public void exportWord1(HttpServletResponse response) {
        ExportWordUtil ewUtil = new ExportWordUtil();
        Map<String, Object> dataMap = new HashMap<>();
        String url = image2Byte("https://diaocha.svdata.cn/realname/images/bg.png");
        dataMap.put("photo", url);
        ewUtil.exportWord(dataMap, "demo.ftl", response, "全国语音实名材料.doc");
    }

    /**
     * 下载word
     * @param userId 用户id
     */
    @GetMapping("/exportWord/{id}")
    @AuthIgnore
    public void exportWord(@PathVariable("id") Integer userId, HttpServletResponse response) throws FileNotFoundException {
        ExportWordUtil ewUtil = new ExportWordUtil();
        Map<String, Object> dataMap = new HashMap<>();
        //企业资质
        String businessLicenseUrl = zsCorpService.getImageAddr(userId, "businessLicenseUrl");
        System.out.println("businessLicenseUrl = " + businessLicenseUrl);
        String businessLicense = image2Byte(businessLicenseUrl);
        //法人身份证
        String corporateIdUrl = zsCorpService.getImageAddr(userId, "corporateIdUrl");
        String corporateId = image2Byte(corporateIdUrl);
        //法人手持照片

        //经办人身份证

        //经办人手持照片

        //电信入网承诺书

        //号码申请公函
        dataMap.put("businessLicense", businessLicense);
        dataMap.put("corporateId", corporateId);
        ewUtil.exportWord(dataMap, "demo.ftl", response, "全国语音实名材料.doc");
    }


    public static String image2Byte(String imgUrl) {
        URL url = null;
        InputStream is = null;
        ByteArrayOutputStream outStream = null;
        HttpURLConnection httpUrl = null;
        try {
            url = new URL(imgUrl);
            httpUrl = (HttpURLConnection) url.openConnection();
            httpUrl.setRequestMethod("GET");
            httpUrl.setConnectTimeout(30 * 1000);
            httpUrl.connect();
            httpUrl.getInputStream();
            is = httpUrl.getInputStream();
            outStream = new ByteArrayOutputStream();
            //创建一个Buffer字符串
            byte[] buffer = new byte[2048];
            //每次读取的字符串长度，如果为-1，代表全部读取完毕
            int len = 0;
            //使用一个输入流从buffer里把数据读取出来
            while ((len = is.read(buffer)) != -1) {
                //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                outStream.write(buffer, 0, len);
            }
            // 对字节数组Base64编码
            return java.util.Base64.getEncoder().encodeToString(outStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpUrl != null) {
                httpUrl.disconnect();
            }
        }
        return null;
    }
}
