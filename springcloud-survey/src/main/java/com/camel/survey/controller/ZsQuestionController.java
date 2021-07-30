package com.camel.survey.controller;

import com.baomidou.mybatisplus.service.IService;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.annotation.AuthIgnore;
import com.camel.survey.model.ZsQuestion;
import com.camel.survey.service.ZsQuestionService;
import com.camel.survey.vo.ZsAnswerSave;
import com.camel.survey.vo.ZsQuestionSave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
 *                ┃┫┫    ┃┫┫    @date 2019-12-09
 *                ┗┻┛    ┗┻┛
 */
@RestController
@RequestMapping("/zsQuestion")
public class ZsQuestionController extends BaseCommonController {


    @Autowired
    private ZsQuestionService service;

    /**
     * 分页查询
     * @param entity
     * @return
     */
    @GetMapping
    public Result index(ZsQuestion entity) {
        return ResultUtil.success(service.selectPage(entity));
    }

    /**
     * 获取详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result details(@PathVariable Integer id) {
        return super.details(id);
    }

    /**
     * 新建保存
     * @param zsQuestionSave
     * @param oAuth2Authentication
     */
    @PostMapping
    public Result save(@RequestBody ZsQuestionSave zsQuestionSave, OAuth2Authentication oAuth2Authentication) {
        return service.save(zsQuestionSave, oAuth2Authentication);
    }

    /**
     * 编辑 更新
     * @param entity
     * @param oAuth2Authentication
     */
    @PutMapping
    public Result update(@RequestBody ZsQuestionSave entity, OAuth2Authentication oAuth2Authentication) {
        return service.update(entity, oAuth2Authentication);
    }

    /**
     * 删除
     * @param id
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return super.delete(id);
    }

    /**
     * 保存答卷
     * @param zsAnswerSave
     * @param request
     */
    @AuthIgnore
    @PostMapping("/answer")
    public Result answer(@RequestBody ZsAnswerSave zsAnswerSave, HttpServletRequest request) {
        return service.saveAnswer(zsAnswerSave);
    }

    /**
     * 保存答卷APP
     * @param zsAnswerSave
     * @param request
     */
    @PostMapping("/answerApp")
    public Result answerApp(@RequestBody ZsAnswerSave zsAnswerSave, HttpServletRequest request) {
        return service.saveAnswer(zsAnswerSave);
    }

    /**
     * 修改问题题目
     * @param entity
     */
    @PostMapping("/simpleUpdate")
    public Result updateSimply(@RequestBody ZsQuestion entity) {
        return super.update(entity);
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
