package com.camel.survey.service;

import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.model.ZsProject;
import com.camel.survey.model.ZsSurvey;
import com.baomidou.mybatisplus.service.IService;
import com.github.pagehelper.PageInfo;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;

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
    PageInfo<ZsSurvey> selectPage(ZsSurvey entity);

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
}
