package com.camel.survey.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.core.utils.PaginationUtil;
import com.camel.survey.mapper.ZsOptionMapper;
import com.camel.survey.model.ZsOption;
import com.camel.survey.service.ZsOptionService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
 *               ┃     ┃        < 服务实现类>
 *               ┃     ┃　　　　　　　　　　　
 *               ┃     ┗━━━━┓   @author baily
 *               ┃          ┣┓
 *               ┃          ┏┛  @since 1.0
 *               ┗┓┓┏━━━━┳┓┏┛
 *                ┃┫┫    ┃┫┫    @date 2019-12-09
 *                ┗┻┛    ┗┻┛
 */
@Service
public class ZsOptionServiceImpl extends ServiceImpl<ZsOptionMapper, ZsOption> implements ZsOptionService {

    @Autowired
    private ZsOptionMapper mapper;

    @Override
    public PageInfo<ZsOption> selectPage(ZsOption entity) {
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.list(entity);
        });
        return pageInfo;
    }

    @Override
    public List<ZsOption> selectByQuestionId(Integer qId) {
        return mapper.selectByQuestionId(qId);
    }

    @Override
    public List<ZsOption> selectFllByQuestionId(Integer qId) {
        return mapper.selectFullByQuestionId(qId);
    }

    @Override
    public List<ZsOption> selectBySurveyId(Integer sId) {
        return mapper.selectBySurveyId(sId);
    }

    @Override
    public ZsOption selectByQuestionAndName(Integer qId, String name) {
        return mapper.selectByQuestionAndName(qId, name);
    }

    @Override
    public void addOptionCurrent(List<Integer> optionIds) {
        mapper.addOptionCurrent(optionIds);
    }

    @Override
    public Boolean contanisIgnore(List<Integer> oIds) {
        return mapper.selectIgnoreCount(oIds) > 0;
    }
}
