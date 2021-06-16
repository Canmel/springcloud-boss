package com.camel.survey.controller;


import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.IService;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.annotation.AuthIgnore;
import com.camel.survey.model.ZsCdrinfo;
import com.camel.survey.model.ZsSeat;
import com.camel.survey.service.ZsAnswerService;
import com.camel.survey.service.ZsCdrinfoService;
import com.camel.survey.service.ZsSeatService;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 前端控制器
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
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result detail(@PathVariable("id") String id) {
        return ResultUtil.success(service.details(id));
    }

    /**
     * 新建保存
     *
     * @param params
     */
    @AuthIgnore
    @PostMapping
    public String save(@RequestBody Map<String, ZsCdrinfo[]> params) {
        System.out.println("------------------------------");
        System.out.println(JSONObject.toJSONString(params));
        try {
            if (!ArrayUtils.isEmpty(params.get(CDR_KEY))) {
                List<ZsCdrinfo> zsCdrinfos = CollectionUtils.arrayToList(params.get(CDR_KEY));
                System.out.println("开始保存录音信息");
                String result = "success";
                for (int i = 0; i < zsCdrinfos.size(); i++) {
                    ZsCdrinfo cdr = zsCdrinfos.get(i);
                    // 如果被保存过就放弃
                    if(service.isSaved(cdr)) {
                       return "success";
                    }
                    // 如果推送的消息中有座席号 查出坐席，更新当前坐席绑定的人员
                    if (!ObjectUtils.isEmpty(cdr.getCaller_agent_num())) {
                        ZsSeat seat = seatService.selectBySeat(cdr.getCaller_agent_num());
                        if(ObjectUtil.isNotEmpty(seat)) {
                            cdr.setUid(seat.getUid());
                            cdr.setSurveyId(seat.getSurveyId());
                        }
                    }
                    cdr.setId(UUID.randomUUID().toString());
                    // 两种方式都未通过直接下一个
                    if(!service.validAndUpdateByUUID(cdr)) {
                        if(!service.validAndUpdateAnserByTaskAndPhone(cdr)) {
                            continue;
                        }
                    }
                    try {
                        if(service.insert(cdr)) {
                            return "success";
                        }
                        return "error";
                    }catch (Exception e) {
                        result = "error";
                        continue;
                    }

                }
                return result;
            }
        } catch (Exception ex) {
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

