package com.camel.survey.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.camel.common.entity.Member;
import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.model.SysUser;
import com.camel.core.utils.PaginationUtil;
import com.camel.core.utils.ResultUtil;
import com.camel.redis.utils.SessionContextUtils;
import com.camel.survey.exceptions.SurveyNotValidException;
import com.camel.survey.mapper.ZsQuestionMapper;
import com.camel.survey.model.*;
import com.camel.survey.service.*;
import com.camel.survey.utils.ApplicationToolsUtils;
import com.camel.survey.vo.ZsAnswerSave;
import com.camel.survey.vo.ZsQuestionSave;
import com.github.pagehelper.PageInfo;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 　　　　　　　 ┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━━━┛┻┓
 * 　　　　　　　┃         ┃ 　
 * 　　　　　　　┃    ━    ┃
 * 　　　　　　　┃  >   <  ┃
 * 　　　　　　　┃         ┃
 * 　　　　　　　┃... ⌒ ...┃
 * 　　　　　　　┃         ┃
 *             ┗━┓     ┏━┛
 *               ┃     ┃　Code is far away from bug with the animal protecting　　　　　　　　　　
 *               ┃     ┃   神兽保佑,代码无bug
 *               ┃     ┃　　　　　　　　　　　
 *               ┃     ┃  　　　　　　
 *               ┃     ┃        < 服务实现类>
 *               ┃     ┃　　　　　　　　　　　
 *               ┃     ┗━━━━┓   @author baily
 *               ┃          ┣┓
 *               ┃          ┏┛  @since 1.0
 *               ┗┓┓┏━━━━┳┓┏┛
 *                ┃┫┫    ┃┫┫    @date 2019-12-09
 *                ┗┻┛    ┗┻┛
 */
@Service
public class ZsQuestionServiceImpl extends ServiceImpl<ZsQuestionMapper, ZsQuestion> implements ZsQuestionService {

    @Autowired
    private ZsQuestionMapper mapper;

    @Autowired
    private ZsOptionService zsOptionService;

    @Autowired
    private ZsAnswerService answerService;

    @Autowired
    private ZsSurveyService surveyService;

    @Autowired
    private ZsAnswerItemService answerItemService;

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ApplicationToolsUtils applicationToolsUtils;

    @Override
    public PageInfo<ZsQuestion> selectPage(ZsQuestion entity) {
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.list(entity);
        });
        return pageInfo;
    }

    @Override
    public Result save(ZsQuestionSave entity, OAuth2Authentication oAuth2Authentication) {
        // 根据问卷ID删除问题以及选项
        Wrapper zsQuestionWrapper = new EntityWrapper<>();
        zsQuestionWrapper.eq("survey_id", entity.getSurveyId());
        List<ZsQuestion> zsQuestions = selectList(zsQuestionWrapper);
        if (zsQuestions.size() > 0) {
            Wrapper<ZsOption> optionWrapper = new EntityWrapper<>();
            List<Integer> qIds = zsQuestions.stream().map(ZsQuestion::getId).collect(Collectors.toList());
            optionWrapper.in("question_id", qIds);
            deleteBatchIds(qIds);
            zsOptionService.delete(optionWrapper);
        }
        SysUser user = applicationToolsUtils.currentUser();
        // 保存问题
        entity.getZsQuestions().forEach(q -> {
            q.setCreatorId(user.getUid());
            mapper.insert(q);
            List<ZsOption> optionList = entity.optionsfilterAndBuildInsertParams(q);
            // 保存选项
            if (CollectionUtils.isNotEmpty(optionList)) {
                zsOptionService.insertBatch(optionList);
            }
        });
        return ResultUtil.success("保存成功");

    }

    @Override
    public Result update(ZsQuestionSave entity, OAuth2Authentication oAuth2Authentication) {
        List<Integer> qIds = entity.getZsQuestions().stream().map(ZsQuestion::getId).collect(Collectors.toList());
        List<Integer> oIds = entity.getZsOptions().stream().map(ZsOption::getId).collect(Collectors.toList());
        deleteBatchIds(qIds);
        deleteBatchIds(oIds);
        save(entity, oAuth2Authentication);
        return ResultUtil.success("修改成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result saveAnswer(ZsAnswerSave zsAnswerSave) {
        // 验证配额以及样本数量是否已经满额
        surveyService.valid(zsAnswerSave);
        ZsSurvey zsSurvey = surveyService.selectById(zsAnswerSave.getSurveyId());
        if (isAnswered(zsAnswerSave)) {
            return ResultUtil.success(StringUtils.isEmpty(zsSurvey.getEndShow()) ? "本次访问结束，感谢您的理解和支持，再见" : zsSurvey.getEndShow());
        }
        if (zsSurvey.isFull()) {
            throw new SurveyNotValidException("我们的（" + zsSurvey.getName() + "）样本个数已满，不好意思打扰您了，祝您生活愉快，再见！");
        }
        ZsAnswer zsAnswer = zsAnswerSave.buildAnswer();
        SysUser user = applicationToolsUtils.currentUser();
        zsAnswer.setUid(user.getUid());
        answerService.insert(zsAnswer);
        // 获取所有问题
        List<ZsQuestion> zsQuestions = surveyService.questions(zsAnswerSave.getSurveyId());
        List<Integer> qIds = zsQuestions.stream().map(ZsQuestion::getId).collect(Collectors.toList());
        // 获取所有问题的所有选项
        List<ZsOption> zsOptions = surveyService.options(qIds);
        // 获取所有被选中的选项
        List<Integer> oIds = zsAnswerSave.getOptIds();

        List<ZsAnswerItem> zsAnswerItemList = zsAnswerSave.buildAnswerItems(zsQuestions, zsOptions, zsAnswer.getId());
        for(int i=0;i<zsAnswerItemList.size();i++){
            zsAnswerItemList.get(i).setUid(user.getUid());
        }

        if (!zsOptionService.contanisIgnore(oIds)) {
            updateCurrent(zsSurvey.getId(), oIds);
        }
        if (answerItemService.insertBatch(zsAnswerItemList)) {
            return ResultUtil.success(StringUtils.isEmpty(zsSurvey.getEndShow()) ? "本次访问结束，感谢您的理解和支持，再见" : zsSurvey.getEndShow());
        } else {
            return ResultUtil.error(ResultEnum.SERVICE_ERROR);
        }
    }

    /**
     * 根据所选的选项判断是否需要忽略配额的增加
     * @param zsOptions
     * @return
     */
    public Boolean ignoreNum(List<ZsOption> zsOptions) {
        Boolean flag = false;
        for (ZsOption zsOption : zsOptions) {
            if (zsOption.getIgnoreNum()) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 更新选项配额
     * @param optionIds
     */
    public void updateCurrent(List<Integer> optionIds) {
        zsOptionService.addOptionCurrent(optionIds);
    }

    /**
     * 使用MQ配合配额库存服务增加配额
     * @param surveyId
     * @param optIds
     */
    public void updateCurrent(Integer surveyId, List<Integer> optIds) {
        JSONObject json = new JSONObject();
        json.put("surveyId", surveyId);
        json.put("optId", optIds);
        this.jmsMessagingTemplate.convertAndSend(new ActiveMQTopic("ActiveMQ.Stock.Add.Topic"), json);
    }

    @Override
    public List<ZsQuestion> selectBySurveyId(Integer id) {
        return mapper.selectBySurveyId(id);
    }

    boolean isAnswered(ZsAnswerSave zsAnswerSave) {
        Wrapper<ZsAnswer> zsAnswerWrapper = new EntityWrapper<>();
        zsAnswerWrapper.eq("creator", zsAnswerSave.getPhone());
        zsAnswerWrapper.eq("survey_id", zsAnswerSave.getSurveyId());
        return answerService.selectCount(zsAnswerWrapper) > 0;
    }
}
