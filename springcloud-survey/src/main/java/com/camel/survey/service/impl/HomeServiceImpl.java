package com.camel.survey.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
import com.camel.core.utils.PaginationUtil;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.enums.ZsStatus;
import com.camel.survey.enums.ZsSurveyState;
import com.camel.survey.mapper.HomeMapper;
import com.camel.survey.model.ZsSurvey;
import com.camel.survey.service.HomeService;
import com.camel.survey.service.ZsProjectService;
import com.camel.survey.service.ZsSurveyService;
import com.camel.survey.utils.ApplicationToolsUtils;
import com.camel.survey.vo.ZsHomeLineChart;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *
 *                 ___====-_  _-====___
 *           _--^^^#####//      \\#####^^^--_
 *        _-^##########// (    ) \\##########^-_
 *       -############//  |\^^/|  \\############-
 *     _/############//   (@::@)   \\############\_
 *    /#############((     \\//     ))#############\
 *   -###############\\    (oo)    //###############-
 *  -#################\\  / VV \  //#################-
 * -###################\\/      \//###################-
 *_#/|##########/\######(   /\   )######/\##########|\#_
 *|/ |#/\#/\#/\/  \#/\##\  |  |  /##/\#/  \/\#/\#/\#| \|
 *`  |/  V  V  `   V  \#\| |  | |/#/  V   '  V  V  \|  '
 *   `   `  `      `   / | |  | | \   '      '  '   '
 *                    (  | |  | |  )
 *                   __\ | |  | | /__
 *                  (vvv(VVV)(VVV)vvv)
 * <首页>
 * @author baily
 * @since 1.0
 * @date 2019/12/18
 **/
@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private ZsProjectService projectService;

    @Autowired
    private ZsSurveyService surveyService;

    @Autowired
    private HomeMapper mapper;

    @Autowired
    private ApplicationToolsUtils applicationToolsUtils;

    @Override
    public Result total() {
        return ResultUtil.success(mapper.total());
    }

    @Override
    public Result collecting() {
        Wrapper<ZsSurvey> zsSurveyWrapper = new EntityWrapper<>();
        zsSurveyWrapper.eq("state", ZsSurveyState.COLLECTING.getValue());
        zsSurveyWrapper.eq("status", ZsStatus.CREATED.getValue());
        List<ZsSurvey> zsSurveys = surveyService.selectList(zsSurveyWrapper);
        return ResultUtil.success(zsSurveys);
    }

    @Override
    public Result lineChart() {
        return ResultUtil.success(new ZsHomeLineChart(mapper.lineChartSurvey(), mapper.lineChartCollect()));
    }

    @Override
    public Result pieChart() {
        return ResultUtil.success(mapper.pieChart());
    }

    @Override
    public PageInfo enabledSurvies(boolean isAll, BasePaginationEntity entity) {
        Integer cid = applicationToolsUtils.currentUser().getCompanyId();
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.enabledSurvies(isAll, cid);
        });
        return pageInfo;
    }

    @Override
    public PageInfo enabledSurviesDev(boolean isAll, BasePaginationEntity entity) {
        SysUser user = applicationToolsUtils.currentUser();
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.enabledSurviesDev(isAll, user.getUid());
        });
        return pageInfo;
    }
}
