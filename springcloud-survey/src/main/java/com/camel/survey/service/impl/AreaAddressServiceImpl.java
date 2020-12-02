package com.camel.survey.service.impl;

import com.camel.core.utils.PaginationUtil;
import com.camel.survey.model.AreaAddress;
import com.camel.survey.mapper.AreaAddressMapper;
import com.camel.survey.service.AreaAddressService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baily
 * @since 2020-12-02
 */
@Service
public class AreaAddressServiceImpl extends ServiceImpl<AreaAddressMapper, AreaAddress> implements AreaAddressService {

    @Autowired
    private AreaAddressMapper mapper;

    @Override
    public PageInfo<AreaAddress> pageQuery(AreaAddress entity) {
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.list(entity);
        });
        return pageInfo;
    }

    @Override
    public List<AreaAddress> selectMatch(String text, Integer areaId) {
        String[] texts = text.split(" ");
        List<String> textList =  new ArrayList<>();
        for (String t: texts) {
            if(StringUtils.isNotBlank(t.trim())) {
                textList.add(t);
            }
        }
        if(textList.size() > 1) {
            return mapper.selectMatchList(textList, areaId);
        }
        return mapper.selectMatch(text.trim(), areaId);
    }
}
