package com.camel.survey.controller;

import com.camel.survey.model.ZsAnswerItem;
import com.camel.survey.model.ZsQuestion;
import com.camel.survey.model.ZsSurvey;
import com.camel.survey.service.*;
import com.camel.survey.utils.ExportExcelUtils;
import com.camel.survey.vo.Excel;
import com.camel.survey.vo.ZsCrossExport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
    private ZsAnswerItemService zsAnswerItemService;

    @Autowired
    private ExportService service;

    @GetMapping("/survey/total/{id}")
    public void total(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response) {
        ZsSurvey survey = zsSurveyService.selectById(id);
        //导出用户相关信息
        ExportExcelUtils.export(service.total(id), survey.getName(), response);
    }

    @GetMapping("/survey/cross")
    public void cross(ZsCrossExport zsCrossExport, HttpServletResponse response) {
        ZsSurvey survey = zsSurveyService.selectById(1);
        ExportExcelUtils.export(service.total(1), "测试是不是真的", response);
    }

}
