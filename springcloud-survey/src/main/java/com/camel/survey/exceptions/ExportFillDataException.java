package com.camel.survey.exceptions;

public class ExportFillDataException extends RuntimeException {
    public ExportFillDataException(String message) {
        super(message);
    }

    public ExportFillDataException() {
        super("导出填写数据时发生异常");
    }
}
