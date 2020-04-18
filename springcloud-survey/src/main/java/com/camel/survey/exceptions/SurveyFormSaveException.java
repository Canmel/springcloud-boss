package com.camel.survey.exceptions;

public class SurveyFormSaveException extends RuntimeException {

    public SurveyFormSaveException() {
        super("问卷调查保存出错");
    }

    public SurveyFormSaveException(String msg) {
        super(msg);
    }

}
