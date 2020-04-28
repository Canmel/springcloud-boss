package com.camel.survey.service;

import javax.servlet.http.HttpServletRequest;

public interface MyFileTransterBackUpdate {
    void update(HttpServletRequest result);

    void update(String result);
}
