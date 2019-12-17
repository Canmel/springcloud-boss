package com.camel.survey.controller;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import com.camel.survey.service.ZsSurveyService;
import com.camel.survey.model.ZsSurvey;
import com.camel.core.controller.BaseCommonController;

import com.baomidou.mybatisplus.service.IService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;

import java.util.List;

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
 *                ┃┫┫    ┃┫┫    @date 2019-12-06
 *                ┗┻┛    ┗┻┛
 */
@RestController
@RequestMapping("/zsSurvey")
public class ZsSurveyController extends BaseCommonController {


    @Autowired
    private ZsSurveyService service;

    /**
    * 分页查询
    */
    @GetMapping
    public Result index(ZsSurvey entity, OAuth2Authentication oAuth2Authentication) {
        return ResultUtil.success(service.selectPage(entity, oAuth2Authentication));
    }

    /**
    * 获取详情
    */
    @GetMapping("/{id}")
    public Result details(@PathVariable Integer id){
        return super.details(id);
    }

    /**
    * 新建保存
    */
    @PostMapping
    public Result save(@RequestBody ZsSurvey entity, OAuth2Authentication oAuth2Authentication) {
        return service.save(entity, oAuth2Authentication);
    }

    /**
     * 编辑 更新
     */
    @PutMapping
    public Result update(@RequestBody ZsSurvey entity) {
        return service.update(entity);
    }

    /**
    * 删除
    */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return super.delete(id);
    }

    @GetMapping("/{id}/projects")
    public Result projects(@PathVariable Integer id) {
        return service.selectListByProjectId(id);
    }

    /**
     * 获取问卷的所有问题和选项
     * @param id
     * @return Result 结果集包含vo.ZsQuestionSave
     *  ZsQuestionSave中包含question and option
     */
    @GetMapping("/questionAndOptions/{id}")
    public Result loadQuestionAndOptions(@PathVariable Integer id) {
        return service.getQuestionAndOptions(id);
    }

    /**
     * 开始调查
     * @param id
     * @return
     */
    @GetMapping("/start/{id}")
    public Result start(@PathVariable Integer id){
        return service.start(id);
    }

    /**
     * 申请参加
     * @return
     */
    @GetMapping("/sign/{id}")
    public Result sign(@PathVariable Integer id, OAuth2Authentication oAuth2Authentication) {
        return service.sign(id, oAuth2Authentication);
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