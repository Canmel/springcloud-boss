package com.camel.survey.service;

import com.camel.core.entity.Result;
import com.camel.survey.model.ZsOtherSurvey;
import com.baomidou.mybatisplus.service.IService;
import com.camel.survey.model.ZsSurvey;
import com.github.pagehelper.PageInfo;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

/**
 * <p>
 * 其他平台问卷 服务类
 * </p>
 *
 * @author baily
 * @since 2020-06-18
 */
public interface ZsOtherSurveyService extends IService<ZsOtherSurvey> {

    /**
     分页查询
     @param entity
     @return
     */
    PageInfo<ZsOtherSurvey> selectPage(ZsOtherSurvey entity, OAuth2Authentication oAuth2Authentication);

    /**
     * 新建
     * @param entity
     * @param oAuth2Authentication
     * @return
     */
    Result save(ZsOtherSurvey entity, OAuth2Authentication oAuth2Authentication) throws Exception;

    /**
     * 重写修改
     * @param zsOtherSurvey
     * @return
     */
    Result update(ZsOtherSurvey zsOtherSurvey) throws Exception;

}
