package com.camel.survey.controller;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.annotation.AuthIgnore;
import com.camel.survey.model.Document;
import com.camel.survey.model.ZsAnswer;
import com.camel.survey.model.ZsCdrinfo;
import com.camel.survey.service.DocumentService;
import com.camel.survey.service.ZsAnswerService;
import com.camel.survey.service.ZsCdrinfoService;
import com.camel.survey.service.ZsSurveyService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private ZsSurveyService surveyService;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private ZsAnswerService answerService;

    @Autowired
    private ZsCdrinfoService cdrinfoService;

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

    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file, Integer answerId) {
        ZsAnswer answer = answerService.selectById(answerId);
        JSONObject upload = documentService.upload(file);
        // 新建一个录音
        ZsCdrinfo cdrinfo = loadNewCdrInfo(upload.getString("key"), answer);
        cdrinfoService.insert(cdrinfo);
        answer.setAgentUUID(cdrinfo.getCall_uuid());
        answer.setStartTime(cdrinfo.getStart_time());
        answer.setCallLastsTime(cdrinfo.getCall_lasts_time());
        answerService.updateById(answer);
        return ResultUtil.success("录音文件上传成功");
    }

    public static ZsCdrinfo loadNewCdrInfo(String path, ZsAnswer zsAnswer) {
        SimpleDateFormat dataParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ZsCdrinfo cdrinfo = new ZsCdrinfo();
        String uuid = RandomUtil.randomUUID();
        cdrinfo.setId(uuid);
        cdrinfo.setCall_uuid(uuid);
        cdrinfo.setUuids(uuid);
        cdrinfo.setCall_type("0");
        cdrinfo.setCaller_num("");
        cdrinfo.setCallee_num(zsAnswer.getCreator());
        cdrinfo.setStart_time(dataParser.format(new Date()));
        cdrinfo.setCall_lasts_time("100");
        cdrinfo.setAgent_duration(100);
        cdrinfo.setRecordFile(path);
        cdrinfo.setUid(zsAnswer.getUid());
        cdrinfo.setSurveyId(zsAnswer.getSurveyId());
        return cdrinfo;
    }
}
