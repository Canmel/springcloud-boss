package com.camel.survey.controller;

import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.model.ZsAnswer;
import com.camel.survey.model.ZsQuestion;
import com.camel.survey.model.ZsSurvey;
import com.camel.survey.service.*;
import com.camel.survey.utils.ExportExcelUtils;
import com.camel.survey.vo.ZsCrossExport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.List;

/**
 *
 *                 ___====-_  _-====___
 *           _--^^^#####//      \\#####^^^--_
 *        _-^##########// (    ) \\##########^-_
 *       -############//  |\^^/|  \\############-
 *     _/############//   (@::@)   \\############\_
 *    /#############((     \\//     ))#############\
 *   -###############\\    (oo)    //###############-
 *  -#################\\  / VV \  //#################-
 * -###################\\/      \//###################-
 *_#/|##########/\######(   /\   )######/\##########|\#_
 *|/ |#/\#/\#/\/  \#/\##\  |  |  /##/\#/  \/\#/\#/\#| \|
 *`  |/  V  V  `   V  \#\| |  | |/#/  V   '  V  V  \|  '
 *   `   `  `      `   / | |  | | \   '      '  '   '
 *                    (  | |  | |  )
 *                   __\ | |  | | /__
 *                  (vvv(VVV)(VVV)vvv)
 * <导出>
 * @author baily
 * @since 1.0
 * @date 2019/12/24
 **/
@RequestMapping("/export")
@RestController
public class ExportController {

    @Autowired
    private ZsSurveyService zsSurveyService;

    @Autowired
    private ZsQuestionService zsQuestionService;

    @Autowired
    private ZsOptionService zsOptionService;

    @Autowired
    private ZsAnswerService answerService;

    @Autowired
    private ExportService service;

    @GetMapping("/survey/form")
    public void index(ZsAnswer entity, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ExportExcelUtils.export(service.form(entity), "样本分析", response);
    }

    /**
     * 问卷汇总导出
     * @param id
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/survey/total/{id}")
    public void total(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response) {
        ZsSurvey survey = zsSurveyService.selectById(id);
        //导出用户相关信息
        ExportExcelUtils.export(service.total(id), "项目答题统计_" + survey.getName(), response);
    }

    /**
     * 问卷交叉导出
     * @param zsCrossExport
     * @param response
     * @return
     */
    @GetMapping("/survey/cross")
    public void cross(ZsCrossExport zsCrossExport, HttpServletResponse response) {
        ZsSurvey survey = zsSurveyService.selectById(zsCrossExport.getSurveyId());
        ExportExcelUtils.export(service.cross(zsCrossExport), "问题交叉统计_" + survey.getName(), response);
    }

    /**
     * 问卷交叉导出不带%
     * @param zsCrossExport
     * @param response
     * @return
     */
    @GetMapping("/survey/crossSimple")
    public void crossSimple(ZsCrossExport zsCrossExport, HttpServletResponse response) {
        ZsSurvey survey = zsSurveyService.selectById(zsCrossExport.getSurveyId());
        ExportExcelUtils.export(service.crossSimple(zsCrossExport), "问题交叉统计_" + survey.getName(), response);
    }

    /**
     * 问卷问题导出
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/survey/{id}/items")
    public void items(@PathVariable Integer id, HttpServletResponse response) {
        ZsQuestion question = zsQuestionService.selectById(id);
        ZsSurvey survey = zsSurveyService.selectById(question.getSurveyId());
        ExportExcelUtils.export(service.items(survey.getId(), id), "问卷样本统计_" + survey.getName(), response);
    }

    /**
     * 问卷明细导出
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/survey/answers/{id}")
    public void answers(@PathVariable Integer id, HttpServletResponse response) throws ParseException {
        ZsSurvey survey = zsSurveyService.selectById(id);
        ExportExcelUtils.export(service.answer(id), "收集样本明细_" + survey.getName(), response);
    }

    /**
     * 问卷坐席明细导出
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/survey/workNum/{id}")
    public void seat(@PathVariable Integer id, HttpServletResponse response) throws ParseException {
        ZsSurvey survey = zsSurveyService.selectById(id);
        ExportExcelUtils.export(service.workNum(id), "访员样本统计_" + survey.getName(), response);
    }

}
