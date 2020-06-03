package com.camel.survey.controller;


import com.baomidou.mybatisplus.service.IService;
import com.camel.common.entity.Member;
import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
import com.camel.core.utils.ApplicationUtils;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.model.Args;
import com.camel.survey.model.ZsSeat;
import com.camel.survey.service.ZsSeatService;
import com.camel.survey.utils.ApplicationToolsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import com.camel.core.controller.BaseCommonController;

import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baily
 * @since 2020-02-19
 */
@RestController
@RequestMapping("/zsSeat")
public class ZsSeatController extends BaseCommonController {
    @Autowired
    private ZsSeatService service;

    @Autowired
    private ApplicationToolsUtils applicationUtils;

    @GetMapping
    public Result index(ZsSeat zsSeat) {
        return ResultUtil.success(service.pageQuery(zsSeat));
    }

    /**
     * 新建保存
     */
    @PostMapping
    public Result save(ZsSeat entity, OAuth2Authentication oAuth2Authentication) {
        return service.save(entity, oAuth2Authentication);
    }

    /**
     * 获取详情
     */
    @GetMapping("/{id}")
    public Result details(@PathVariable Integer id) {
        return super.details(id);
    }

    /**
     * 编辑 更新
     */
    @PutMapping
    public Result update(@RequestBody ZsSeat entity) {
        return super.update(entity);
    }

    @GetMapping("/current")
    public Result current() {
        SysUser member = applicationUtils.currentUser();
        return ResultUtil.success(service.selectByUid(member.getUid()));
    }

    @GetMapping("/{id}/selectByUid")
    public Result selectByUid(@PathVariable Integer id) {
        return ResultUtil.success(service.selectByUid(id));
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
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

