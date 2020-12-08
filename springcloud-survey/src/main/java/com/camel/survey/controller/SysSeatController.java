package com.camel.survey.controller;


import com.baomidou.mybatisplus.service.IService;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.model.Args;
import com.camel.survey.model.SysSeat;
import com.camel.survey.service.SysSeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import com.camel.core.controller.BaseCommonController;

import java.util.Date;

/**
 * <p>
 * 坐席表 前端控制器
 * </p>
 *
 * @author baily
 * @since 2020-05-30
 */
@RestController
@RequestMapping("/sysSeat")
public class SysSeatController extends BaseCommonController {
    @Autowired
    private SysSeatService service;

    /**
     * 分页查询
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','DEVOPS')")
    public Result index(SysSeat entity) {
        return ResultUtil.success(service.selectPage(entity));
    }

    /**
     * 获取详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DEVOPS')")
    public Result details(@PathVariable Integer id) {
        return super.details(id);
    }

    /**
     * 新建保存
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','DEVOPS')")
    public Result save(SysSeat entity, OAuth2Authentication oAuth2Authentication) {
        return service.save(entity, oAuth2Authentication);
    }

    /**
     * 编辑 更新
     */
    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN','DEVOPS')")
    public Result update(@RequestBody Args entity) {
        entity.setUpdateAt(new Date());
        return super.update(entity);
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DEVOPS')")
    public Result delete(@PathVariable Integer id) {
        return super.delete(id);
    }

    @Override
    public IService getiService() {
        return service;
    }

    @Override
    public String getMouduleName() {
        return "坐席";
    }
}

