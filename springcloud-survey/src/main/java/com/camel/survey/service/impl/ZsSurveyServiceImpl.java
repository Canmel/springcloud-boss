package com.camel.survey.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.camel.common.entity.Member;
import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.model.SysUser;
import com.camel.core.utils.ResultUtil;
import com.camel.redis.utils.SessionContextUtils;
import com.camel.survey.enums.ZsStatus;
import com.camel.survey.enums.ZsSurveyState;
import com.camel.survey.model.*;
import com.camel.survey.mapper.ZsSurveyMapper;
import com.camel.survey.service.RelSurveyExamService;
import com.camel.survey.service.ZsOptionService;
import com.camel.survey.service.ZsQuestionService;
import com.camel.survey.service.ZsSurveyService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.core.utils.PaginationUtil;
import com.camel.survey.utils.ApplicationToolsUtils;
import com.camel.survey.vo.ZsQuestionSave;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.ArrayList;
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
    private ApplicationToolsUtils applicationToolsUtils;

    @Autowired
    private RelSurveyExamService relSurveyExamService;

    @Override
    public PageInfo<ZsSurvey> selectPage(ZsSurvey entity) {
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
    public Result save(ZsSurvey entity, OAuth2Authentication oAuth2Authentication) {
        Member member = (Member) SessionContextUtils.getInstance().currentUser(redisTemplate, oAuth2Authentication.getName());
        entity.setCreatorId(member.getId());
        if (insert(entity)) {
            List<RelSurveyExam> relSurveyExamList = new ArrayList<>();
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
        // 查询问题
        Wrapper<ZsQuestion> zsQuestionWrapper = new EntityWrapper<>();
        zsQuestionWrapper.eq("survey_id", id);
        List<ZsQuestion> questionList = questionService.selectList(zsQuestionWrapper);
        // 获取所有问题ID
        List<Integer> questionIds = questionList.stream().map(ZsQuestion::getId).collect(Collectors.toList());
        // 获取所有选项
        Wrapper<ZsOption> zsOptionWrapper = new EntityWrapper<>();
        List<ZsOption> optionList = new ArrayList<>();
        if (questionIds.size() > 0) {
            zsOptionWrapper.in("question_id", questionIds);
            optionList = optionService.selectList(zsOptionWrapper);
        }

        // 包装返回
        return ResultUtil.success(new ZsQuestionSave(questionList, optionList));
    }

    @Override
    public Result start(Integer id) {
        ZsSurvey survey = mapper.selectById(id);
        if (!ObjectUtils.isEmpty(survey)) {
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
    public Result sign(OAuth2Authentication oAuth2Authentication) {
        Member member = applicationToolsUtils.currentUser(oAuth2Authentication);

        return ResultUtil.success("");
    }
}
