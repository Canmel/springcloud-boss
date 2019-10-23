package com.camel.oa.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.IService;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.oa.enums.ZsProjectLevels;
import com.camel.oa.model.ZsProject;
import com.camel.oa.service.ZsProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * 　　　　　　　 ┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━━━┛┻┓
 * 　　　　　　　┃         ┃
 * 　　　　　　　┃    ━    ┃
 * 　　　　　　　┃  >   <  ┃
 * 　　　　　　　┃         ┃
 * 　　　　　　　┃... ⌒ ...┃
 * 　　　　　　　┃         ┃
 * ┗━┓     ┏━┛
 * ┃     ┃　Code is far away from bug with the animal protecting
 * ┃     ┃   神兽保佑,代码无bug
 * ┃     ┃
 * ┃     ┃
 * ┃     ┃        <智慧招商项目 前端控制器>
 * ┃     ┃
 * ┃     ┗━━━━┓   @author baily
 * ┃          ┣┓
 * ┃          ┏┛  @since 1.0
 * ┗┓┓┏━━━━┳┓┏┛
 * ┃┫┫    ┃┫┫    @date 2019-10-22
 * ┗┻┛    ┗┻┛
 */
@RestController
@RequestMapping("/zsProject")
public class ZsProjectController extends BaseCommonController {


    @Autowired
    private ZsProjectService service;

    /**
     * 分页查询
     */
    @GetMapping
    public Result index(ZsProject entity) {
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
    public Result save(@RequestBody ZsProject entity, OAuth2Authentication oAuth2Authentication) {
        return service.save(entity, oAuth2Authentication);
    }

    @GetMapping("/all")
    public Result all() {
        return ResultUtil.success(service.selectList(new EntityWrapper<ZsProject>()));
    }

    /**
     * 编辑 更新
     */
    @PutMapping
    public Result update(@RequestBody ZsProject entity) {
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
     * 所有项目等级
     */
    @GetMapping("/levels")
    public Result levels() {
        return ResultUtil.success(ZsProjectLevels.all());
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