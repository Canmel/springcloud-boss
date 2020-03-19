package com.camel.survey.utils;

import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.camel.survey.annotation.ExcelAnnotation;
import com.camel.survey.exceptions.ExcelImportException;
import com.camel.survey.model.ZsOption;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.TextAlign;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

public class ExcelUtil {

    /**
     * 读取excel反射实体
     *
     * @param file  MultipartFile
     * @param clazz entity
     * @return
     * @throws RuntimeException
     */
    public static <T extends Object> List<T> readExcelObject(MultipartFile file, Class<T> clazz) {

        // 存储excel数据
        List<T> list = new ArrayList<>();
        Workbook wookbook = null;
        InputStream inputStream = null;

        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            throw new ExcelImportException("文件读取异常");
        }

        // 根据excel文件版本获取工作簿
        if (file.getOriginalFilename().endsWith(".xls")) {
            wookbook = xls(inputStream);
        } else if (file.getOriginalFilename().endsWith(".xlsx")) {
            wookbook = xlsx(inputStream);
        } else {
            throw new ExcelImportException("非excel文件");
        }

        // 得到一个工作表
        Sheet sheet = wookbook.getSheetAt(0);

        // 获取行总数
        int rows = sheet.getLastRowNum() + 1;
        Row row;

        // 获取类所有属性
        Field[] fields = clazz.getDeclaredFields();

        T obj = null;
        int coumnIndex = 0;
        Cell cell = null;
        ExcelAnnotation excelAnnotation = null;
        for (int i = 1; i < rows; i++) {
            // 获取excel行
            row = sheet.getRow(i);
            //此处用来过滤空行
            Cell cell0 = row.getCell(0);
            if (ObjectUtils.isEmpty(cell0)) {
                continue;
            }
            cell0.setCellType(CellType.STRING);
            Cell cell1 = row.getCell(1);
            cell1.setCellType(CellType.STRING);
            if (cell0.getStringCellValue() == "" && cell1.getStringCellValue() == "") {
                continue;
            }
            try {
                // 创建实体
                obj = clazz.newInstance();
                for (Field f : fields) {
                    // 设置属性可访问
                    f.setAccessible(true);
                    // 判断是否是注解
                    if (f.isAnnotationPresent(ExcelAnnotation.class)) {
                        // 获取注解
                        excelAnnotation = f.getAnnotation(ExcelAnnotation.class);
                        // 获取列索引
                        coumnIndex = excelAnnotation.columnIndex();
                        // 获取单元格
                        cell = row.getCell(coumnIndex);
                        // 设置属性
                        setFieldValue(obj, f, wookbook, cell);
                    }
                }
                System.out.println(obj);
                // 添加到集合中
                list.add(obj);
            } catch (InstantiationException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
                throw new ExcelImportException("excel文件内容出错");
            }
        }
        try {
            //释放资源
            wookbook.close();
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 绑定实体值
     *
     * @param obj      Object
     * @param f        Field
     * @param wookbook Workbook
     * @param cell     Cell
     * @return
     * @throws RuntimeException
     */
    private static void setFieldValue(Object obj, Field f, Workbook wookbook, Cell cell) {
        try {
            cell.setCellType(CellType.STRING);
            if (f.getType() == byte.class || f.getType() == Byte.class) {
                f.set(obj, Byte.parseByte(cell.getStringCellValue()));
            } else if (f.getType() == int.class || f.getType() == Integer.class) {
                f.set(obj, Integer.parseInt(cell.getStringCellValue()));
            } else if (f.getType() == Double.class || f.getType() == double.class) {
                f.set(obj, Double.parseDouble(cell.getStringCellValue()));
            } else if (f.getType() == BigDecimal.class) {
                f.set(obj, new BigDecimal(cell.getStringCellValue()));
            } else if (f.getType() == Date.class) {
                f.set(obj, HSSFDateUtil.getJavaDate(Double.parseDouble(cell.getStringCellValue())));
            } else {
                f.set(obj, cell.getStringCellValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 对excel 2003处理
     */
    private static Workbook xls(InputStream is) {
        try {
            // 得到工作簿
            return new HSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对excel 2007处理
     */
    private static Workbook xlsx(InputStream is) {
        try {
            // 得到工作簿
            return new XSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取sheet的名称
     *
     * @param name
     * @param qIndex
     * @return
     */
    public static String sheetName(String name, Integer qIndex) {
        StringBuffer stringBuffer = new StringBuffer("Q");
        stringBuffer.append(qIndex).append(".").append(name);
        return stringBuffer.toString();
    }

    /**
     * 合计表的表头
     *
     * @param title
     * @param sheet
     */
    public static void setTotalTitle(String title, HSSFSheet sheet) {
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue(title);
        cell.setCellStyle(createTitleStyle(sheet.getWorkbook()));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
    }

    public static HSSFCellStyle createHeadStyle(HSSFWorkbook wb) {
        HSSFCellStyle style = wb.createCellStyle();
        style.setFillBackgroundColor(IndexedColors.BLUE_GREY.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        return style;
    }

    public static HSSFCellStyle createTitleStyle(HSSFWorkbook wb) {
        HSSFCellStyle style = wb.createCellStyle();
        style.setFillBackgroundColor(IndexedColors.BLUE_GREY.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setFillForegroundColor(HSSFColor.LIME.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    public static HSSFCellStyle createCellStyle(HSSFWorkbook wb) {
        HSSFCellStyle style = wb.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    public static HSSFCellStyle createTotalHeadStyle(HSSFWorkbook wb) {
        HSSFCellStyle style = wb.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    public static void creatTotalHead(HSSFSheet sheet, Integer startRow) {
        String[] titles = {"序号", "选项", "样本情况"};
        int columIndex = 1;
        HSSFRow row = sheet.createRow(startRow);
        for (String title : titles) {
            HSSFCell cell = row.createCell(columIndex++);
            cell.setCellStyle(createTotalHeadStyle(sheet.getWorkbook()));
            cell.setCellValue(title);
        }
    }

//    public static void creatTotalBody(HSSFSheet sheet, Integer startRow, List<ZsOption> options) {
//
//    }

    public static void creatTotalRow(HSSFRow row, String oName, Integer oCount, Integer index) {
        creatTableCell(row, 1).setCellValue(index);
        creatTableCell(row, 2).setCellValue(oName);
        creatTableCell(row, 3).setCellValue(oCount);
    }

    public static HSSFCell creatTableCell(HSSFRow row, Integer cIndex) {
        HSSFCell cell = row.createCell(cIndex);
        cell.setCellStyle(createCellStyle(row.getSheet().getWorkbook()));
        return cell;
    }

}
