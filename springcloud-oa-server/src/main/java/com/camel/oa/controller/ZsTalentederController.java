package com.camel.oa.controller;
import com.camel.oa.model.ZsMerchant;
import com.camel.oa.utils.ApplicationToolsUtils;
import org.springframework.web.bind.annotation.GetMapping;
import com.camel.oa.service.ZsTalentederService;
import com.camel.oa.model.ZsTalenteder;
import com.camel.core.controller.BaseCommonController;

import com.baomidou.mybatisplus.service.IService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;

import java.util.List;

@RestController
@RequestMapping("/zsTalenteder")
public class ZsTalentederController extends BaseCommonController {


    @Autowired
    private ZsTalentederService service;

    @Autowired
    private ApplicationToolsUtils applicationToolsUtils;

    /**
    * 分页查询
    */
    @GetMapping
    public Result index(ZsTalenteder entity) {
        return ResultUtil.success(service.selectPage(entity));
    }

    /**
    * 获取详情
    */
    @GetMapping("/{id}")
    public Result details(@PathVariable Integer id){
        ZsTalenteder talenteder = service.selectById(id);
        talenteder.setCreator(applicationToolsUtils.getUser(talenteder.getCreatorId()));
        return ResultUtil.success(talenteder);
    }

    /**
    * 新建保存
    */
    @PostMapping
    public Result save(@RequestBody ZsTalenteder entity) {
        return super.save(entity);
    }

    /**
     * 编辑 更新
     */
    @PutMapping
    public Result update(@RequestBody ZsTalenteder entity) {
        return super.update(entity);
    }

    /**
    * 删除
    */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return super.delete(id);
    }

    /**
    * 获取service
    */
    @Override
    public IService getiService() {
       return service;
    }

    /**
    * 获取模块名称
    */
    @Override
    public String getMouduleName() {
        return "人才";
    }

}