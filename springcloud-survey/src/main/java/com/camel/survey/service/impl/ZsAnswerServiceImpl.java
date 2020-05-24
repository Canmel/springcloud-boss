package com.camel.survey.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.core.entity.Result;
import com.camel.core.utils.PaginationUtil;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.enums.ZsYesOrNo;
import com.camel.survey.mapper.*;
import com.camel.survey.model.ZsAnswer;
import com.camel.survey.model.ZsAnswerItem;
import com.camel.survey.model.ZsOption;
import com.camel.survey.model.ZsSurvey;
import com.camel.survey.service.ZsAnswerService;
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
    public ZsAnswer details(Integer id) {
        return mapper.details(id);
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
            if(zsSurvey.getCurrentNum()>=zsSurvey.getCollectNum())
                return ResultUtil.updateError("样本状态","该问卷收集数目已满，不可恢复！");
            List<ZsAnswerItem> answerItems=answerItemMapper.selectByAnswerId(id);
            for(int i=0;i<answerItems.size();i++){
                ZsOption option = optionMapper.selectByQuestionAndName(answerItems.get(i).getQuestionId(),answerItems.get(i).getOption());
                if(option!=null&&option.getConfigration()!=null){
                    if(option.getCurrent()>=option.getConfigration()){
                        return ResultUtil.updateError("样本状态","该样本内存在配额已满选项，不可恢复！");
                    }
                    option.setCurrent(option.getCurrent()+1);
                    optionMapper.updateById(option);
                }
            }
            zsAnswer.setValid(ZsYesOrNo.YES);
            answerItemMapper.chageInvalidByAnswer(id, ZsYesOrNo.YES.getCode());
            /**
             * 修改当前样本数量
             */
            zsSurvey.setCurrentNum(zsSurvey.getCurrentNum() + 1);
        } else {
            List<ZsAnswerItem> answerItems=answerItemMapper.selectByAnswerId(id);
            for(int i=0;i<answerItems.size();i++){
                ZsOption option = optionMapper.selectByQuestionAndName(answerItems.get(i).getQuestionId(),answerItems.get(i).getOption());
                if(option!=null&&option.getConfigration()!=null){
                    option.setCurrent(option.getCurrent()-1);
                    optionMapper.updateById(option);
                }
            }
            zsAnswer.setValid(ZsYesOrNo.NO);
            answerItemMapper.chageInvalidByAnswer(id, ZsYesOrNo.NO.getCode());
            /**
             * 修改当前样本数量
             */
            zsSurvey.setCurrentNum(zsSurvey.getCurrentNum() - 1);

        }
        surveyMapper.updateById(zsSurvey);
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
        ZsAnswer answer = selectById(id);
        ZsSurvey zsSurvey = surveyMapper.selectById(answer.getSurveyId());
        if (!ObjectUtils.isEmpty(answer.getValid()) && answer.getValid() == ZsYesOrNo.YES){
            List<ZsAnswerItem> answerItems=answerItemMapper.selectByAnswerId(id);
            for(int i=0;i<answerItems.size();i++){
                ZsOption option = optionMapper.selectByQuestionAndName(answerItems.get(i).getQuestionId(),answerItems.get(i).getOption());
                if(option!=null&&option.getConfigration()!=null){
                    option.setCurrent(option.getCurrent()-1);
                    optionMapper.updateById(option);
                }
            }
            zsSurvey.setCurrentNum(zsSurvey.getCurrentNum() - 1);
        }
        surveyMapper.updateById(zsSurvey);
        mapper.deleteById(id);
        Wrapper<ZsAnswerItem> answerItemWrapper = new EntityWrapper<>();
        answerItemWrapper.eq("answer_id", id);
        answerItemMapper.delete(answerItemWrapper);
        return ResultUtil.success("删除成功");
    }

    public void updateCurrent(Integer surveyId, List<Integer> optIds) {
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
}
