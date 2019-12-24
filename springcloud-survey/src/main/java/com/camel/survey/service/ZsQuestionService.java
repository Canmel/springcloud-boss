package com.camel.survey.service;

import com.camel.core.entity.Result;
import com.camel.survey.model.ZsProject;
import com.camel.survey.model.ZsQuestion;
import com.baomidou.mybatisplus.service.IService;
import com.camel.survey.vo.ZsAnswerSave;
import com.camel.survey.vo.ZsQuestionSave;
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
 *                ┃┫┫    ┃┫┫    @date 2019-12-09
 *                ┗┻┛    ┗┻┛
 */
public interface ZsQuestionService extends IService<ZsQuestion> {
    /**
     分页查询
     @param entity
     @return
     */
    PageInfo<ZsQuestion> selectPage(ZsQuestion entity);

    /**
     * 新建
     * @param entity
     * @param oAuth2Authentication
     * @return
     */
    Result save(ZsQuestionSave entity, OAuth2Authentication oAuth2Authentication);

    /**
     * 修改更新
     * @param entity
     * @param oAuth2Authentication
     * @return
     */
    Result update(ZsQuestionSave entity, OAuth2Authentication oAuth2Authentication);

    /**
     * 问卷结果保存
     * @param zsAnswerSave
     * @return
     */
    Result saveAnswer(ZsAnswerSave zsAnswerSave);

    /**
     * 通过问卷ID获取问题
     */
    List<ZsQuestion> selectBySurveyId(Integer id);
}
