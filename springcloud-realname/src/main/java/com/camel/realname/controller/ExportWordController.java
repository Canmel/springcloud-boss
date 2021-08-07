package com.camel.realname.controller;

import com.camel.realname.annotation.AuthIgnore;
import com.camel.realname.enums.ApproveType;
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
     * @param id id
     * @param type ApproveType code
     */
    @GetMapping("/exportWord/{id}")
    public void exportWord(@PathVariable("id") Integer id,
                           Integer type,
                           HttpServletResponse response) throws IOException {
        ApproveType approveType = null;
        if(type == null){
            response.setContentType("text/html");
            response.getWriter().println("type 参数 为空");
        }
        approveType = ApproveType.getEnumByCode(type);
        if(approveType != null){
            if(approveType.getCode().equals(ApproveType.企业.getCode())){
                zsCorpService.exportWord(id,approveType,response);
            }else if (approveType.getCode().equals(ApproveType.外呼号码.getCode())){
                applyNumberService.exportWord(id, approveType, response);
            }else if(approveType.getCode() == 2){
                response.setContentType("text/html");
                response.getWriter().println("暂无 个人 实名认证信息");
            }
        }else{
            response.setContentType("text/html");
            response.getWriter().println("查无该 type 类型");
        }
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
        String excelUrl = applyNumberService.getFileUrl(id,ApproveType.外呼号码,"applySheet");
        System.out.println("excelUrl = " + excelUrl);
        String excelName = "客户申请表";
        //  返回excel
        InputStream is = null;
        OutputStream os = null;
        HttpURLConnection httpUrl = null;
        try {
            URL url = new URL(excelUrl);
            httpUrl = (HttpURLConnection) url.openConnection();
            httpUrl.setRequestMethod("GET");
            httpUrl.setConnectTimeout(30 * 1000);
            httpUrl.connect();
            is = httpUrl.getInputStream();
            response.reset();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename="
                    + new String(excelName.getBytes("gb2312"), "ISO-8859-1") + ".xls");
            os = response.getOutputStream();
            int len = -1;
            byte[] b = new byte[1024];
            while((len = is.read(b)) != -1){
                os.write(b,0,b.length);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IIOException("Can't get input stream from URL!",e);
        }finally {
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(os != null){
                try {
                    os.close();
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
