package com.camel.survey.service;

import com.baomidou.mybatisplus.service.IService;
import com.camel.survey.model.ZsAnswerItem;
import com.camel.survey.model.ZsOption;
import com.camel.survey.model.ZsQuestion;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

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
 *                ┃┫┫    ┃┫┫    @date 2019-12-17
 *                ┗┻┛    ┗┻┛
 */
public interface ZsAnswerItemService extends IService<ZsAnswerItem> {
    /**
     分页查询
     @param entity
     @return
     */
    PageInfo<ZsAnswerItem> selectPage(ZsAnswerItem entity);

    Map<String, Object> selectCrossCount(ZsQuestion qF, ZsQuestion qS, ZsOption oF, ZsOption oS, Integer surveyId);

    List<ZsAnswerItem> selectBySurvey(Integer surveyId);

    List<Map<String, Object>> selectExport(Integer id);

    List<Map<String, Object>> selectCrossCounts(ZsQuestion qF, ZsQuestion qS, ZsOption os, Integer id);

    List<Map<String, Object>> selectSeatTotal(Integer id);
}
