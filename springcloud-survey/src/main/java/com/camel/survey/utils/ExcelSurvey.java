package com.camel.survey.utils;


import java.io.*;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

public class ExcelSurvey {


    public static void main(String[] args) {
        writeExcel("222","D://");
    }
    public static void writeExcel(String name, String pathName) {
        //使用apache的poI HssFworkbook对象写入Excel并实现样式
        HSSFWorkbook workbook = new HSSFWorkbook();

        //创建sheet页并以传入的问卷名称创建
        HSSFSheet sheet = workbook.createSheet(name);
        //设置第一sheet页里的列宽
        sheet.setColumnWidth(0,(short)256*(short)6.38+(short)184);
        sheet.setColumnWidth(1,(short)256*(short)42.25+(short)184);
        sheet.setColumnWidth(2,(short)256*(short)23.38+(short)184);
        sheet.setColumnWidth(3,(short)256*(short)10.63+(short)184);
        sheet.setColumnWidth(4,(short)256*(short)8.38+(short)184);
        sheet.setColumnWidth(5,(short)256*(short)8.38+(short)184);
        sheet.setColumnWidth(6,(short)256*(short)28.13+(short)184);
        sheet.setColumnWidth(7,(short)256*(short)8.38+(short)184);
        sheet.setColumnWidth(8,(short)256*(short)8.38+(short)184);
        sheet.setColumnWidth(9,(short)256*(short)12.25+(short)184);
        HSSFCellStyle style = cellStyle(workbook);
        //创建第一行
        HSSFRow row = sheet.createRow(0);
        //设置第一行
        creatHSSFCellStyle(row,workbook);

        FileOutputStream outputStream = null;
        try {
            File file = new File(pathName+name+".xls");
            if(file.exists()){
                file.delete();
            };
            outputStream = new FileOutputStream(pathName+name+".xls");
            workbook.write(outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置单元格边框
     * @param workbook
     * @return
     */
    public static HSSFCellStyle cellStyle(HSSFWorkbook workbook){
        HSSFCellStyle style = workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);//下边框
        style.setBorderLeft(BorderStyle.THIN);//左边框
        style.setBorderRight(BorderStyle.THIN);//右边框
        style.setBorderTop(BorderStyle.THIN);//上边框
        style.setFillForegroundColor(HSSFColor.GOLD.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        HSSFFont f  = workbook.createFont();
        f.setFontHeightInPoints((short) 10);//字号
        f.setBold(true);//加粗
        style.setFont(f);
        style.setAlignment(HorizontalAlignment.CENTER);//左右居中
        return style;
    }

    public static HSSFCellStyle backGround(HSSFWorkbook workbook){
        HSSFCellStyle style = workbook.createCellStyle();

        return style;
    }
    /**
     * 设置Ecel第一行信息
     * @param row
     * @param workbook
     * @return
     */
    public static HSSFCell creatHSSFCellStyle(HSSFRow row, HSSFWorkbook workbook){
        HSSFCellStyle style = cellStyle(workbook);
        HSSFCell rowCell1 = row.createCell(0);
        rowCell1.setCellValue("题号");
        rowCell1.setCellStyle(style);
        HSSFCell rowCell2 = row.createCell(1);
        rowCell2.setCellValue("题目");
        rowCell2.setCellStyle(style);
        HSSFCell rowCell3 = row.createCell(2);
        rowCell3.setCellValue("类型");
        rowCell3.setCellStyle(style);
        HSSFCell rowCell4 = row.createCell(3);
        rowCell4.setCellValue("最多选择");
        rowCell4.setCellStyle(style);
        HSSFCell rowCell5 = row.createCell(4);
        rowCell5.setCellValue("最少选择");
        rowCell5.setCellStyle(style);
        HSSFCell rowCell6 = row.createCell(5);
        rowCell6.setCellValue("项号");
        rowCell6.setCellStyle(style);
        HSSFCell rowCell7 = row.createCell(6);
        rowCell7.setCellValue("选项");
        rowCell7.setCellStyle(style);
        HSSFCell rowCell8 = row.createCell(7);
        rowCell8.setCellValue("跳转至");
        rowCell8.setCellStyle(style);
        HSSFCell rowCell9 = row.createCell(8);
        rowCell9.setCellValue("忽略配额");
        rowCell9.setCellStyle(style);
        HSSFCell rowCell10 = row.createCell(9);
        rowCell10.setCellValue("是否为备注");
        rowCell10.setCellStyle(style);
        return rowCell1;
    }
}
