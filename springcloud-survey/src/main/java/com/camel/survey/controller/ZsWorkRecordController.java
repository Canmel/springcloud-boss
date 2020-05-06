package com.camel.survey.controller;


import com.baomidou.mybatisplus.service.IService;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.model.ZsWorkRecord;
import com.camel.survey.service.ZsWorkRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import com.camel.core.controller.BaseCommonController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baily
 * @since 2020-04-27
 */
@RestController
@RequestMapping("/zsWorkRecord")
public class ZsWorkRecordController extends BaseCommonController {

    @Autowired
    private ZsWorkRecordService service;

    /**
     * 分页查询
     */
    @GetMapping
    public Result index(ZsWorkRecord entity) {
        return ResultUtil.success(service.selectPage(entity));
    }

    @PostMapping("/sign")
    public Result sign(ZsWorkRecord entity, OAuth2Authentication oAuth2Authentication){
        return service.start(entity, oAuth2Authentication);
    }
    /**
     * 编辑 更新
     */
    @PutMapping
    public Result update(@RequestBody ZsWorkRecord entity) {
        return service.updateSignW(entity);
    }

    @DeleteMapping("/signDown/{id}")
    public Result delete(@PathVariable Integer id, OAuth2Authentication oAuth2Authentication){
        return super.delete(id);
    }

    @Override
    public IService getiService() {
        return service;
    }

    @Override
    public String getMouduleName() {
        return "班次报名";
    }
}

