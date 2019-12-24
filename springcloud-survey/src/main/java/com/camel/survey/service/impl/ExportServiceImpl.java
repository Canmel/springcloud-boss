package com.camel.survey.service.impl;

import com.camel.survey.exceptions.ExportFillDataException;
import com.camel.survey.model.ZsQuestion;
import com.camel.survey.model.ZsSurvey;
import com.camel.survey.service.ExportService;
import com.camel.survey.service.ZsQuestionService;
import com.camel.survey.service.ZsSurveyService;
import com.camel.survey.utils.ExportExcelUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
public class ExportServiceImpl implements ExportService {
    @Autowired
    private ZsQuestionService zsQuestionService;

    @Override
    public HSSFWorkbook total(Integer surveyId, List list, LinkedHashMap<String, String> fieldMap) {
        //创建一个WorkBook,对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        List<ZsQuestion> questions = zsQuestionService.selectBySurveyId(surveyId);
        questions.forEach(question -> {
            HSSFSheet sheet = wb.createSheet("Q" + question.getOrderNum());
            //创建单元格，并设置值表头 设置表头居中
            HSSFCellStyle style = wb.createCellStyle();
            //创建一个居中格式
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            try {
                // 填充工作表
                HSSFRow row = sheet.createRow(0);
                HSSFCell cell = row.createCell(0);
                cell.setCellValue("Q" + question.getOrderNum() + "." + question.getName());
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
            } catch (Exception ex) {
                throw new ExportFillDataException();
            }
        });
        return wb;
    }
}
