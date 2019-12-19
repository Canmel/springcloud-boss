package com.camel.survey.service;

import com.camel.core.entity.Result;

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
}
