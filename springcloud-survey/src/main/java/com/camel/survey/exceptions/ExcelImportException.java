package com.camel.survey.exceptions;

public class ExcelImportException extends RuntimeException {
    public ExcelImportException() {
        super("Excel导入时发生错误，请联系管理员");
    }

    public ExcelImportException(String message) {
        super(message);
    }
}
