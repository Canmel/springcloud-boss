package com.camel.survey.service;

import com.camel.core.entity.BasePaginationEntity;
import com.camel.core.entity.Result;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface HomeService {
    /**
     * 首页统计
     * @return
     */
    Result total();

    /**
     * 调查中的问卷
     * @return
     */
    Result collecting();

    /**
     * 直线图数据
     * @return
     */
    Result lineChart();

    /**
     * 饼图数据
     * @return
     */
    Result pieChart();

    /**
     * 开启的项目问卷
     * @return
     */
    PageInfo enabledSurvies(boolean isAll, BasePaginationEntity entity);

    /**
     * 督导自己创建的开启的项目问卷
     * @return
     */
    PageInfo enabledSurviesDev(boolean isAll, BasePaginationEntity entity);
}
