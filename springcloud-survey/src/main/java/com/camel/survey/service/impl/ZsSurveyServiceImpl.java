package com.camel.survey.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.common.entity.Member;
import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.model.SysUser;
import com.camel.core.utils.PaginationUtil;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.enums.*;
import com.camel.survey.exceptions.SourceDataNotValidException;
import com.camel.survey.exceptions.SurveyFormSaveException;
import com.camel.survey.exceptions.SurveyNotValidException;
import com.camel.survey.mapper.SysUserMapper;
import com.camel.survey.mapper.ZsExamMapper;
import com.camel.survey.mapper.ZsSurveyMapper;
import com.camel.survey.model.*;
import com.camel.survey.service.*;
import com.camel.survey.utils.ApplicationToolsUtils;
import com.camel.survey.utils.ExcelUtil;
import com.camel.survey.vo.ZsAnswerSave;
import com.camel.survey.vo.ZsDynamicView;
import com.camel.survey.vo.ZsSendSms;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;
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
 * ┃┫┫    ┃┫┫    @date 2019-12-06
 * ┗┻┛    ┗┻┛
 */
@Service
public class ZsSurveyServiceImpl extends ServiceImpl<ZsSurveyMapper, ZsSurvey> implements ZsSurveyService {

    @Autowired
    private ZsSurveyMapper mapper;

    @Autowired
    private SysUserMapper sysUserMapper;

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
    private ZsAnswerService answerService;

    @Autowired
    private ZsWorkService zsWorkService;

    @Autowired
    private ZsWorkShiftService zsWorkShiftService;

    @Autowired
    private ZsPhoneInformationService phoneInformationService;

    @Autowired
    private ZsCdrinfoService cdrinfoService;

    @Autowired
    private ZsUnionConfigrationService unionConfigrationService;

    public static final String SMS_CONTEXT_MODEL = "您好，欢迎参加关于?title?的调查，参加问卷收集得话费，点击?url?";

    public static final String SMS_SURVEY_URL = "http://127.0.0.1:8080/survey/web_survey.html";

    @Override
    public List<ZsSurvey> selectList(ZsSurvey entity) {
        return mapper.list(entity);
    }

    @Override
    public PageInfo<ZsSurvey> selectPage(ZsSurvey entity, OAuth2Authentication oAuth2Authentication) {
        SysUser user = applicationToolsUtils.currentUser();
        List<Integer> roleIds = sysUserMapper.findRoleIdsByUserId(user.getUid());
        if (!CollectionUtils.isEmpty(roleIds) && roleIds.contains(7)){
            entity.setUid(user.getUid());
        }else {
            entity.setCompanyId(user.getCompanyId());
        }

        if(ObjectUtil.isEmpty(entity.getState()) && ObjectUtil.isNotEmpty(entity.getStateId())) {
            entity.setState(ZsSurveyState.getEnumByValue(entity.getStateId()));
        }
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.list(entity);
        });
        List<ZsSurvey> list = pageInfo.getList();
        list.forEach(e -> {
            applicationToolsUtils.allUsers().forEach(sysUser -> {
                if (sysUser.getUid().equals(e.getCreatorId())) {
                    e.setCreator(sysUser);
                    if (e.getCreatorId().equals(user.getUid()) || zsWorkShiftService.selectByUidandSurveyId(user.getUid(), e.getId()) != null) {
                        e.setAuthority(ZsYesOrNo.YES);
                    } else {
                        e.setAuthority(ZsYesOrNo.NO);
                    }
                }
            });
            Wrapper<ZsSign> zsSignWrapper = new EntityWrapper<>();
            zsSignWrapper.eq("status", ZsStatus.CREATED.getValue());
            zsSignWrapper.eq("survey_id", e.getId());
            zsSignWrapper.eq("creator", user.getUid());
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
        if (ObjectUtils.isEmpty(entity)) {
            return entity;
        }
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
        SysUser user = applicationToolsUtils.currentUser();
        entity.setCreatorId(user.getUid());
        if (insert(entity)) {
            return ResultUtil.success("新增成功");
        }
        return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "新增失败");
    }

    @Override
    public Result selectListByProjectId(Integer id) {
        Wrapper<ZsSurvey> zsSurveyWrapper = new EntityWrapper<>();
        zsSurveyWrapper.eq("project_id", id);
        if(ObjectUtil.isNotEmpty(applicationToolsUtils.currentUser().getCompanyId())) {
            zsSurveyWrapper.eq("company_id", applicationToolsUtils.currentUser().getCompanyId());
        }
        List<ZsSurvey> surveys = this.selectList(zsSurveyWrapper);
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
    public Result selectListByProjectIdDev(Integer id) {
        SysUser user = applicationToolsUtils.currentUser();
        Wrapper<ZsSurvey> zsSurveyWrapper = new EntityWrapper<>();
        zsSurveyWrapper.eq("project_id", id);
        if(ObjectUtil.isNotEmpty(user.getCompanyId())) {
            zsSurveyWrapper.eq("company_id", user.getCompanyId());
        }
        zsSurveyWrapper.eq("creator", applicationToolsUtils.currentUser().getUid());
        List<ZsSurvey> surveys = this.selectList(zsSurveyWrapper);
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
            List<RelSurveyExam> relSurveyExams = new ArrayList<>();
            zsSurvey.getExams().forEach(examId -> {
                relSurveyExams.add(new RelSurveyExam(examId, zsSurvey.getId()));
            });
            if (!CollectionUtils.isEmpty(relSurveyExams)) {
                Wrapper<RelSurveyExam> surveyExamWrapper = new EntityWrapper<>();
                surveyExamWrapper.eq("survey_id", zsSurvey.getId());
                relSurveyExamService.delete(surveyExamWrapper);
                relSurveyExamService.insertBatch(relSurveyExams);
            }
            return ResultUtil.success("修改成功");
        }

        return ResultUtil.success("修改失败");
    }

    @Override
    public Result sign(Integer id, OAuth2Authentication oAuth2Authentication) {
        SysUser member = applicationToolsUtils.currentUser();
        if (isSigned(id, member.getUid())) {
            return ResultUtil.success("您已经投递过了，无需重复提交");
        }
        if (isSuccess(id, member.getUid())) {
            return ResultUtil.success("您已经投递过了，并且已经审核通过，无需重复提交");
        }
        if (zsSignService.insert(new ZsSign(id, member.getUsername(), member.getUid()))) {
            return ResultUtil.success("投递成功");
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
     *
     * @param survey 问卷
     */
    public void sendMessage(ZsSurvey survey, String contxt) {
        Wrapper<ZsConcat> concatWrapper = new EntityWrapper<>();
        concatWrapper.eq("survey_id", survey.getId());
        List<ZsConcat> zsConcatList = zsConcatService.selectList(concatWrapper);
        for (ZsConcat zsConcat : zsConcatList) {
            // 发送短信之前先把记录记一下
            ZsSurveyRecord zsSurveyRecord = new ZsSurveyRecord(zsConcat.getPhone(), survey.getId(), survey.getName(), applicationToolsUtils.currentUser().getUid());
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
        // 是否满配额
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

        if(!unionConfigrationService.valid(survey.getId(), zsOptions)) {
            throw new SurveyNotValidException("我们（" + survey.getName() + "）的样本调查配额已满，谢谢您的支持！不好意思打扰了，再见。");
        }

        return ResultUtil.success("");
    }

    @Override
    public void updateCurrent(Integer id) {
        mapper.updateCurrent(id);
    }

    @Override
    public ZsSurvey getByNameFromList(String name, List<ZsSurvey> surveys) {
        for (ZsSurvey zsSurvey : surveys) {
            if (zsSurvey.getName().equals(name)) {
                return zsSurvey;
            }
        }
        return null;
    }

    @Override
    @Transactional
    public boolean importSurvey(MultipartFile file, Integer surveyId) {
        SysUser user = applicationToolsUtils.currentUser();
        List<ZsQuestion> questions = ExcelUtil.readExcelObject(file, ZsQuestion.class, ZsQuestion.loadTranslate());
        questions.forEach(q -> {
            q.setSurveyId(surveyId);
            q.setCreatorId(user.getUid());
        });
        List<ZsOption> options = ExcelUtil.readExcelObject(file, ZsOption.class, ZsOption.loadTranslate());
        List<ZsQuestion> uniqueQuestions = questions.stream().collect(
                Collectors.collectingAndThen(Collectors.toCollection(
                        () -> new TreeSet<>(Comparator.comparing(o -> o.getOrderNum()))), ArrayList::new)
        );
        Wrapper zsQuestionWrapper = new EntityWrapper<>();
        zsQuestionWrapper.eq("survey_id", surveyId);
        List<ZsQuestion> zsQuestions = questionService.selectList(zsQuestionWrapper);
        if (zsQuestions.size() > 0) {
            Wrapper<ZsOption> optionWrapper = new EntityWrapper<>();
            List<Integer> qIds = zsQuestions.stream().map(ZsQuestion::getId).collect(Collectors.toList());
            optionWrapper.in("question_id", qIds);
            questionService.deleteBatchIds(qIds);
            optionService.delete(optionWrapper);
        }
        questionService.insertBatch(uniqueQuestions);
        uniqueQuestions.forEach(q -> {
            options.forEach((o) -> {
                if (q.getName().equals(o.getQuestion())) {
                    o.setQuestionId(q.getId());
                    o.setCurrent(0);
                    o.setCreatorId(user.getUid());
                }
            });
        });
        return optionService.insertBatch(options);
    }

    @Override
    public ZsSurvey findLatestSurvey() {
        return mapper.findLatestSurvey();
    }

    @Override
    public Integer avgTime(Integer id) {
        return mapper.avgTime(id);
    }

    @Override
    public Result stopOrUse(Integer id) {
        ZsSurvey survey = mapper.selectById(id);
        if (!ObjectUtils.isEmpty(survey)) {
            if (survey.getState().getValue() == ZsSurveyState.COLLECTING.getValue()) {
                survey.setState(ZsSurveyState.CLOSED);
                if (this.updateById(survey)) {
                    return ResultUtil.success("回收问卷成功");
                }
            }
            if (survey.getState().getValue() == ZsSurveyState.CLOSED.getValue()) {
                survey.setState(ZsSurveyState.COLLECTING);
                if (this.updateById(survey)) {
                    return ResultUtil.success("启用问卷成功");
                }
            }

        }
        return ResultUtil.error(ResultEnum.NOT_VALID_PARAM.getCode(), "问卷状态更改失败");
    }

    @Override
    public String reviewRate(Integer id) {
        Wrapper wrapper = new EntityWrapper();
        wrapper.eq("survey_id", id);
        wrapper.eq("status", ZsStatus.CREATED.getValue());
        Integer count = answerService.selectCount(wrapper);
        wrapper.in("review_status", new Integer[]{1, 2});
        Integer countReviews = answerService.selectCount(wrapper);
        if (0 == count || 0 == countReviews) {
            return "0";
        }
        NumberFormat format = NumberFormat.getPercentInstance();
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(2);

        return format.format(countReviews.floatValue() / count.floatValue());
    }

    @Override
    public Result check(Integer id) {
        Wrapper wrapper = new EntityWrapper();
        wrapper.eq("survey_id", id);
        wrapper.eq("checked", 0);
        wrapper.eq("status", 1);
        List<ZsAnswer> answers = answerService.selectList(wrapper);
        List<ZsWork> zsWorks = mapper.checkAnswers(id);
        zsWorks.forEach(w -> {
            w.setSource(ZsWorkSource.CHECKED);
        });
        answers.forEach(a -> {
            a.setChecked(1);
        });
        if (answerService.updateBatchById(answers) && zsWorkService.insertBatch(zsWorks)) {
            return ResultUtil.success("结算成功");
        }
        return ResultUtil.error(ResultEnum.NOT_VALID_PARAM.getCode(), "结算失败");
    }

    @Override
    public ZsSign selectTotal(Integer surveyId, Integer id, ZsSign zsDynamicView) {
        Map<String, Object> result = mapper.selectTotal(surveyId, id);
        zsDynamicView.setTryNum((Long) result.get("try_num"));
        zsDynamicView.setSuccessNum((BigDecimal) result.get("success_num"));
        zsDynamicView.setInvalidNum((BigDecimal) result.get("invalid_num"));
        zsDynamicView.setLogicInvalidNum((BigDecimal) result.get("logic_invalid_num"));
        zsDynamicView.setCallLastsTime((Double) result.get("call_lasts_time"));
        return zsDynamicView;
    }

    @Override
    public boolean telValid(Integer id, String taskid, String tel) {
        ZsSurvey survey = mapper.selectById(id);
        List<ZsQuestion> questions = questionService.selectBySurveyId(id);

        ZsPhoneInformation phoneInformation = new ZsPhoneInformation();
        phoneInformation.setMobile(tel);
        phoneInformation.setSurveyId(id);
        List<ZsPhoneInformation> informations = phoneInformationService.selectByMobileAndSurvey(phoneInformation);

        ZsPhoneInformation information = null;
        if (!CollectionUtil.isEmpty(informations)) {
            information = informations.get(0);
        }
        if (!ObjectUtils.isEmpty(information)) {
            String infoStr = information.getInformation();
            JSONObject jsonObject = (JSONObject) JSONObject.parse(infoStr);
            for (ZsQuestion q : questions) {
                if (!StringUtils.isEmpty(q.getConfigType())) {
                    String v = jsonObject.getString(q.getConfigType());
                    if (!StringUtils.isEmpty(v)) {
                        List<ZsOption> options = optionService.selectByQuestionId(q.getId());
                        for (ZsOption o : options) {
                            // 有配额 有当前值
                            if(v.equals(o.getName())) {
                                if(!ObjectUtils.isEmpty(o.getConfigration()) && !ObjectUtils.isEmpty(o.getCurrent())) {
                                    if(o.getConfigration() <= o.getCurrent()) {
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public Result difficult(Integer id) {
        Wrapper wrapper = new EntityWrapper<>();
        wrapper.eq("survey_id", id);

        Integer count = cdrinfoService.selectCount(wrapper);

        Wrapper answerWrapper = new EntityWrapper();
        answerWrapper.eq("valid", ZsYesOrNo.YES.getCode());
        answerWrapper.eq("survey_id", id);
        Integer answerCount = answerService.selectCount(answerWrapper);

        Integer avg = this.avgTime(id);
        if(ObjectUtil.isNotEmpty(count) && count > 0) {
            Double successRate = 1.0 * answerCount / count;
            if(ObjectUtil.isNotEmpty(successRate) && successRate > 0) {
                return  ResultUtil.success(avg / successRate);
            }
        }

        return ResultUtil.success(0);
    }

    @Override
    public List<ZsPhoneInformation> selectRandom(Integer surveyId, Integer randomNum) {
        // TODO 获取号码信息列表
        return null;
    }
}
