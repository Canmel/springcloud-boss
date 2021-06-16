package com.camel.survey.service;

import com.baomidou.mybatisplus.service.IService;
import com.camel.core.entity.Result;
import com.camel.survey.model.ZsAnswer;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Set;

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
public interface ZsAnswerService extends IService<ZsAnswer> {
    /**
     分页查询
     @param entity
     @return
     */
    PageInfo<ZsAnswer> selectPage(ZsAnswer entity);

    List<ZsAnswer> list(ZsAnswer entity);

    /**
     * 获取答案详情
     * @param id
     * @return
     */
    ZsAnswer details(Integer id);

    /**
     * 获取答案详情
     * @param id
     * @return
     */
    ZsAnswer full(Integer id);

    ZsAnswer details(String agent);

    Result toInvalid(Integer id);

    Result toValid(Integer id);

    Result invalid(Integer id);

    Result deleteAnswer(Integer id);

    List<ZsAnswer> selectAllWithConversation(Integer id);

    List<ZsAnswer> selectByAgentUuid(String agentUuid);

    boolean review(Integer answerId, Integer reviewStatus, String reviewMsg, Integer reviewSpent);

    List<ZsAnswer> randomList(ZsAnswer zsAnswer);

    Set<String> selectAgentUuidsByEntity(ZsAnswer entity);

    String selectTimeRange(Integer surveyId);

    String selectTimeRangeReview(Integer surveyId);

    /**
     * 通过问卷ID和外呼号码 确定唯一的样本
     * @param surveyId
     * @param callee_num
     * @return
     */
    ZsAnswer selectBySurveyIdAndPhone(Integer surveyId, String callee_num);
}
