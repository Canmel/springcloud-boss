package com.camel.survey.controller;

import com.baomidou.mybatisplus.service.IService;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.model.Args;
import com.camel.survey.service.ArgsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


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
 * ┃     ┃        < 前端控制器>
 * ┃     ┃
 * ┃     ┗━━━━┓   @author baily
 * ┃          ┣┓
 * ┃          ┏┛  @since 1.0
 * ┗┓┓┏━━━━┳┓┏┛
 * ┃┫┫    ┃┫┫    @date 2019-11-22
 * ┗┻┛    ┗┻┛
 */
@RestController
@RequestMapping("/args")
public class ArgsController extends BaseCommonController {


    @Autowired
    private ArgsService service;

    /**
     * 分页查询
     * @param entity
     * @param oAuth2Authentication
     * @return
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','DEVOPS')")
    public Result index(Args entity, OAuth2Authentication oAuth2Authentication) {
        return ResultUtil.success(service.selectPage(entity));
    }

    /**
     * 获取详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DEVOPS')")
    public Result details(@PathVariable Integer id) {
        return super.details(id);
    }

    /**
     * 新建保存
     * @param entity
     * @param oAuth2Authentication
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','DEVOPS')")
    public Result save(Args entity, OAuth2Authentication oAuth2Authentication) {
        return service.save(entity, oAuth2Authentication);
    }

    /**
     * 编辑 更新
     * @param entity
     */
    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN','DEVOPS')")
    public Result update(@RequestBody Args entity) {
        entity.setUpdateAt(new Date());
        return super.update(entity);
    }

    /**
     * 删除
     * @param id
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DEVOPS')")
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
        return "系统参数";
    }

}