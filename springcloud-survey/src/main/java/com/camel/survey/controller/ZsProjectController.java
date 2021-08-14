package com.camel.survey.controller;

import com.baomidou.mybatisplus.service.IService;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.model.ZsProject;
import com.camel.survey.service.ZsProjectService;
import com.camel.survey.service.ZsSurveyService;
import com.camel.survey.utils.ApplicationToolsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
 * ┃┫┫    ┃┫┫    @date 2019-12-04
 * ┗┻┛    ┗┻┛
 */
@RestController
@RequestMapping("/zsProject")
public class ZsProjectController extends BaseCommonController {


    @Autowired
    private ZsProjectService service;

    @Autowired
    private ZsSurveyService surveyService;

    @Autowired
    private ApplicationToolsUtils applicationToolsUtils;

    /**
     * 分页查询
     *
     * @param entity
     * @return
     */
    @GetMapping
    public Result index(ZsProject entity) {
        return ResultUtil.success(service.selectPage(entity));
    }

    /**
     * 根据督导分页查询
     *
     * @param entity
     * @return
     */
    @GetMapping("/Dev")
    public Result indexDev(ZsProject entity) {
        return ResultUtil.success(service.selectPageDev(entity));
    }

    /**
     * 获取详情
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result details(@PathVariable Integer id) {
        return super.details(id);
    }

    /**
     * 新建保存
     *
     * @param entity
     */
    @PostMapping
    public Result save(ZsProject entity, OAuth2Authentication oAuth2Authentication) {
        entity.setCompanyId(applicationToolsUtils.currentUser().getCompanyId());
        return service.save(entity, oAuth2Authentication);
    }

    /**
     * 编辑 更新
     *
     * @param entity
     */
    @PutMapping
    public Result update(@RequestBody ZsProject entity) {
        return super.update(entity);
    }

    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return super.delete(id);
    }

    /**
     * 问卷通过导入新增
     *
     * @param file
     * @return
     */
    @PostMapping("/importSurvey/{id}")
    public Result importSurvey(@RequestParam("file") MultipartFile file, @PathVariable("id") Integer surveyId) {
        surveyService.importSurvey(file, surveyId);
        return ResultUtil.success("成功");
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
