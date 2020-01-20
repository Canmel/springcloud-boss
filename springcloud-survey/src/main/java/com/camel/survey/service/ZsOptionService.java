package com.camel.survey.service;

import com.baomidou.mybatisplus.service.IService;
import com.camel.survey.model.ZsOption;
import com.github.pagehelper.PageInfo;

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
public interface ZsOptionService extends IService<ZsOption> {
    /**
     分页查询
     @param entity
     @return
     */
    PageInfo<ZsOption> selectPage(ZsOption entity);

    /**
     * 通过问题ID获取所有的选项
     * @param qId
     * @return
     */
    List<ZsOption> selectByQuestionId(Integer qId);

    /**
     * 通过问题ID获取所有的选项
     * @param qId
     * @return
     */
    List<ZsOption> selectFllByQuestionId(Integer qId);

    /**
     * 选项
     * @param sId
     * @return
     */
    List<ZsOption> selectBySurveyId(Integer sId);

    ZsOption selectByQuestionAndName(Integer qId, String name);

    void addOptionCurrent(List<Integer> optionIds);

    Boolean contanisIgnore(List<Integer> oIds);
}
