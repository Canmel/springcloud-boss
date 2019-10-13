package com.camel.oa.controller;


import com.baomidou.mybatisplus.service.IService;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.utils.ResultUtil;
import com.camel.oa.model.Document;
import com.camel.oa.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2019-10-12
 */
@RestController
@RequestMapping("/document")
public class DocumentController extends BaseCommonController {
    @Autowired
    private DocumentService service;


    @GetMapping
    public Result index(Document document) {
        return ResultUtil.success(service.selectPage(document));
    }

    /**
     * 上传文件
     * @param file
     * @return
     */
    @PostMapping
    public Result upload(@RequestParam("file") MultipartFile file, Principal principal) {
        OAuth2Authentication authentication = (OAuth2Authentication) principal;
        return ResultUtil.success(service.save(file, authentication));
    }

    /**
     * 获取预览地址
     * @param id
     * @return 获取下载地址
     */
    @GetMapping("/view/{id}")
    public Result view(@PathVariable Integer id) {
        try {
            return ResultUtil.success((Object) service.url(id));
        } catch (FileNotFoundException e) {
            return ResultUtil.error(ResultEnum.RESOURCESNOTFOUND);
        }
    }

    @GetMapping("/download/{id}")
    public void download(@PathVariable Integer id, HttpServletResponse response) throws Exception {
        Document document = service.selectById(id);
        if (ObjectUtils.isEmpty(document)) {
            throw new RuntimeException("找不到文件");
        }
        response.reset(); //清除buffer缓存
        response = setHeader(response, document.getDname());
        OutputStream output;
        String url = service.url(id);

        output = response.getOutputStream();
        BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);
        bufferedOutPut.flush();
        bufferedOutPut.close();
    }

    @GetMapping("/{id}")
    public Result url(@PathVariable("id") Integer id) throws FileNotFoundException {
        return ResultUtil.success((Object) service.url(id));
    }

    /**
     * 重设response
     *
     * @param response
     * @param fileName
     * @return
     */
    private HttpServletResponse setHeader(HttpServletResponse response, String fileName) {
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        response.setContentType("application;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        return response;
    }

    @Override
    public IService getiService() {
        return service;
    }

    @Override
    public String getMouduleName() {
        return "文档";
    }
}

