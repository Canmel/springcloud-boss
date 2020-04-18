package com.camel.survey.exceptions;

public class SurveyNotValidException extends RuntimeException {

    public SurveyNotValidException() {
        super("问卷配额已满");
    }

    public SurveyNotValidException(String msg) {
        super(msg);
    }

}
