package com.camel.survey.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.common.entity.Member;
import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.model.SysUser;
import com.camel.core.utils.PaginationUtil;
import com.camel.core.utils.ResultUtil;
import com.camel.redis.utils.SessionContextUtils;
import com.camel.survey.enums.*;
import com.camel.survey.exceptions.SourceDataNotValidException;
import com.camel.survey.exceptions.SurveyFormSaveException;
import com.camel.survey.exceptions.SurveyNotValidException;
import com.camel.survey.mapper.ZsExamMapper;
import com.camel.survey.mapper.ZsSurveyMapper;
import com.camel.survey.model.*;
import com.camel.survey.service.*;
import com.camel.survey.utils.ApplicationToolsUtils;
import com.camel.survey.vo.ZsAnswerSave;
import com.camel.survey.vo.ZsSendSms;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
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
 *                ┃┫┫    ┃┫┫    @date 2019-12-06
 *                ┗┻┛    ┗┻┛
 */
@Service
public class ZsSurveyServiceImpl extends ServiceImpl<ZsSurveyMapper, ZsSurvey> implements ZsSurveyService {

    @Autowired
    private ZsSurveyMapper mapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ZsQuestionService questionService;

    @Autowired
    private ZsOptionService optionService;

    @Autowired
    private ZsExamMapper zsExamMapper;

    @Autowired
    private ZsSignService zsSignService;

    @Autowired
    private ApplicationToolsUtils applicationToolsUtils;

    @Autowired
    private RelSurveyExamService relSurveyExamService;

    @Autowired
    private ZsConcatService zsConcatService;

    @Autowired
    private ZsSmsService zsSmsService;

    @Autowired
    private ZsSurveyRecordService surveyRecordService;

    @Autowired
    private ZsAnswerItemService answerItemService;

    public static final String SMS_CONTEXT_MODEL = "您好，欢迎参加关于?title?的调查，参加问卷收集得话费，点击?url?";

    public static final String SMS_SURVEY_URL = "http://127.0.0.1:8080/survey/web_survey.html";

    @Override
    public PageInfo<ZsSurvey> selectPage(ZsSurvey entity, OAuth2Authentication oAuth2Authentication) {
        Member member = applicationToolsUtils.currentUser(oAuth2Authentication);
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.list(entity);
        });
        List<ZsSurvey> list = pageInfo.getList();
        list.forEach(e -> {
            applicationToolsUtils.allUsers().forEach(sysUser -> {
                if (sysUser.getUid().equals(e.getCreatorId())) {
                    e.setCreator(sysUser);
                }
            });
            Wrapper<ZsSign> zsSignWrapper = new EntityWrapper<>();
            zsSignWrapper.eq("status", ZsStatus.CREATED.getValue());
            zsSignWrapper.eq("survey_id", e.getId());
            zsSignWrapper.eq("result", ZsSurveySignResult.SUCCESS.getValue());
            if (zsSignService.selectCount(zsSignWrapper) > 0) {
                e.setIsApplySuccess(ZsYesOrNo.YES);
            } else {
                e.setIsApplySuccess(ZsYesOrNo.NO);
            }
        });
        return pageInfo;
    }

    @Override
    public ZsSurvey selectById(Serializable id) {
        ZsSurvey entity = mapper.selectById(id);
        applicationToolsUtils.allUsers().forEach(sysUser -> {
            if (sysUser.getUid().equals(entity.getCreatorId())) {
                entity.setCreator(sysUser);
            }
        });
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result save(ZsSurvey entity, OAuth2Authentication oAuth2Authentication) {
        Member member = (Member) SessionContextUtils.getInstance().currentUser(redisTemplate, oAuth2Authentication.getName());
        entity.setCreatorId(member.getId());
        if (insert(entity)) {
            List<RelSurveyExam> relSurveyExamList = new ArrayList<>();
            if (ObjectUtils.isEmpty(entity.getExams())) {
                throw new SurveyFormSaveException();
            }
            entity.getExams().forEach(examId -> {
                relSurveyExamList.add(new RelSurveyExam(examId, entity.getId()));
            });
            relSurveyExamService.insertBatch(relSurveyExamList);
            return ResultUtil.success("新增成功");
        }
        return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "新增失败");
    }

    @Override
    public Result selectListByProjectId(Integer id) {
        List<ZsSurvey> surveys = mapper.list(new ZsSurvey(id, ZsStatus.CREATED));
        List<SysUser> users = applicationToolsUtils.allUsers();
        surveys.forEach(survey -> {
            users.forEach(sysUser -> {
                if (sysUser.getUid().equals(survey.getCreatorId())) {
                    survey.setCreator(sysUser);
                }
            });
        });
        return ResultUtil.success(surveys);
    }

    @Override
    public Result getQuestionAndOptions(Integer id) {
        return ResultUtil.success(questionService.selectBySurveyId(id));
    }

    @Override
    public Result start(Integer id) {
        ZsSurvey survey = mapper.selectById(id);
        if (!ObjectUtils.isEmpty(survey)) {
            if (!ObjectUtils.isEmpty(survey.getCollectType()) && survey.getCollectType().getValue().equals(ZsSurveyCollectType.SMS.getValue())) {
                String content = SMS_CONTEXT_MODEL;
                sendMessage(survey, content);
            }
            survey.setState(ZsSurveyState.COLLECTING);
            if (updateById(survey)) {
                return ResultUtil.success("问卷已经开始调查");
            } else {
                return ResultUtil.error(ResultEnum.RESOURCESNOTFOUND);
            }
        }
        return ResultUtil.error(ResultEnum.RESOURCESNOTFOUND);
    }

    @Override
    public Result update(ZsSurvey zsSurvey) {
        if (updateById(zsSurvey)) {
            Wrapper<RelSurveyExam> surveyExamWrapper = new EntityWrapper<>();
            surveyExamWrapper.eq("survey_id", zsSurvey.getId());
            relSurveyExamService.delete(surveyExamWrapper);
            List<RelSurveyExam> relSurveyExams = new ArrayList<>();
            zsSurvey.getExams().forEach(examId -> {
                relSurveyExams.add(new RelSurveyExam(examId, zsSurvey.getId()));
            });
            if (!CollectionUtils.isEmpty(relSurveyExams)) {
                relSurveyExamService.insertBatch(relSurveyExams);
            }
            return ResultUtil.success("修改成功");
        }

        return ResultUtil.success("修改失败");
    }

    @Override
    public Result sign(Integer id, OAuth2Authentication oAuth2Authentication) {
        Member member = applicationToolsUtils.currentUser(oAuth2Authentication);
        if (isSigned(id, member.getId())) {
            return ResultUtil.success("您已经投递过了，无需重复提交");
        }
        if (isSuccess(id, member.getId())) {
            return ResultUtil.success("您已经投递过了，并且已经审核通过，无需重复提交");
        }
        List<ZsExam> zsExams = zsExamMapper.listBySurveyId(id);
        List<ZsExam> userExams = zsExamMapper.listByUserId(member.getId());

        if (CollectionUtils.isEmpty(zsExams)) {
            throw new SourceDataNotValidException("您选择了一条没有限制的问卷，这是一条不正确的数据，请联系管理员");
        }
        if (CollectionUtils.isEmpty(userExams)) {
            return ResultUtil.success("投递失败，您没有获取相关等级权限！");
        }
        if (userExams.containsAll(zsExams)) {
            if (zsSignService.insert(new ZsSign(id, member.getMemberName(), member.getId()))) {
                return ResultUtil.success("投递成功");
            }
        } else {
            return ResultUtil.success("投递失败，您没有获取相关等级权限！");
        }
        return ResultUtil.error(ResultEnum.SERVICE_ERROR);
    }

    public Boolean isSigned(Integer surveyId, Integer userId) {
        Wrapper<ZsSign> signWrapper = new EntityWrapper<>();
        signWrapper.eq("survey_id", surveyId);
        signWrapper.eq("status", ZsSurveyState.CREATED);
        signWrapper.eq("creator", userId);
        return zsSignService.selectCount(signWrapper) > 0;
    }

    public Boolean isSuccess(Integer surveyId, Integer userId) {
        Wrapper<ZsSign> signWrapper = new EntityWrapper<>();
        signWrapper.eq("survey_id", surveyId);
        signWrapper.eq("status", ZsSurveyState.CREATED);
        signWrapper.eq("creator", userId);
        signWrapper.eq("result", ZsSurveySignResult.SUCCESS.getValue());
        return zsSignService.selectCount(signWrapper) > 0;
    }

    /**
     * 通过问卷ID发短信给相应的用户
     * @param survey 问卷
     */
    public void sendMessage(ZsSurvey survey, String contxt) {
        Wrapper<ZsConcat> concatWrapper = new EntityWrapper<>();
        concatWrapper.eq("survey_id", survey.getId());
        List<ZsConcat> zsConcatList = zsConcatService.selectList(concatWrapper);
        for (ZsConcat zsConcat : zsConcatList) {
            // 发送短信之前先把记录记一下
            ZsSurveyRecord zsSurveyRecord = new ZsSurveyRecord(zsConcat.getPhone(), survey.getId(), survey.getName(), applicationToolsUtils.currentUser().getId());
            surveyRecordService.insert(zsSurveyRecord);
            String url = SMS_SURVEY_URL;
            url = url + "?" + survey.getId() + "=" + zsConcat.getPhone();
            contxt = contxt.replace("?title?", survey.getName());
            contxt = contxt.replace("?url?", url);
            zsSmsService.send(new ZsSendSms(zsConcat.getPhone(), contxt));
        }
    }

    @Override
    public List<ZsQuestion> questions(Integer surveyId) {
        List<ZsQuestion> questionList = questionService.selectBySurveyId(surveyId);
        return questionList;
    }

    @Override
    public List<ZsOption> options(List<Integer> qIds) {
        Wrapper<ZsOption> zsOptionWrapper = new EntityWrapper<>();
        List<ZsOption> optionList = new ArrayList<>();
        if (qIds.size() > 0) {
            zsOptionWrapper.in("question_id", qIds);
            optionList = optionService.selectList(zsOptionWrapper);
        }
        return optionList;
    }

    @Override
    public Result valid(ZsAnswerSave zsAnswerSave) {
        ZsSurvey survey = selectById(zsAnswerSave.getSurveyId());
        if (survey.isFull()) {
            throw new SurveyNotValidException("我们的（" + survey.getName() + "）样本个数已满，不好意思打扰您了，祝您生活愉快，再见！");
        }
        List<Integer> optIds = zsAnswerSave.getOptIds();
        List<ZsOption> zsOptions = optionService.selectBatchIds(optIds);

        zsOptions.forEach(zsOption -> {
            if (zsOption.isFull()) {
                throw new SurveyNotValidException("我们（" + zsOption.getName() + "）的样本调查配额已满，谢谢您的支持！不好意思打扰了，再见。");
            }
        });
        return ResultUtil.success("");
    }

    @Override
    public void updateCurrent(Integer id) {
        mapper.updateCurrent(id);
    }
}
