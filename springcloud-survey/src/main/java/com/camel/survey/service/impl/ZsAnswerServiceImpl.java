package com.camel.survey.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
import com.camel.core.utils.PaginationUtil;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.enums.ZsYesOrNo;
import com.camel.survey.mapper.*;
import com.camel.survey.model.ZsAnswer;
import com.camel.survey.model.ZsAnswerItem;
import com.camel.survey.model.ZsOption;
import com.camel.survey.model.ZsSurvey;
import com.camel.survey.service.ZsAnswerService;
import com.camel.survey.utils.ApplicationToolsUtils;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 　　　　　　　 ┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━━━┛┻┓
 * 　　　　　　　┃         ┃
 * 　　　　　　　┃    ━    ┃
 * 　　　　　　　┃  >   <  ┃
 * 　　　　　　　┃         ┃
 * 　　　　　　　┃... ⌒ ...┃
 * 　　　　　　　┃         ┃
 * ┗━┓     ┏━┛
 * ┃     ┃　Code is far away from bug with the animal protecting
 * ┃     ┃   神兽保佑,代码无bug
 * ┃     ┃
 * ┃     ┃
 * ┃     ┃        < 服务实现类>
 * ┃     ┃
 * ┃     ┗━━━━┓   @author baily
 * ┃          ┣┓
 * ┃          ┏┛  @since 1.0
 * ┗┓┓┏━━━━┳┓┏┛
 * ┃┫┫    ┃┫┫    @date 2019-12-17
 * ┗┻┛    ┗┻┛
 */
@Service
public class ZsAnswerServiceImpl extends ServiceImpl<ZsAnswerMapper, ZsAnswer> implements ZsAnswerService {

    @Autowired
    private ZsAnswerMapper mapper;

    @Autowired
    private ZsSurveyMapper surveyMapper;

    @Autowired
    private ZsAnswerItemMapper answerItemMapper;

    @Autowired
    private ZsOptionMapper optionMapper;

    @Autowired
    private ZsAnswerMapper zsAnswerMapper;

    @Autowired
    private ZsCdrinfoMapper zsCdrinfoMapper;

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private ApplicationToolsUtils applicationToolsUtils;

    @Override
    public PageInfo<ZsAnswer> selectPage(ZsAnswer entity) {
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.list(entity);
        });
        List list = pageInfo.getList();
        for (Object obj: list) {
            ZsAnswer answer = (ZsAnswer) obj;
            if(!ObjectUtils.isEmpty(answer.getAgentUUID()) && !answer.getAgentUUID().equals("0")) {
                answer.setCdrinfo(zsCdrinfoMapper.selectByAgentUUID(answer.getAgentUUID()));
            }
        }
        return pageInfo;
    }

    @Override
    public List<ZsAnswer> list(ZsAnswer entity) {
        entity.setPageNum(null);
        entity.setPageSize(null);
        return mapper.list(entity);
    }

    @Override
    public ZsAnswer details(Integer id) {
        return mapper.details(id);
    }

    @Override
    public ZsAnswer details(String agent) {
        return mapper.detailsA(agent);
    }

    @Override
    @Transactional
    public Result invalid(Integer id) {
//        mapper.invalidItems()
        /**
         * 修改子表样本数量
         */
        ZsAnswer zsAnswer = this.selectById(id);
        ZsSurvey zsSurvey = surveyMapper.selectById(zsAnswer.getSurveyId());
        if (!ObjectUtils.isEmpty(zsAnswer.getValid()) && zsAnswer.getValid() == ZsYesOrNo.NO) {
            // 获取所有选项，如果包含不计配额的不需要恢复配额
            ZsAnswer answer = selectById(id);
            Boolean ignoreNum = false;
            List<Integer> oIds = new ArrayList<>();
            // 查询本次回答所有的内容选项
            List<ZsAnswerItem> answerItems = answerItemMapper.selectByAnswerId(id);
            if (!ObjectUtils.isEmpty(answer)) {
                List<ZsOption> option = optionMapper.selectBySurveyId(answer.getSurveyId());
                for (int i = 0; i < answerItems.size(); i++) {
                    for (int j = 0; j < option.size(); j++) {
                        if (answerItems.get(i).getOption().equals(option.get(j).getName()) && answerItems.get(i).getQuestion().equals(option.get(j).getZsQuestion().getName())) {
                            if (!ObjectUtils.isEmpty(option.get(i).getIgnoreNum()) && option.get(i).getIgnoreNum()) {
                                ignoreNum = true;
                            }
                            oIds.add(option.get(j).getId());
                        }

                    }
                }
            }
            if (!ignoreNum) {
                addCurrent(answer.getSurveyId(), oIds);
            }
            zsAnswer.setValid(ZsYesOrNo.YES);
            answerItemMapper.chageInvalidByAnswer(id, ZsYesOrNo.YES.getCode());
        } else {
            // 获取所有选项，如果包含不计配额的不需要恢复配额
            ZsAnswer answer = selectById(id);
            Boolean ignoreNum = false;
            List<Integer> oIds = new ArrayList<>();
            // 查询本次回答所有的内容选项
            List<ZsAnswerItem> answerItems = answerItemMapper.selectByAnswerId(id);
            if (!ObjectUtils.isEmpty(answer)) {
                List<ZsOption> option = optionMapper.selectBySurveyId(answer.getSurveyId());
                for (int i = 0; i < answerItems.size(); i++) {
                    for (int j = 0; j < option.size(); j++) {
                        if (answerItems.get(i).getOption().equals(option.get(j).getName()) && answerItems.get(i).getQuestion().equals(option.get(j).getZsQuestion().getName())) {
                            if (!ObjectUtils.isEmpty(option.get(i).getIgnoreNum()) && option.get(i).getIgnoreNum()) {
                                ignoreNum = true;
                            }
                            oIds.add(option.get(j).getId());
                        }

                    }
                }
            }
            if (!ignoreNum) {
                reduceCurrent(answer.getSurveyId(), oIds);
            }
            zsAnswer.setValid(ZsYesOrNo.NO);
            answerItemMapper.chageInvalidByAnswer(id, ZsYesOrNo.NO.getCode());
        }
        /**
         * 修改主表状态
         */
        mapper.updateById(zsAnswer);
        if(zsAnswer.getValid().getCode()==1) {
            return ResultUtil.success("样本状态已更改为有效");
        }
        else {
            return ResultUtil.success("样本状态已更改为无效");
        }
    }

    @Override
    @Transactional
    public Result deleteAnswer(Integer id) {
        // 获取所有选项，如果包含不计配额的不需要恢复配额
        ZsAnswer answer = selectById(id);
        if (!ObjectUtils.isEmpty(answer.getValid()) && answer.getValid() == ZsYesOrNo.YES) {
            Boolean ignoreNum = false;
            List<Integer> oIds = new ArrayList<>();
            // 查询本次回答所有的内容选项
            List<ZsAnswerItem> answerItems = answerItemMapper.selectByAnswerId(id);
            if (!ObjectUtils.isEmpty(answer)) {
                List<ZsOption> option = optionMapper.selectBySurveyId(answer.getSurveyId());
                for (int i = 0; i < answerItems.size(); i++) {
                    for (int j = 0; j < option.size(); j++) {
                        if (answerItems.get(i).getOption().equals(option.get(j).getName()) && answerItems.get(i).getQuestion().equals(option.get(j).getZsQuestion().getName())) {
                            if (!ObjectUtils.isEmpty(option.get(i).getIgnoreNum()) && option.get(i).getIgnoreNum()) {
                                ignoreNum = true;
                            }
                            oIds.add(option.get(j).getId());
                        }

                    }
                }
            }
            if (!ignoreNum) {
                reduceCurrent(answer.getSurveyId(), oIds);
            }
        }
        mapper.deleteById(id);
        Wrapper<ZsAnswerItem> answerItemWrapper = new EntityWrapper<>();
        answerItemWrapper.eq("answer_id", id);
        answerItemMapper.delete(answerItemWrapper);
        return ResultUtil.success("删除成功");
    }


    public void reduceCurrent(Integer surveyId, List<Integer> optIds) {
        JSONObject json = new JSONObject();
        json.put("surveyId", surveyId);
        json.put("optId", optIds);
        this.jmsMessagingTemplate.convertAndSend(new ActiveMQTopic("ActiveMQ.Stock.Reduce.Topic"), json);
    }

    public void addCurrent(Integer surveyId, List<Integer> optIds) {
        JSONObject json = new JSONObject();
        json.put("surveyId", surveyId);
        json.put("optId", optIds);
        this.jmsMessagingTemplate.convertAndSend(new ActiveMQTopic("ActiveMQ.Stock.Add.Topic"), json);
    }

    @Override
    public List<ZsAnswer> selectAllWithConversation(Integer id) {
        return mapper.selectAllWithConversation(id);
    }

    @Override
    public ZsAnswer selectByAgentUuid(String agentUuid) {
        return mapper.selectByAgentUuid(agentUuid);
    }

    @Override
    public boolean review(Integer answerId, Integer reviewStatus, String reviewMsg) {
        SysUser user = applicationToolsUtils.currentUser();
        return this.updateById(new ZsAnswer(answerId, reviewMsg, reviewStatus, user.getUid()));
    }

    @Override
    public List<ZsAnswer> randomList(ZsAnswer zsAnswer) {
        return mapper.randomList(zsAnswer.getSurveyId());
    }

    @Override
    public Set<String> selectAgentUuidsByEntity(ZsAnswer entity) {
        return mapper.selectAgentUuidsByEntity(entity);
    }
}
