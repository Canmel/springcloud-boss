package com.camel.survey.utils;

import com.baomidou.mybatisplus.toolkit.MapUtils;
import com.camel.survey.annotation.ExcelAnnotation;
import com.camel.survey.exceptions.ExcelImportException;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
            if(ObjectUtils.isEmpty(row)) {
                continue;
            }
            //此处用来过滤空行
            Cell cell0 = row.getCell(0);
            if (ObjectUtils.isEmpty(cell0)) {
                continue;
            }
//            cell0.setCellType(CellType.STRING);
            Cell cell1 = row.getCell(1);
            if(ObjectUtils.isEmpty(cell1)) {
                continue;
            }
//            cell1.setCellType(CellType.STRING);
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
                        setFieldValue(obj, f, wookbook, cell, null);
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
//            wookbook.close();
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static <T extends Object> List<T> readExcelObject(MultipartFile file, Class<T> clazz, Map<String, List> translater) {

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
//            cell0.setCellType(CellType.STRING);
            Cell cell1 = row.getCell(1);
//            cell1.setCellType(CellType.STRING);
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
                        setFieldValue(obj, f, wookbook, cell, translater);
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
//            wookbook.close();
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
    private static void setFieldValue(Object obj, Field f, Workbook wookbook, Cell cell, Map<String, List> translater) {
        List<String> keys = null;
        List<Integer> values = null;
        if (MapUtils.isNotEmpty(translater)) {
            keys = translater.get("keys");
            values = translater.get("values");
        }
        try {
//            cell.setCellType(CellType.STRING);
            if (f.getType() == byte.class || f.getType() == Byte.class) {
                f.set(obj, Byte.parseByte(cell.getStringCellValue()));
            } else if (f.getType() == int.class || f.getType() == Integer.class) {
                String value = cell.getStringCellValue();
                f.set(obj, doTrans(value, keys, values));
            } else if (f.getType() == Double.class || f.getType() == double.class) {
                f.set(obj, Double.parseDouble(cell.getStringCellValue()));
            } else if (f.getType() == BigDecimal.class) {
                f.set(obj, new BigDecimal(cell.getStringCellValue()));
            } else if (f.getType() == Date.class) {
                f.set(obj, HSSFDateUtil.getJavaDate(Double.parseDouble(cell.getStringCellValue())));
            } else if (f.getType() == boolean.class || f.getType() == Boolean.class) {
                String value = cell.getStringCellValue();
                f.set(obj, doTransBoolean(value));
            } else {
                Object value = cell.getStringCellValue();
                f.set(obj, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Integer doTrans(String o, List keys, List<Integer> values) {
        Integer result = null;
        try {
            if (o == null || o.equals("")) {
                return result;
            }
            result = Integer.parseInt(o);
        } catch (NumberFormatException e) {
            if (!CollectionUtils.isEmpty(keys)) {
                int keyIndex = keys.indexOf(o);
                if (keyIndex > -1 && !ObjectUtils.isEmpty(values.get(keyIndex))) {
                    result = values.get(keyIndex);
                }
            }
        }
        return result;
    }

    public static Boolean doTransBoolean(String o) {
        if ("是".equals(o)) {
            return true;
        }
        return false;
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
        String result = stringBuffer.toString();
        if (result.indexOf("?") > -1) {
            result = result.replace("?", "？");
        }
        return result;
    }

    /**
     * 合计表的表头
     *
     * @param title
     * @param row
     */
    public static void setTotalTitle(String title, Row row, Sheet sheet) {
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 14));
        Cell cell = row.createCell(0);
        cell.setCellStyle(createTitleHeadStyle(sheet.getWorkbook()));
        cell.setCellValue(title);
    }

    public static HSSFCellStyle createHeadStyle(HSSFWorkbook wb) {
        HSSFCellStyle style = wb.createCellStyle();
        style.setFillBackgroundColor(IndexedColors.BLUE_GREY.getIndex());
        style.setBorderBottom((short) 1);
        style.setBorderLeft((short) 1);
        style.setBorderRight((short) 1);
        style.setBorderTop((short) 1);
        return style;
    }

    public static CellStyle createTitleStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setFillBackgroundColor(IndexedColors.BLUE_GREY.getIndex());
        style.setBorderBottom((short) 1);
        style.setBorderLeft((short) 1);
        style.setBorderRight((short) 1);
        style.setBorderTop((short) 1);
        style.setFillForegroundColor(HSSFColor.LIME.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        return style;
    }

    public static CellStyle createCellStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setBorderBottom((short) 1);
        style.setBorderLeft((short) 1);
        style.setBorderRight((short) 1);
        style.setBorderTop((short) 1);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        return style;
    }

    public static CellStyle createTotalHeadStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setBorderBottom((short) 1);
        style.setBorderLeft((short) 1);
        style.setBorderRight((short) 1);
        style.setBorderTop((short) 1);
        style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
//        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        style.setAlignment(HorizontalAlignment.CENTER);
//        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    public static CellStyle createTitleHeadStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setBorderBottom((short) 1);
        style.setBorderLeft((short) 1);
        style.setBorderRight((short) 1);
        style.setBorderTop((short) 1);
        style.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
//        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        style.setAlignment(HorizontalAlignment.CENTER);
//        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }


    public static void creatTotalHead(Sheet sheet, Integer startRow) {
        String[] titles = {"序号", "选项", "样本情况"};
        int columIndex = 1;
        Row row = sheet.createRow(startRow);
        for (String title : titles) {
            Cell cell = row.createCell(columIndex++);
            cell.setCellStyle(createTotalHeadStyle(sheet.getWorkbook()));
            cell.setCellValue(title);
        }
    }

    public static void creatTotalRow(Row row, String oName, Integer oCount, Integer index) {
        creatTableCell(row, 1).setCellValue(index);
        creatTableCell(row, 2).setCellValue(oName);
        creatTableCell(row, 3).setCellValue(oCount);
    }

    public static Cell creatTableCell(Row row, Integer cIndex) {
        Cell cell = row.createCell(cIndex);
        cell.setCellStyle(createCellStyle(row.getSheet().getWorkbook()));
        return cell;
    }

    /**
     * 判断文件格式
     *
     * @param in
     * @param fileName
     * @return
     */
    public static Workbook getWorkbook(InputStream in, String fileName) throws Exception {

        Workbook book = null;
        String filetype = fileName.substring(fileName.lastIndexOf("."));

        if (".xls".equals(filetype)) {
            book = new HSSFWorkbook(in);
        } else if (".xlsx".equals(filetype)) {
            book = new XSSFWorkbook(in);
        } else {
            throw new Exception("请上传excel文件！");
        }

        return book;
    }

}
