package com.camel.survey.exceptions;

public class SourceDataNotValidException extends RuntimeException {
    public SourceDataNotValidException(String msg) {
        super(msg);
    }

    public SourceDataNotValidException() {
        super("元数据设置不正确，请联系管理员。");
    }
}
