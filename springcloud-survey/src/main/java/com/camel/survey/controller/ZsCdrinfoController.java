package com.camel.survey.controller;


import com.baomidou.mybatisplus.service.IService;
import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.annotation.AuthIgnore;
import com.camel.survey.model.ZsAnswer;
import com.camel.survey.model.ZsCdrinfo;
import com.camel.survey.service.ZsAnswerService;
import com.camel.survey.service.ZsCdrinfoService;
import org.attoparser.trace.MarkupTraceEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import com.camel.core.controller.BaseCommonController;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baily
 * @since 2020-04-18
 */
@RestController
@RequestMapping("/zsCdrinfo")
public class ZsCdrinfoController extends BaseCommonController {

    @Autowired
    private ZsCdrinfoService service;

    @Autowired
    private ZsAnswerService answerService;

    @AuthIgnore
    @PostMapping
    public String save(@RequestBody Map<String, ZsCdrinfo[]> params) {
        try{
            if(service.insertBatch(CollectionUtils.arrayToList(params.get("cdr")))) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                ZsCdrinfo cdr= (ZsCdrinfo) CollectionUtils.arrayToList(params.get("cdr")).get(0);
                ZsAnswer answer = new ZsAnswer();
                String[] uuids=null;
                if(StringUtils.isEmpty(cdr.getUuids())) {
                    return "success";
                }
                uuids = cdr.getUuids().split(",");
                for(int i=0;i<uuids.length;i++){
                    if(answerService.selectByAgentUuid(uuids[i])!=null){
                        answer.setId(answerService.selectByAgentUuid(uuids[i]).getId());
                    }
                }
                answer.setStartTime(cdr.getStart_time());
                answer.setCallLastsTime(cdr.getCall_lasts_time());
                answer.setEndTime(sdf.format(sdf.parse(answer.getStartTime()).getTime() + Long.valueOf(answer.getCallLastsTime())*1000));
                if(answer.getId()!=null) answerService.updateById(answer);
                return "success";
            }
        }catch (Exception ex) {
            ex.printStackTrace();
            return "error";
        }
        return "error";
    }

    @Override
    public IService getiService() {
        return service;
    }

    @Override
    public String getMouduleName() {
        return "话单推送";
    }
}

