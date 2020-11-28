package com.camel.survey.utils.export;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import com.camel.survey.mapper.ZsAnswerItemMapper;
import com.camel.survey.mapper.ZsAnswerMapper;
import com.camel.survey.model.*;
import com.camel.survey.service.ZsAnswerItemService;
import com.camel.survey.service.ZsAnswerService;
import com.camel.survey.service.ZsQuestionService;
import com.camel.survey.service.ZsSurveyService;
import com.camel.survey.utils.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.examples.CellTypes;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OptionExport {

    @Autowired
    private ZsSurveyService surveyService;

    @Autowired
    private ZsQuestionService questionService;

    @Autowired
    private ZsAnswerItemMapper answerItemMapper;

    @Autowired
    private ZsAnswerMapper zsAnswerMapper;

    public HSSFWorkbook options(HSSFWorkbook workbook, ZsAnswer zsAnswer) {
        ZsSurvey survey = surveyService.selectById(zsAnswer.getSurveyId());
        HSSFSheet sheet = workbook.createSheet("选项");
        List<ZsQuestion> questions = questionService.selectBySurveyId(zsAnswer.getSurveyId());
        List<ZsAnswer> answers = zsAnswerMapper.list(zsAnswer);
        List<Integer> aIds = answers.stream().map(ZsAnswer::getId).collect(Collectors.toList());
        List<ZsAnswerItem> answerItems = answerItemMapper.listByAnswers(aIds);
        addTitle(sheet, survey);
        addQuestion(sheet, questions, answerItems);
        return workbook;
    }

    /**
     * 添加标题
     *
     * @param sheet
     */
    private void addTitle(HSSFSheet sheet, ZsSurvey survey) {
        HSSFCellStyle headStyle = HSSFUtils.createHeadStyle(sheet.getWorkbook());
        ExcelUtil.setTotalTitle(survey.getName(), sheet.createRow(0), sheet);
    }

    /**
     * 添加问卷
     *
     * @param sheet
     * @param questionList
     */
    private void addQuestion(HSSFSheet sheet, List<ZsQuestion> questionList, List<ZsAnswerItem> answerItems) {
        for (ZsQuestion q : questionList) {
            HashSet<String> set = new HashSet<>();
            for (ZsOption o : q.getOptions()) {
                o.setCurrent(0);
                for(ZsAnswerItem item: answerItems) {
                    if(item.getOptionId().equals(o.getId())) {
                        o.setCurrent(o.getCurrent()+1);
                        set.add(q.getId() + "_" + o.getId() + "_" + item.getCreator());
                    }
                }
            }
            q.setAnswerCount(set.size());
        }
        Integer rowIndex = 1;
        HSSFCellStyle cellStyle = (HSSFCellStyle) HSSFUtils.createStyle(sheet.getWorkbook());
        for (ZsQuestion q : questionList) {
            StringBuilder type = new StringBuilder();
            type.append("[").append((q.getType().equals(1) ? "单选题" : q.getType() == 2 ? "多选题" : "问答题")).append("]");
            type.append(" 第").append(q.getOrderNum()).append("题： ").append(q.getName().trim());
            ExcelUtil.setTitle(type.toString(), sheet.createRow(rowIndex++), sheet);
            Integer allNum = 0;
            for (ZsOption o : q.getOptions()) {
                allNum += ObjectUtil.isNotEmpty(o.getCurrent()) ? o.getCurrent() : 0;
            }
            for (ZsOption o : q.getOptions()) {
                Integer c = ObjectUtil.isEmpty(o.getCurrent()) ? 0 : o.getCurrent();
                Double s = (1.0 * c) / allNum;
                StringBuilder option = new StringBuilder();
                option.append(o.getOrderNum()).append(": ").append(o.getName());
                if(!ObjectUtils.isEmpty(o.getScore())) {
                    option.append("(计").append(o.getScore()).append("分)");
                }
                ExcelUtil.setOption(option.toString(), sheet.createRow(rowIndex), sheet);
                sheet.getRow(rowIndex).createCell(13).setCellStyle(cellStyle);
                sheet.getRow(rowIndex).getCell(13).setCellValue(c);
                sheet.getRow(rowIndex).createCell(14).setCellStyle(cellStyle);
                sheet.getRow(rowIndex).getCell(14).setCellValue(NumberUtil.formatPercent(s, 2));
                rowIndex++;
            }
            ExcelUtil.setUserCont("本题答题人数：", sheet.createRow(rowIndex), sheet);
            sheet.getRow(rowIndex).createCell(13).setCellValue(q.getAnswerCount());
            sheet.getRow(rowIndex).getCell(13).setCellStyle(cellStyle);
            sheet.getRow(rowIndex++).createCell(14).setCellStyle(cellStyle);
        }
    }
}
