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

    ZsAnswer details(String agent);

    Result invalid(Integer id);

    Result deleteAnswer(Integer id);

    List<ZsAnswer> selectAllWithConversation(Integer id);

    ZsAnswer selectByAgentUuid(String agentUuid);

    boolean review(Integer answerId, Integer reviewStatus, String reviewMsg);

    List<ZsAnswer> randomList(ZsAnswer zsAnswer);

    Set<String> selectAgentUuidsByEntity(ZsAnswer entity);
}
