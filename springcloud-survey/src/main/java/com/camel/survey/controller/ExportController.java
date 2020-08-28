package com.camel.survey.controller;

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
    private ZsAnswerItemService zsAnswerItemService;

    @Autowired
    private ExportService service;

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
        ExportExcelUtils.export(service.total(id), survey.getName(), response);
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
        ExportExcelUtils.export(service.cross(zsCrossExport), "交叉_" + survey.getName(), response);
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
    public void answers(@PathVariable Integer id, HttpServletResponse response) {
        ZsSurvey survey = zsSurveyService.selectById(id);
        ExportExcelUtils.export(service.answer(id), "样本明细_" + survey.getName(), response);
    }

    /**
     * 问卷坐席明细导出
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/survey/workNum/{id}")
    public void seat(@PathVariable Integer id, HttpServletResponse response) {
        ZsSurvey survey = zsSurveyService.selectById(id);
        ExportExcelUtils.export(service.workNum(id), "工号收集统计_" + survey.getName(), response);
    }

}
