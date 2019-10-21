package com.camel.oa.service;

import com.baomidou.mybatisplus.service.IService;
import com.camel.core.entity.Result;
import com.camel.oa.model.Questionnaire;
import com.github.pagehelper.PageInfo;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

public interface QuestionnaireService extends IService<Questionnaire> {
    /**
     * 分页查询
     *
     * @param entity
     * @return
     */
    PageInfo<Questionnaire> selectPage(Questionnaire entity);

    /**
     * 保存
     *
     * @param entity
     * @return
     */
    Result save(Questionnaire entity, OAuth2Authentication authentication);
}
