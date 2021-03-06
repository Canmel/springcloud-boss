package com.camel.survey.service;

import com.baomidou.mybatisplus.service.IService;
import com.camel.core.entity.Result;
import com.camel.survey.model.ZsOption;
import com.camel.survey.model.ZsQuestion;
import com.camel.survey.model.ZsSurvey;
import com.camel.survey.vo.ZsAnswerSave;
import com.github.pagehelper.PageInfo;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

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
 *               ┃     ┃        < 服务类>
 *               ┃     ┃　　　　　　　　　　　
 *               ┃     ┗━━━━┓   @author baily
 *               ┃          ┣┓
 *               ┃          ┏┛  @since 1.0
 *               ┗┓┓┏━━━━┳┓┏┛
 *                ┃┫┫    ┃┫┫    @date 2019-12-06
 *                ┗┻┛    ┗┻┛
 */
public interface ZsSurveyService extends IService<ZsSurvey> {
    /**
     分页查询
     @param entity
     @return
     */
    PageInfo<ZsSurvey> selectPage(ZsSurvey entity, OAuth2Authentication oAuth2Authentication);

    /**
     * 新建
     * @param entity
     * @param oAuth2Authentication
     * @return
     */
    Result save(ZsSurvey entity, OAuth2Authentication oAuth2Authentication);

    /**
     * 通过项目id查询列表
     * @param id    项目ID
     * @return
     */
    Result selectListByProjectId(Integer id);

    /**
     * 通过问卷ID获取问题与选项
     * @param id    问卷id
     * @return
     */
    Result getQuestionAndOptions(Integer id);

    /**
     * 开始收集
     * @param id
     * @return
     */
    Result start(Integer id);

    /**
     * 重写修改
     * @param zsSurvey
     * @return
     */
    Result update(ZsSurvey zsSurvey);

    /**
     * 申请报名参加调查问卷
     * @param oAuth2Authentication
     * @return
     */
    Result sign(Integer id, OAuth2Authentication oAuth2Authentication);

    /**
     * 通过问卷ID 获取问题
     * @param surveyId
     * @return
     */
    List<ZsQuestion> questions(Integer surveyId);

    /**
     * 通过问题id获取选项
     * @param qIds
     * @return
     */
    List<ZsOption> options(List<Integer> qIds);

    Result valid(ZsAnswerSave zsAnswerSave);

    void updateCurrent(Integer id);

}
