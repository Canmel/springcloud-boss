package com.camel.survey.controller;

import com.baomidou.mybatisplus.service.IService;
import com.camel.common.entity.Member;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.model.ZsSign;
import com.camel.survey.service.ZsSignService;
import com.camel.survey.utils.ApplicationToolsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * 　　　　　　　 ┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━━━┛┻┓
 * 　　　　　　　┃         ┃ 　
 * 　　　　　　　┃    ━    ┃
 * 　　　　　　　┃  >   <  ┃
 * 　　　　　　　┃         ┃
 * 　　　　　　　┃... ⌒ ...┃
 * 　　　　　　　┃         ┃
 *             ┗━┓     ┏━┛
 *               ┃     ┃　Code is far away from bug with the animal protecting　　　　　　　　　　
 *               ┃     ┃   神兽保佑,代码无bug
 *               ┃     ┃　　　　　　　　　　　
 *               ┃     ┃  　　　　　　
 *               ┃     ┃        <问卷调查报名记录 前端控制器>
 *               ┃     ┃　　　　　　　　　　　
 *               ┃     ┗━━━━┓   @author baily
 *               ┃          ┣┓
 *               ┃          ┏┛  @since 1.0
 *               ┗┓┓┏━━━━┳┓┏┛
 *                ┃┫┫    ┃┫┫    @date 2019-12-13
 *                ┗┻┛    ┗┻┛
 */
@RestController
@RequestMapping("/zsSign")
public class ZsSignController extends BaseCommonController {


    @Autowired
    private ZsSignService service;

    @Autowired
    private ApplicationToolsUtils applicationToolsUtils;

    /**
     * 分页查询
     */
    @GetMapping
    public Result index(ZsSign entity) {
        return ResultUtil.success(service.selectPage(entity));
    }

    /**
     * 获取详情
     */
    @GetMapping("/{id}")
    public Result details(@PathVariable Integer id) {
        return super.details(id);
    }

    /**
     * 新建保存
     */
    @PostMapping
    public Result save(@RequestBody ZsSign entity) {
        return super.save(entity);
    }

    /**
     * 编辑 更新
     */
    @PutMapping
    public Result update(@RequestBody ZsSign entity) {
        return super.update(entity);
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return super.delete(id);
    }

    @GetMapping("/user")
    public Result getByUserId(Principal principal) {
        SysUser member = applicationToolsUtils.currentUser();
        return service.selectByUserId(member.getUid());
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
        return "";
    }

}