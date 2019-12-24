package com.camel.survey.service.impl;

import com.camel.survey.exceptions.ExportFillDataException;
import com.camel.survey.service.ExportService;
import com.camel.survey.utils.ExportExcelUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class ExportServiceImpl implements ExportService {
    @Override
    public HSSFWorkbook total(Integer surveyId, List list, LinkedHashMap<String, String> fieldMap) {
        //创建一个WorkBook,对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        //在Workbook中，创建一个sheet，对应Excel中的工作薄（sheet）
        HSSFSheet sheet1 = wb.createSheet("asdasd");
        HSSFSheet sheet2 = wb.createSheet("asdasdss");
        //创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        //创建一个居中格式
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 填充工作表
        try {
            ExportExcelUtils.fillSheet(sheet1, list, fieldMap, style);
            ExportExcelUtils.fillSheet(sheet2, list, fieldMap, style);
        }catch (Exception ex) {
            throw new ExportFillDataException();
        }
        return wb;
    }
}
