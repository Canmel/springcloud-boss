package com.camel.survey.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.core.utils.PaginationUtil;
import com.camel.survey.mapper.ZsAnswerItemMapper;
import com.camel.survey.model.ZsAnswerItem;
import com.camel.survey.model.ZsOption;
import com.camel.survey.model.ZsQuestion;
import com.camel.survey.service.ZsAnswerItemService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
 *               ┃     ┃        < 服务实现类>
 *               ┃     ┃　　　　　　　　　　　
 *               ┃     ┗━━━━┓   @author baily
 *               ┃          ┣┓
 *               ┃          ┏┛  @since 1.0
 *               ┗┓┓┏━━━━┳┓┏┛
 *                ┃┫┫    ┃┫┫    @date 2019-12-17
 *                ┗┻┛    ┗┻┛
 */
@Service
public class ZsAnswerItemServiceImpl extends ServiceImpl<ZsAnswerItemMapper, ZsAnswerItem> implements ZsAnswerItemService {

    @Autowired
    private ZsAnswerItemMapper mapper;

    @Override
    public PageInfo<ZsAnswerItem> selectPage(ZsAnswerItem entity) {
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.list(entity);
        });
        return pageInfo;
    }

    @Override
    public Map<String, Object> selectCrossCount(ZsQuestion qF, ZsQuestion qS, ZsOption oF, ZsOption oS, Integer surveyId) {

        return mapper.selectCrossCount(qF.getName(), qS.getName(), oF.getName(), oS.getName(), surveyId);
    }

    @Override
    public List<Map<String, Object>> selectCrossCounts(ZsQuestion qF, ZsQuestion qS, ZsOption os, Integer id) {

        return mapper.selectCrossCounts(qF.getName(), qS.getName(), os.getName(), id);
    }


    @Override
    public List<ZsAnswerItem> selectBySurvey(Integer surveyId) {
        return mapper.selectBySurvey(surveyId);
    }

    @Override
    public List<Map<String, Object>> selectExport(Integer id) {
        return mapper.selectExport(id);
    }
}
