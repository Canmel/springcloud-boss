package com.camel.survey.exceptions;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.camel.survey.utils.ExcelCulumnUtil;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ExcelImportException extends RuntimeException {
    public ExcelImportException() {
        super("Excel导入时发生错误，请联系管理员");
    }

    public ExcelImportException(String message) {
        super(message);
    }

    public static ExcelImportException build(String msg, Row row, Cell cell) {
        StringBuffer stringBuffer = new StringBuffer("读取到");
        if(ObjectUtil.isNotEmpty(row)) {
            Integer rowNum = row.getRowNum() + 1;
            stringBuffer.append("第" + rowNum + "行，");
        }
        if(ObjectUtil.isNotEmpty(cell)) {
            Integer cellIndex = cell.getColumnIndex() + 1;
            stringBuffer.append("第" + ExcelCulumnUtil.excelColIndexToStr(cellIndex) + "列，");
        }
        if(StringUtils.contains(msg, "empty String")) {
            stringBuffer.append("是空字符串");
            return new ExcelImportException(msg);
        } if(StringUtils.contains(msg, "For input string")) {
            stringBuffer.append("格式不正确，请检查");
            return new ExcelImportException(stringBuffer.toString());
        } if(StringUtils.contains(msg, "ExcelImportException")) {
            stringBuffer.append("读取文件的文件内容转换为数字时出错");
            return new ExcelImportException(stringBuffer.toString());
        } else {
            return new ExcelImportException(msg);
        }
    }
}
