package com.camel.survey.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.camel.common.entity.Member;
import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
import com.camel.core.utils.ResultUtil;
import com.camel.redis.utils.SessionContextUtils;
import com.camel.survey.model.ZsOption;
import com.camel.survey.model.ZsProject;
import com.camel.survey.model.ZsQuestion;
import com.camel.survey.model.ZsSurvey;
import com.camel.survey.mapper.ZsSurveyMapper;
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

    @Override
    public PageInfo<ZsSurvey> selectPage(ZsSurvey entity) {
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.list(entity);
        });

        List<ZsSurvey> list = pageInfo.getList();
        list.forEach(e -> {
            applicationToolsUtils.allUsers().forEach(sysUser -> {
                if(sysUser.getUid().equals(e.getCreatorId())) {
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
            if(sysUser.getUid().equals(entity.getCreatorId())) {
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
            return ResultUtil.success("新增成功");
        }
        return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "新增失败");
    }

    @Override
    public Result selectListByProjectId(Integer id) {
        Wrapper<ZsSurvey> surveyWrapper = new EntityWrapper<>();
        surveyWrapper.eq("project_id", id);
        List<ZsSurvey> surveys = selectList(surveyWrapper);
        List<SysUser> users = applicationToolsUtils.allUsers();
        surveys.forEach(survey -> {
            users.forEach(sysUser -> {
                if(sysUser.getUid().equals(survey.getCreatorId())){
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
        if(questionIds.size() > 0) {
            zsOptionWrapper.in("question_id", questionIds);
            optionList = optionService.selectList(zsOptionWrapper);
        }

        // 包装返回
        return ResultUtil.success(new ZsQuestionSave(questionList, optionList));
    }
}
