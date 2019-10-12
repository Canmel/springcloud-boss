package com.camel.oa.controller;


import com.baomidou.mybatisplus.service.IService;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.oa.model.Document;
import com.camel.oa.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
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

    @GetMapping("/{id}")
    public Result url(@PathVariable("id") Integer id) throws FileNotFoundException {
        return ResultUtil.success((Object) service.url(id));
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

