package com.camel.survey.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.camel.survey.enums.ZsStatus;
import com.camel.survey.exceptions.ExportFillDataException;
import com.camel.survey.mapper.ZsAnswerMapper;
import com.camel.survey.model.*;
import com.camel.survey.service.*;
import com.camel.survey.utils.ExportExcelUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.*;

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
@Service
public class ExportServiceImpl implements ExportService {
    @Autowired
    private ZsQuestionService zsQuestionService;

    @Autowired
    private ZsOptionService zsOptionService;

    @Autowired
    private ZsAnswerMapper zsAnswerMapper;

    @Override
    public HSSFWorkbook total(Integer surveyId, List list, LinkedHashMap<String, String> fieldMap) {
        //创建一个WorkBook,对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        List<ZsQuestion> questions = zsQuestionService.selectBySurveyId(surveyId);
        questions.forEach(question -> {
            HSSFCellStyle style = createCellStyle(wb);
            Wrapper<ZsOption> optionWrapper = new EntityWrapper<>();
            optionWrapper.eq("question_id", question.getId());
            optionWrapper.eq("status", ZsStatus.CREATED.getValue());
            List<ZsOption> optionList = zsOptionService.selectList(optionWrapper);
            List<Map<String, Object>> answers = selectRateBySurveyQuestion(surveyId, question.getName());
            Integer count = 0;

            HSSFSheet sheet = wb.createSheet("Q" + question.getOrderNum());
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            //创建单元格，并设置值表头 设置表头居中
            try {
                // 填充工作表
                HSSFRow row = sheet.createRow(0);
                HSSFCell cell = row.createCell(0);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
                fillCell(cell, createHeadStyle(wb), "Q" + question.getOrderNum() + "." + question.getName());
                HSSFRow rowTitle = sheet.createRow(1);
                fillCell(rowTitle.createCell(0), createTitleStyle(wb), "");
                fillCell(rowTitle.createCell(1), createTitleStyle(wb), "选项");
                fillCell(rowTitle.createCell(2), createTitleStyle(wb), "个数");
                fillCell(rowTitle.createCell(3), createTitleStyle(wb), "百分比");

                for (int i = 0; i < optionList.size(); i++) {
                    Integer cellIndex = 0;
                    HSSFRow optionRow = sheet.createRow(2 + i);
                    fillCell(optionRow.createCell(cellIndex++), style, i + 1);
                    fillCell(optionRow.createCell(cellIndex++), style, optionList.get(i).getName());
                    for (Map<String, Object> answer : answers) {
                        if (count == 0) {
                            count = Integer.parseInt(answer.get("count").toString());
                        }
                        if (answer.get("option").equals(optionList.get(i).getName())) {
                            String num = answer.get("num").toString();
                            fillCell(optionRow.createCell(cellIndex++), style, Integer.parseInt(num));
                            fillCell(optionRow.createCell(cellIndex), style, (String) answer.get("rate"));
                        }
                    }
                    if (cellIndex < 3) {
                        fillCell(optionRow.createCell(cellIndex++), style, 0);
                        fillCell(optionRow.createCell(cellIndex), style, 0);
                    }
                }
                HSSFRow row6 = sheet.createRow(optionList.size() + 2);
                fillRow(sheet.createRow(optionList.size() + 2), style, CollectionUtils.arrayToList(new String[]{"合计", "", count.toString(), "100%"}));

            } catch (Exception ex) {
                throw new ExportFillDataException();
            }
        });
        return wb;
    }

    void fillRow(HSSFRow row, HSSFCellStyle style, List<String> values) {
        Integer index = 0;
        for (String value: values) {
            fillCell(row.createCell(index++), style, value);
        }
    }

    HSSFCellStyle createHeadStyle(HSSFWorkbook wb) {
        HSSFCellStyle style = wb.createCellStyle();
        style.setFillBackgroundColor(IndexedColors.BLUE_GREY.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_THIN);
        return style;
    }

    HSSFCellStyle createTitleStyle(HSSFWorkbook wb) {
        HSSFCellStyle style = wb.createCellStyle();
        style.setFillBackgroundColor(IndexedColors.BLUE_GREY.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setFillForegroundColor(HSSFColor.LIME.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        return style;
    }

    HSSFCellStyle createCellStyle(HSSFWorkbook wb) {
        HSSFCellStyle style = wb.createCellStyle();
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        return style;
    }

    public HSSFCell fillCell(HSSFCell cell, HSSFCellStyle style, String value) {
        cell.setCellValue(value);
        cell.setCellStyle(style);
        return cell;
    }

    public HSSFCell fillCell(HSSFCell cell, HSSFCellStyle style, Integer value) {
        value = ObjectUtils.isEmpty(value) ? 0 : value;
        cell.setCellValue(value);
        cell.setCellStyle(style);
        return cell;
    }

    List<Map<String, Object>> selectRateBySurveyQuestion(Integer id, String question) {
        return zsAnswerMapper.selectRateBySurveyQuestion(id, question);
    }
}
