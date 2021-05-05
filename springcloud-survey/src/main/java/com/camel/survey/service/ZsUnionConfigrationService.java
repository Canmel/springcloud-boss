package com.camel.survey.service;

import com.camel.survey.model.ZsOption;
import com.camel.survey.model.ZsSurvey;
import com.camel.survey.model.ZsUnionConfigration;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baily
 * @since 2021-05-05
 */
public interface ZsUnionConfigrationService extends IService<ZsUnionConfigration> {

    /**
     * 根据多个选项，查找现存有效答案数量
     * @param options
     * @return
     */
    int selectValidAnserCountByMultipleOption(List<Integer> options, Integer surveyId);

    /**
     * 验证问卷选项是否可用
     * @param surveyId,
     * @param options
     * @return
     */
    boolean valid(Integer surveyId, List<ZsOption> options);

    /**
     * 查询所选选项是否沾染联合配额
     * @param configration
     * @param options
     * @return
     */
    List<ZsUnionConfigration> involveList(ZsUnionConfigration configration, List<ZsOption> options);
}
