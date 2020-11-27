package com.camel.survey.utils.export;

import com.camel.survey.enums.ZsYesOrNo;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

public class HSSFUtils {

    public static HSSFCellStyle createHeadStyle(HSSFWorkbook wb) {
        HSSFCellStyle style = wb.createCellStyle();
        style.setFillBackgroundColor(IndexedColors.BLUE_GREY.getIndex());
        style.setBorderBottom((short)1);
        style.setBorderLeft((short)1);
        style.setBorderRight((short)1);
        style.setBorderTop((short)1);
        return style;
    }

    public static HSSFCellStyle createTitleStyle(HSSFWorkbook wb) {
        HSSFCellStyle style = wb.createCellStyle();
        style.setFillBackgroundColor(IndexedColors.BLUE_GREY.getIndex());
        style.setBorderBottom((short)1);
        style.setBorderLeft((short)1);
        style.setBorderRight((short)1);
        style.setBorderTop((short)1);
        return style;
    }

    public static HSSFCellStyle createCellStyle(HSSFWorkbook wb) {
        HSSFCellStyle style = wb.createCellStyle();
        style.setBorderBottom((short) 1);
        style.setBorderLeft((short) 1);
        style.setBorderRight((short) 1);
        style.setBorderTop((short) 1);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        return style;
    }

    public static HSSFCellStyle createStyle(HSSFWorkbook wb) {
        HSSFCellStyle style = wb.createCellStyle();
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        return style;
    }

    public static HSSFCell fillCell(HSSFCell cell, HSSFCellStyle style, String value) {
        cell.setCellValue(value);
        cell.setCellStyle(style);
        return cell;
    }

    public static HSSFCell fillCell(HSSFCell cell, HSSFCellStyle style, Integer value) {
        value = ObjectUtils.isEmpty(value) ? 0 : value;
        cell.setCellValue(value);
        cell.setCellStyle(style);
        return cell;
    }

    public static String renderInvalidMsg(Map<String, Object> map) {
        if(map.get("valid").equals(ZsYesOrNo.YES.getCode())) {
            // 有效
            return "";
        } else {
            if(StringUtils.isEmpty(map.get("in_valid_msg"))) {
                return "逻辑无效";
            }else{
                return (String) map.get("in_valid_msg");
            }
        }
    }

    public static void fillRow(HSSFRow row, HSSFCellStyle style, List<Object> values) {
        Integer index = 0;
        for (Object value : values) {
            if (value instanceof Integer) {
                fillCell(row.createCell(index++), style, (Integer) value);
            } else if (value instanceof String) {
                fillCell(row.createCell(index++), style, (String) value);
            } else {
                fillCell(row.createCell(index++), style, value.toString());
            }
        }
    }

    public static void fillRow(HSSFRow row, HSSFCellStyle style, List<Object> values, Integer step) {
        Integer index = 0;
        for (Object value : values) {
            if (value instanceof Integer) {
                fillCell(row.createCell(step * index), style, (Integer) value);
            }
            if (value instanceof String) {
                fillCell(row.createCell(step * index), style, (String) value);
            }
            row.createCell(step * index + 1).setCellStyle(style);
            index++;
        }
    }

    public static void fillRow(HSSFRow row, HSSFCellStyle style, List<Object> values, Integer step, Boolean merge) {
        Integer index = 0;
        for (Object value : values) {
            if (merge) {
                row.getSheet().addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), step * index, step * index + step - 1));
            }
            if (value instanceof Integer) {
                fillCell(row.createCell(step * index), style, (Integer) value);
            }
            if (value instanceof String) {
                fillCell(row.createCell(step * index), style, (String) value);
            }
            row.createCell(step * index + 1).setCellStyle(style);
            index++;
        }
    }
}
