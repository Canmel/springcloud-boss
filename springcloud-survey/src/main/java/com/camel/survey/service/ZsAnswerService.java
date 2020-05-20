package com.camel.survey.service;

import com.baomidou.mybatisplus.service.IService;
import com.camel.core.entity.Result;
import com.camel.survey.model.ZsAnswer;
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

    /**
     * 获取答案详情
     * @param id
     * @return
     */
    ZsAnswer details(Integer id);

    Result invalid(Integer id);

    Result deleteAnswer(Integer id);

    List<ZsAnswer> selectAllWithConversation(Integer id);

    ZsAnswer selectByAgentUuid(String agentUuid);
}
