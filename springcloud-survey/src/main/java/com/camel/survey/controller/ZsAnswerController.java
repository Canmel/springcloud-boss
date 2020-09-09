package com.camel.survey.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.model.*;
import com.camel.survey.service.*;
import com.camel.survey.utils.FileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.tomcat.util.modeler.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
 *                ┃┫┫    ┃┫┫    @date 2019-12-17
 *                ┗┻┛    ┗┻┛
 */
@RestController
@RequestMapping("/zsAnswer")
public class ZsAnswerController extends BaseCommonController {


    @Autowired
    private ZsAnswerService service;

    @Autowired
    private ZsSurveyService surveyService;

    @Autowired
    private ZsQuestionService questionService;

    @Autowired
    private ZsOptionService optionService;

    @Autowired
    private ZsAnswerItemService answerItemService;

    @Autowired
    private ZsCdrinfoService cdrinfoService;

    /**
     * 分页查询
     * @param entity
     * @return
     */
    @GetMapping
    public Result index(ZsAnswer entity) {
        return ResultUtil.success(service.selectPage(entity));
    }

    @GetMapping("/changeRadio")
    public Result changeRadio(Integer answerId, Integer questionId, Integer optionId) {
        Wrapper wrapper = new EntityWrapper<ZsAnswerItem>();
        wrapper.eq("answer_id", answerId);
        wrapper.eq("question_id", questionId);
        wrapper.eq("type", 1);
        ZsAnswerItem zsAnswerItem = answerItemService.selectOne(wrapper);
        ZsOption n = optionService.selectById(optionId);
        ZsOption o = optionService.selectById(zsAnswerItem.getOptionId());
        if(ObjectUtils.notEqual(n.getTarget(), o.getTarget())) {
            return ResultUtil.error(ResultEnum.NOT_VALID_PARAM.getCode(), "逻辑关系不一致，重选失败");
        }
        zsAnswerItem.setOptionId(optionId);
        answerItemService.updateById(zsAnswerItem);
        return ResultUtil.success("修改成功");
    }

    @GetMapping("/changeRemark")
    public Result changeRemark(Integer id, String remark) {
        ZsAnswerItem zsAnswerItem = answerItemService.selectById(id);
        zsAnswerItem.setValue(remark);
        answerItemService.updateById(zsAnswerItem);
        return ResultUtil.success("修改成功");
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/download")
    public void download(ZsAnswer entity, HttpServletResponse response) {
        ZsSurvey zsSurvey = surveyService.selectById(entity.getSurveyId());
        entity.setPageSize(null);
        entity.setPageNum(null);
        Set<String> agents=  service.selectAgentUuidsByEntity(entity);
        List<ZsCdrinfo> cdrinfos = cdrinfoService.selectList(agents);

        FileUtils.getInstance().downloadZipFiles(response, cdrinfos, zsSurvey.getName());
    }

    /**
     * 获取详情///
     * @param id
     * @return
     */
    @GetMapping("/full/{id}")
    public Result full(@PathVariable Integer id) {
        return ResultUtil.success(service.details(id));
    }

    /**
     * 获取详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result details(@PathVariable Integer id) {
        return ResultUtil.success(service.details(id));
    }

    /**
     * 获取复核答卷
     * @param agent
     * @return
     */
    @GetMapping("/agent/{agent}")
    public Result detailsp(@PathVariable("agent") String agent) {
        return ResultUtil.success(service.details(agent));
    }

    /**
     * 新建保存
     * @param entity
     */
    @PostMapping
    public Result save(@RequestBody ZsAnswer entity) {
        return super.save(entity);
    }

    /**
     * 编辑 更新
     * @param entity
     */
    @PutMapping
    public Result update(@RequestBody ZsAnswer entity) {
        return super.update(entity);
    }

    /**
     * 删除
     * @param id
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return  service.deleteAnswer(id);
    }

    /**
     * 更改答卷状态
     * @param id
     */
    @GetMapping("/invalid/{id}")
    public Result invalid(@PathVariable Integer id) {
        return service.invalid(id);
    }

    /**
     * 复核答卷
     * @param answerId
     * @param reviewStatus
     * @param reviewMsg
     */
    @PostMapping("/review")
    public Result review(Integer answerId, Integer reviewStatus, String reviewMsg, Integer reviewSpent) {
        if(service.review(answerId, reviewStatus, reviewMsg, reviewSpent)) {
            return ResultUtil.success("复核成功");
        }else {
            return ResultUtil.error(ResultEnum.BAD_REQUEST.getCode(), "复核失败");
        }
    }

    /**
     * 获取待复核列表
     * @param entity
     * @return
     */
    @GetMapping("/randoms")
    public Result randoms(ZsAnswer entity) {
        return ResultUtil.success(service.randomList(entity));
    }

    /**
     * 获取样本时间范围
     * @param id
     * @return
     */
    @GetMapping("/selectTimeRange/{id}")
    public Result selectTimeRange(@PathVariable Integer id) {
        return ResultUtil.success("",service.selectTimeRange(id));
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