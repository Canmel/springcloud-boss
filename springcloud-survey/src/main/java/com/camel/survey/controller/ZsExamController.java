package com.camel.survey.controller;

import com.baomidou.mybatisplus.service.IService;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.model.ZsExam;
import com.camel.survey.service.ZsExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
 *             ┗━┓     ┏━┛
 *               ┃     ┃　Code is far away from bug with the animal protecting　　　　　　　　　　
 *               ┃     ┃   神兽保佑,代码无bug
 *               ┃     ┃　　　　　　　　　　　
 *               ┃     ┃  　　　　　　
 *               ┃     ┃        < 前端控制器>
 *               ┃     ┃　　　　　　　　　　　
 *               ┃     ┗━━━━┓   @author baily
 *               ┃          ┣┓
 *               ┃          ┏┛  @since 1.0
 *               ┗┓┓┏━━━━┳┓┏┛
 *                ┃┫┫    ┃┫┫    @date 2019-12-12
 *                ┗┻┛    ┗┻┛
 */
@RestController
@RequestMapping("/zsExam")
public class ZsExamController extends BaseCommonController {


    @Autowired
    private ZsExamService service;

    /**
     * 分页查询
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Result serviceindex(ZsExam entity, OAuth2Authentication oAuth2Authentication) {
        return ResultUtil.success(service.selectPage(entity, oAuth2Authentication));
    }

    /**
     * 获取详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Result details(@PathVariable Integer id) {
        return super.details(id);
    }

    /**
     * 新建保存
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Result save(ZsExam entity, OAuth2Authentication oAuth2Authentication) {
        return service.save(entity, oAuth2Authentication);
    }

    /**
     * 编辑 更新
     */
    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Result update(@RequestBody ZsExam entity) {
        return super.update(entity);
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Result delete(@PathVariable Integer id) {
        return super.delete(id);
    }

    @GetMapping("/delivery/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Result delivery(OAuth2Authentication oAuth2Authentication, @PathVariable Integer id) {
        return service.delivery(id, oAuth2Authentication);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Result all() {
        return service.all();
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