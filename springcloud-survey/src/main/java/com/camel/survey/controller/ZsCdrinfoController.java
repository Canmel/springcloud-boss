package com.camel.survey.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.IService;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.annotation.AuthIgnore;
import com.camel.survey.model.ZsAnswer;
import com.camel.survey.model.ZsCdrinfo;
import com.camel.survey.model.ZsSeat;
import com.camel.survey.service.ZsAnswerService;
import com.camel.survey.service.ZsCdrinfoService;
import com.camel.survey.service.ZsSeatService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    public static final String CDR_KEY = "cdr";

    @Autowired
    private ZsCdrinfoService service;

    @Autowired
    private ZsSeatService seatService;

    @Autowired
    private ZsAnswerService answerService;

    /**
     * 获取录音信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result detail(@PathVariable("id") String id){
        return ResultUtil.success(service.details(id));
    }

    /**
     * 新建保存
     * @param params
     */
    @AuthIgnore
    @PostMapping
    public String save(@RequestBody Map<String, ZsCdrinfo[]> params) {
        try{
            if(!ArrayUtils.isEmpty(params.get(CDR_KEY))) {
                List<ZsCdrinfo> zsCdrinfos = CollectionUtils.arrayToList(params.get(CDR_KEY));
                LoggerFactory.getLogger(ZsCdrinfoController.class).info("开始保存录音信息" + JSONObject.toJSON(params).toString());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for (int i = 0; i < zsCdrinfos.size(); i++) {
                    ZsAnswer answer = new ZsAnswer();
                    ZsCdrinfo cdr= zsCdrinfos.get(i);
                    if(!ObjectUtils.isEmpty(cdr.getCaller_agent_num())) {
                        ZsSeat seat = seatService.selectBySeat(cdr.getCaller_agent_num());
                        cdr.setUid(seat.getUid());
                    }
                    if(StringUtils.isEmpty(cdr.getUuids())) {
                        cdr.setId(UUID.randomUUID().toString());
                        service.insert(cdr);
                    }else {
                        String[] uuids = cdr.getUuids().split(",");
                        for (int j = 0; j < uuids.length; j++) {
                            ZsAnswer zsAnswer = answerService.selectByAgentUuid(uuids[j]);
                            if (!ObjectUtils.isEmpty(zsAnswer)) {
                                answer.setId(zsAnswer.getId());
                                cdr.setId(uuids[j]);
                            }
                        }
                        if(ObjectUtils.isEmpty(cdr.getId())) {
                            cdr.setId(UUID.randomUUID().toString());
                        }
                        service.insert(cdr);
                        answer.setStartTime(cdr.getStart_time());
                        answer.setCallLastsTime(cdr.getCall_lasts_time());
                        answer.setEndTime(sdf.format(sdf.parse(answer.getStartTime()).getTime() + Long.valueOf(answer.getCallLastsTime())*1000));
                        if(answer.getId()!=null) {
                            answerService.updateById(answer);
                        }
                    }

                }
                return "success";
            }
        }catch (Exception ex) {
            ex.printStackTrace();
            return "error";
        }
        return "error";
    }

    /**
     * 获取service
     */
    @Override
    public IService getiService() {
        return service;
    }

    /**
     * 获取模块名称
     */
    @Override
    public String getMouduleName() {
        return "话单推送";
    }
}

