package com.camel.survey.service.impl;

import com.camel.survey.mapper.ZsSeatMapper;
import com.camel.survey.model.ZsPhoneInformation;
import com.camel.survey.mapper.ZsPhoneInformationMapper;
import com.camel.survey.model.ZsSeat;
import com.camel.survey.service.ZsPhoneInformationService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baily
 * @since 2020-06-10
 */
@Service
public class ZsPhoneInformationServiceImpl extends ServiceImpl<ZsPhoneInformationMapper, ZsPhoneInformation> implements ZsPhoneInformationService {

    @Autowired
    public ZsPhoneInformationMapper mapper;

    @Override
    public PageInfo<ZsPhoneInformation> pageQuery(ZsPhoneInformation zsPhoneInformation) {
        PageInfo pageInfo = PageHelper.startPage(zsPhoneInformation.getPageNum(), zsPhoneInformation.getPageSize()).doSelectPageInfo(()-> mapper.list(zsPhoneInformation));
        return pageInfo;
    }

}
