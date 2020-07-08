package com.camel.survey.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.camel.core.utils.PaginationUtil;
import com.camel.survey.model.ZsSurveyBenchmark;
import com.camel.survey.mapper.ZsSurveyBenchmarkMapper;
import com.camel.survey.service.ZsSurveyBenchmarkService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 项目基准 服务实现类
 * </p>
 *
 * @author baily
 * @since 2020-07-08
 */
@Service
public class ZsSurveyBenchmarkServiceImpl extends ServiceImpl<ZsSurveyBenchmarkMapper, ZsSurveyBenchmark> implements ZsSurveyBenchmarkService {

    @Autowired
    private ZsSurveyBenchmarkMapper mapper;

    @Override
    public PageInfo<ZsSurveyBenchmark> selectPage(ZsSurveyBenchmark entity) {
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.list(entity);
        });
        return pageInfo;
    }

    @Override
    public List<ZsSurveyBenchmark> selectListByPids(Integer id) {
        Wrapper wrapper = new EntityWrapper();
        wrapper.eq("pid", id);
        return mapper.selectList(wrapper);
    }
}
