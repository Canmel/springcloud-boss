package com.camel.survey.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.camel.common.entity.Member;
import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.utils.ResultUtil;
import com.camel.redis.utils.SessionContextUtils;
import com.camel.survey.annotation.AuthIgnore;
import com.camel.survey.model.*;
import com.camel.survey.mapper.ZsQuestionMapper;
import com.camel.survey.service.*;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.core.utils.PaginationUtil;
import com.camel.survey.vo.ZsAnswerSave;
import com.camel.survey.vo.ZsQuestionSave;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

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
    private RedisTemplate redisTemplate;

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
        if(zsQuestions.size() > 0) {
            Wrapper<ZsOption> optionWrapper = new EntityWrapper<>();
            List<Integer> qIds = zsQuestions.stream().map(ZsQuestion::getId).collect(Collectors.toList());
            optionWrapper.in("question_id", qIds);
            deleteBatchIds(qIds);
            zsOptionService.delete(optionWrapper);
        }
        Member member = (Member) SessionContextUtils.getInstance().currentUser(redisTemplate, oAuth2Authentication.getName());
        // 保存问题
        entity.getZsQuestions().forEach(q -> {
            q.setCreatorId(member.getId());
            mapper.insert(q);
            List<ZsOption> optionList = entity.optionsfilterAndBuildInsertParams(q);
            // 保存选项
            if (CollectionUtils.isNotEmpty(optionList)) {
                zsOptionService.insertBatch(optionList);
            }
        });
        return ResultUtil.success("新增成功");

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
    public Result saveAnswer(ZsAnswerSave zsAnswerSave) {
        Wrapper<ZsAnswer> zsAnswerWrapper = new EntityWrapper<>();
        zsAnswerWrapper.eq("creator", zsAnswerSave.getPhone());
        int count = answerService.selectCount(zsAnswerWrapper);
        if(count > 0) {
            return ResultUtil.success("提交成功");
        }
        ZsSurvey zsSurvey = surveyService.selectById(zsAnswerSave.getSurveyId());
        zsSurvey.setCurrentNum(zsSurvey.getCurrentNum() + 1);
        surveyService.updateById(zsSurvey);
        ZsAnswer zsAnswer = zsAnswerSave.buildAnswer();
        answerService.insert(zsAnswer);
        List<ZsQuestion> zsQuestions = surveyService.questions(zsAnswerSave.getSurveyId());
        List<Integer> qIds = zsQuestions.stream().map(ZsQuestion::getId).collect(Collectors.toList());
        List<ZsOption> zsOptions = surveyService.options(qIds);

        List<ZsAnswerItem> zsAnswerItemList = zsAnswerSave.buildAnswerItems(zsQuestions, zsOptions, zsAnswer.getId());
        if(answerItemService.insertBatch(zsAnswerItemList)) {
            return ResultUtil.success("提交成功");
        } else {
            return ResultUtil.error(ResultEnum.SERVICE_ERROR);
        }
    }
}
