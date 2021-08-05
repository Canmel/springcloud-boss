package com.camel.realname.controller;

import com.camel.realname.annotation.AuthIgnore;
import com.camel.realname.model.ZsCorp;
import com.camel.realname.service.ApplyNumberService;
import com.camel.realname.service.ZsCorpService;
import com.camel.realname.utils.ExportWordUtil;
import com.qiniu.util.Auth;
import com.qiniu.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.IIOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
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

    @Resource
    private ApplyNumberService applyNumberService;


    /**
     * 下载word
     * @param userId 用户id
     */
    @GetMapping("/exportWord/{id}")
//    @AuthIgnore
    public void exportWord(@PathVariable("id") Integer userId, HttpServletResponse response) throws FileNotFoundException {
        ExportWordUtil ewUtil = new ExportWordUtil();
        Map<String, Object> dataMap = new HashMap<>();
        //企业资质
        String businessLicenseUrl = zsCorpService.getImageAddr(userId, "businessLicenseUrl");
        System.out.println("businessLicenseUrl = " + businessLicenseUrl);
        String businessLicense = image2Byte(businessLicenseUrl);
        //法人身份证
        String corporateIdUrl = zsCorpService.getImageAddr(userId, "corporateIdUrl");
        System.out.println("corporateIdUrl = " + corporateIdUrl);
        String corporateId = image2Byte(corporateIdUrl);
        //法人手持照片

        //经办人身份证 (正
        String agentIdUrl = zsCorpService.getImageAddr(userId, "agentIdUrl");
        String agentIdZ = image2Byte(agentIdUrl);
        // （ 反
        String agentIdFurl = zsCorpService.getImageAddr(userId, "agentIdFurl");
        String agentIdF = image2Byte(agentIdFurl);
        //经办人手持照片
        String agentIdHurl = zsCorpService.getImageAddr(userId, "agentIdHurl");
        String agentIdH = image2Byte(agentIdHurl);
        //电信入网承诺书
        String acceptanceUrl = zsCorpService.getImageAddr(userId, "acceptanceUrl");
        String acceptance = image2Byte(acceptanceUrl);
        //号码申请公函
        String entrustmentLetterUrl = zsCorpService.getImageAddr(userId, "entrustmentLetterUrl");
        String entrustmentLetter = image2Byte(entrustmentLetterUrl);

        dataMap.put("businessLicense", businessLicense);
        dataMap.put("corporateId", corporateId);
        dataMap.put("agentIdZ", agentIdZ);
        dataMap.put("agentIdF", agentIdF);
        dataMap.put("agentIdH", agentIdH);
        dataMap.put("acceptance", acceptance);
        dataMap.put("entrustmentLetter", entrustmentLetter);

        ewUtil.exportWord(dataMap, "demo.ftl", response, "全国语音实名材料.doc");
    }


    public static String image2Byte(String imgUrl) {
        if (imgUrl == null && !imgUrl.startsWith("http")){
            return null;
        }
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

    /**
     * 下载客户申请表excel
     * @param id
     * @return
     * @throws FileNotFoundException
     */
    @GetMapping("/getApplySheet/{id}")
    @AuthIgnore
    public void getApplySheet(@PathVariable("id") Integer id,HttpServletResponse response) throws FileNotFoundException, IIOException {
        String excelUrl = applyNumberService.url(id);
        System.out.println("excelUrl = " + excelUrl);
        String excelName = "客户申请表";
        //  返回excel
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        HttpURLConnection httpUrl = null;
        try {
            URL url = new URL(excelUrl);
            httpUrl = (HttpURLConnection) url.openConnection();
            httpUrl.setRequestMethod("GET");
            httpUrl.setConnectTimeout(30 * 1000);
            httpUrl.connect();
            bis = new BufferedInputStream(httpUrl.getInputStream());
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
//            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-disposition", "attachment; filename="
                    + new String(excelName.getBytes("gb2312"), "ISO-8859-1") + ".xls");
            bos = new BufferedOutputStream(response.getOutputStream());
            int len = -1;
            byte[] b = new byte[1024];
            while((len = bis.read(b)) != -1){
                bos.write(b,0,b.length);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IIOException("Can't get input stream from URL!",e);
        }finally {
            if(bis != null){
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(bos != null){
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpUrl != null) {
                httpUrl.disconnect();
            }
        }
    }
}
