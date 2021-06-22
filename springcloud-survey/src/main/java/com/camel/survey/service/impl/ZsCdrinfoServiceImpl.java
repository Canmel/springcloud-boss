package com.camel.survey.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.survey.mapper.ZsCdrinfoMapper;
import com.camel.survey.model.ZsAnswer;
import com.camel.survey.model.ZsCallPlan;
import com.camel.survey.model.ZsCdrinfo;
import com.camel.survey.service.ZsAnswerService;
import com.camel.survey.service.ZsCallPlanService;
import com.camel.survey.service.ZsCdrinfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baily
 * @since 2020-04-18
 */
@Service
public class ZsCdrinfoServiceImpl extends ServiceImpl<ZsCdrinfoMapper, ZsCdrinfo> implements ZsCdrinfoService {
    @Autowired
    private ZsCdrinfoMapper mapper;

    @Autowired
    private ZsCallPlanService callPlanService;

    @Autowired
    private ZsAnswerService answerService;

    @Override
    public ZsCdrinfo details(String id) {
        return mapper.details(id);
    }

    @Override
    public List<ZsCdrinfo> selectList(Set<String> agents) {

        return mapper.selectListByAgents(agents);
    }

    @Override
    public Integer selectAvgTime(Integer id) {
        return mapper.selectAvgTime(id);
    }

    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public boolean validAndUpdateByUUID(ZsCdrinfo cdr) {
        if (org.springframework.util.StringUtils.isEmpty(cdr.getUuids())) {
            System.out.println("cdr 没有UUIDS");
        } else {
            String[] uuids = cdr.getUuids().split(",");
            List<ZsAnswer> zsAnswers = new ArrayList<>();
            for (int j = 0; j < uuids.length; j++) {
                List<ZsAnswer> tmpAnswers = answerService.selectByAgentUuid(uuids[j]);
                if(!CollectionUtils.isEmpty(tmpAnswers)) {
                    zsAnswers = tmpAnswers;
                    cdr.setId(uuids[j]);
                }
            }
            if(!CollectionUtils.isEmpty(zsAnswers)) {
                for (ZsAnswer zsAnswer: zsAnswers) {
                    updateAnswer(zsAnswer.getId(), cdr);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean validAndUpdateAnserByTaskAndPhone(ZsCdrinfo zsCdrinfo) {
        String taskname = zsCdrinfo.getTaskname();
        if(StringUtils.isBlank(taskname)) {
            System.out.println("taskname 为空");
            return false;
        }
        ZsCallPlan callPlan = callPlanService.selectByTaskName(zsCdrinfo.getTaskname());
        if(ObjectUtil.isNull(callPlan)) {
            System.out.println("未找到外呼计划");
            return false;
        }
        ZsAnswer answer = answerService.selectBySurveyIdAndPhone(zsCdrinfo.getSurveyId(), zsCdrinfo.getCallee_num());
        if(ObjectUtil.isNull(answer)) {
            System.out.println("未找到相关样本");
            return false;
        }
        return updateAnswer(answer.getId(), zsCdrinfo);
    }

    @Override
    public boolean updateAnswer(Integer answerId, ZsCdrinfo cdrinfo) {
        if(ObjectUtil.isNull(answerId)) {
            return false;
        }
        ZsAnswer answer = new ZsAnswer();
        answer.setId(answerId);
        answer.setAgentUUID(cdrinfo.getId());
        answer.setStartTime(cdrinfo.getStart_time());
        answer.setCallLastsTime(cdrinfo.getCall_lasts_time());
        answer.setRecord(cdrinfo.getRecordFile());
        try {
            answer.setEndTime(sdf.format(sdf.parse(answer.getStartTime()).getTime() + Long.valueOf(answer.getCallLastsTime()) * 1000));
            answerService.updateById(answer);
        }catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean isSaved(ZsCdrinfo cdr) {
        Wrapper<ZsCdrinfo> cdrinfoWrapper = new EntityWrapper<>();
        cdrinfoWrapper.eq("call_uuid", cdr.getCall_uuid());
        return selectCount(cdrinfoWrapper) > 0;
    }
}
