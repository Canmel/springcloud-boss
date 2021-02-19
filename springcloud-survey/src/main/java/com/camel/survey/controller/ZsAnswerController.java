package com.camel.survey.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.model.SysUser;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.model.*;
import com.camel.survey.service.*;
import com.camel.survey.utils.FileUtils;
import com.camel.survey.utils.StockUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.tomcat.util.modeler.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashSet;
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
 * ┃┫┫    ┃┫┫    @date 2019-12-17
 * ┗┻┛    ┗┻┛
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
    private ZsAnswerService answerService;

    @Autowired
    private ZsCdrinfoService cdrinfoService;

    @Autowired
    private FileUtils fileUtils;

    @Autowired
    private StockUtils stockUtils;

    /**
     * 分页查询
     *
     * @param entity
     * @return
     */
    @GetMapping
    public Result index(ZsAnswer entity) {
        return ResultUtil.success(service.selectPage(entity));
    }

    @GetMapping("/changeRadio")
    public Result changeRadio(Integer answerId, Integer questionId, Integer optionId) {
        // target option
        ZsOption n = optionService.selectById(optionId);
        // 验证配额
        if (ObjectUtil.isNotEmpty(n.getCurrent()) && ObjectUtil.isNotEmpty(n.getConfigration()) && n.getCurrent() >= n.getConfigration()) {
            return ResultUtil.error(ResultEnum.NOT_VALID_PARAM.getCode(), "目标选项暂无空闲配额");
        }

        Wrapper wrapper = new EntityWrapper<ZsAnswerItem>();
        wrapper.eq("answer_id", answerId);
        wrapper.eq("question_id", questionId);
        wrapper.eq("type", 1);
        ZsAnswerItem zsAnswerItem = answerItemService.selectOne(wrapper);
        ZsOption o = optionService.selectById(zsAnswerItem.getOptionId());
        if (ObjectUtils.notEqual(n.getTarget(), o.getTarget())) {
            return ResultUtil.error(ResultEnum.NOT_VALID_PARAM.getCode(), "逻辑关系不一致，重选失败");
        }
        zsAnswerItem.setOptionId(optionId);
        zsAnswerItem.setValue(n.getName());
        answerItemService.updateById(zsAnswerItem);

        // 修改配额
        List<Integer> reduceOption = new ArrayList<>();
        List<Integer> addOption = new ArrayList<>();
        reduceOption.add(o.getId());
        addOption.add(optionId);
        stockUtils.changeOption(reduceOption, addOption);
        return ResultUtil.success("修改成功");
    }

    @GetMapping("/addCheckbox")
    public Result addCheckbox(Integer answerId, Integer questionId, Integer optionId, String value) {
        // 先找到这个问卷的所有回答选项
        ZsQuestion question = questionService.selectById(questionId);
        ZsAnswer answer = answerService.selectById(answerId);
        ZsOption option = optionService.selectById(optionId);
        if (ObjectUtil.isNull(question)) {
            return ResultUtil.error(ResultEnum.NOT_VALID_PARAM.getCode(), "参数错误");
        }
        Integer surveyId = question.getSurveyId();
        Wrapper wrapper = new EntityWrapper<ZsAnswerItem>();
        wrapper.eq("answer_id", answerId);
        wrapper.eq("survey_id", question.getSurveyId());
        List<ZsAnswerItem> zsAnswerItemList = answerItemService.selectList(wrapper);
        Set<Integer> selectedQuestionId = new HashSet<>();
        for (ZsAnswerItem item : zsAnswerItemList) {
            selectedQuestionId.add(item.getQuestionId());
        }
        ZsOption n = optionService.selectById(optionId);
        if (ObjectUtil.isNull(n)) {
            return ResultUtil.error(ResultEnum.NOT_VALID_PARAM.getCode(), "参数错误");
        }
        if (!selectedQuestionId.contains(n.getQuestionId())) {
            return ResultUtil.error(ResultEnum.NOT_VALID_PARAM.getCode(), "逻辑关系不一致，重选失败");
        }
        answerItemService.insert(new ZsAnswerItem(answerId, surveyId, questionId, question.getName(), ObjectUtil.isEmpty(value) ? option.getName() : value, 2, answer.getCreator(), option.getName(), optionId, 1));
        Integer[] addOption = {optionId};
        stockUtils.addCurrent(CollectionUtils.arrayToList(addOption));
        return ResultUtil.success("修改成功");
    }

    @GetMapping("/reduceCheckbox")
    public Result reduceCheckbox(Integer answerId, Integer questionId, Integer optionId, String value) {
        // 先找到这个问卷的所有回答选项
        ZsQuestion question = questionService.selectById(questionId);
        ZsAnswer answer = answerService.selectById(answerId);
        ZsOption option = optionService.selectById(optionId);
        if (ObjectUtil.isNull(question)) {
            return ResultUtil.error(ResultEnum.NOT_VALID_PARAM.getCode(), "参数错误");
        }
        Integer surveyId = question.getSurveyId();
        Wrapper wrapper = new EntityWrapper<ZsAnswerItem>();
        wrapper.eq("answer_id", answerId);
        wrapper.eq("survey_id", question.getSurveyId());
        List<ZsAnswerItem> zsAnswerItemList = answerItemService.selectList(wrapper);
        Set<Integer> selectedQuestionId = new HashSet<>();
        List<Integer> selectedQuestionFullId = new ArrayList<>();
        for (ZsAnswerItem item : zsAnswerItemList) {
            selectedQuestionId.add(item.getQuestionId());
            selectedQuestionFullId.add(item.getQuestionId());
        }
        ZsOption n = optionService.selectById(optionId);
        if (ObjectUtil.isNull(n)) {
            return ResultUtil.error(ResultEnum.NOT_VALID_PARAM.getCode(), "参数错误");
        }
        selectedQuestionFullId.remove(option.getQuestionId());
        List<Integer> myList = selectedQuestionFullId.stream().distinct().collect(Collectors.toList());
        wrapper.eq("option_id", optionId);
        if (!myList.containsAll(selectedQuestionId)) {
            return ResultUtil.error(ResultEnum.NOT_VALID_PARAM.getCode(), "逻辑关系不一致，重选失败");
        }
        answerItemService.delete(wrapper);
        Integer[] reduceOption = {optionId};
        stockUtils.reduceCurrent(CollectionUtils.arrayToList(reduceOption));
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
        Set<String> agents = service.selectAgentUuidsByEntity(entity);
        List<ZsCdrinfo> cdrinfos = cdrinfoService.selectList(agents);

        fileUtils.downloadZipFiles(response, cdrinfos, zsSurvey.getName());
    }

    /**
     * 获取详情///
     *
     * @param id
     * @return
     */
    @GetMapping("/full/{id}")
    public Result full(@PathVariable Integer id) {
        return ResultUtil.success(service.details(id));
    }

    /**
     * 获取详情
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result details(@PathVariable Integer id) {
        return ResultUtil.success(service.details(id));
    }

    /**
     * 获取审核答卷
     *
     * @param agent
     * @return
     */
    @GetMapping("/agent/{agent}")
    public Result detailsp(@PathVariable("agent") String agent) {
        return ResultUtil.success(service.details(agent));
    }

    /**
     * 新建保存
     *
     * @param entity
     */
    @PostMapping
    public Result save(@RequestBody ZsAnswer entity) {
        return super.save(entity);
    }

    /**
     * 编辑 更新
     *
     * @param entity
     */
    @PutMapping
    public Result update(@RequestBody ZsAnswer entity) {
        return super.update(entity);
    }

    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return service.deleteAnswer(id);
    }

    /**
     * 更改答卷状态
     *
     * @param id
     */
    @GetMapping("/invalid/{id}")
    public Result invalid(@PathVariable Integer id) {
        return service.invalid(id);
    }

    /**
     * 审核答卷
     *
     * @param answerId
     * @param reviewStatus
     * @param reviewMsg
     */
    @PostMapping("/review")
    public Result review(Integer answerId, Integer reviewStatus, String reviewMsg, Integer reviewSpent) {
        if (service.review(answerId, reviewStatus, reviewMsg, reviewSpent)) {
            return ResultUtil.success("审核成功");
        } else {
            return ResultUtil.error(ResultEnum.BAD_REQUEST.getCode(), "审核失败");
        }
    }

    /**
     * 获取待审核列表
     *
     * @param entity
     * @return
     */
    @GetMapping("/randoms")
    public Result randoms(ZsAnswer entity) {
        return ResultUtil.success(service.randomList(entity));
    }

    /**
     * 获取样本收集时间范围
     *
     * @param id
     * @return
     */
    @GetMapping("/selectTimeRange/{id}")
    public Result selectTimeRange(@PathVariable Integer id) {
        return ResultUtil.success("", service.selectTimeRange(id));
    }

    /**
     * 获取样本审核时间范围
     *
     * @param id
     * @return
     */
    @GetMapping("/selectTimeRangeReview/{id}")
    public Result selectTimeRangeReview(@PathVariable Integer id) {
        return ResultUtil.success("", service.selectTimeRangeReview(id));
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
