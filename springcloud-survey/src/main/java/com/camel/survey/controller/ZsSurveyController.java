package com.camel.survey.controller;

import com.baomidou.mybatisplus.service.IService;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.annotation.AuthIgnore;
import com.camel.survey.enums.ZsSurveyState;
import com.camel.survey.model.ZsSurvey;
import com.camel.survey.service.ZsSurveyService;
import com.camel.survey.vo.ZsAnswerSave;
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
    @PreAuthorize("hasAnyRole('ADMIN','INTERVIEWER')")
    public Result index(ZsSurvey entity, OAuth2Authentication oAuth2Authentication) {
        return ResultUtil.success(service.selectPage(entity, oAuth2Authentication));
    }

    /**
     * 获取详情
     */
    @AuthIgnore
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','INTERVIEWER')")
    public Result details(@PathVariable Integer id) {
        return super.details(id);
    }

    /**
     * 新建保存
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Result save(@RequestBody ZsSurvey entity, OAuth2Authentication oAuth2Authentication) {
        return service.save(entity, oAuth2Authentication);
    }

    /**
     * 编辑 更新
     */
    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Result update(@RequestBody ZsSurvey entity) {
        return service.update(entity);
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Result delete(@PathVariable Integer id) {
        return super.delete(id);
    }

    @PutMapping("/close/{id}")
    public Result close(@PathVariable Integer id){
        ZsSurvey survey = service.selectById(id);
        survey.setState(ZsSurveyState.CLOSED);
        service.updateById(survey);
        return ResultUtil.success("问卷 " + survey.getName() + " 已经关闭！");
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
    @AuthIgnore
    @GetMapping("/questionAndOptions/{id}")
    public Result loadQuestionAndOptions(@PathVariable Integer id) {
        return service.getQuestionAndOptions(id);
    }

    /**
     * 获取问卷的所有问题和选项
     * 为了问卷
     * @param id
     * @return Result 结果集包含vo.ZsQuestionSave
     *  ZsQuestionSave中包含question and option
     */
    @AuthIgnore
    @GetMapping("/questions/{id}")
    public Result loadQuestionSurvey(@PathVariable Integer id) {
        ZsSurvey survey = service.selectById(id);
        if(!survey.getState().equals(ZsSurveyState.COLLECTING)) {
            return ResultUtil.error(ResultEnum.BAD_REQUEST.getCode(), "该问卷尚未开始收集，请联系管理员");
        }
        return ResultUtil.success(service.questions(id));
    }

    /**
     * 开始调查
     * @param id
     * @return
     */
    @GetMapping("/start/{id}")
    public Result start(@PathVariable Integer id) {
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

    @PostMapping("/valid")
    public Result valid(@RequestBody ZsAnswerSave zsAnswerSave) {

        return service.valid(zsAnswerSave);
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