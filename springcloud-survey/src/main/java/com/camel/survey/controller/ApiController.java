package com.camel.survey.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.camel.survey.annotation.AuthIgnore;
import com.camel.survey.service.ZsSurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private ZsSurveyService surveyService;
    /**
     *
     * @param id
     * @param taskid
     * @param tel
     * @return
     */
    @AuthIgnore
    @GetMapping("/valid/{id}")
    public JSON valid(@PathVariable Integer id, String taskid, String tel) {
        JSONObject jsonObject = new JSONObject();
        if(surveyService.telValid(id, taskid, tel)) {
            jsonObject.put("result", "1");
        }else{
            jsonObject.put("result", "0");
        }

        return jsonObject;

    }
}
