package com.camel.survey.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.camel.core.model.SysUser;
import com.camel.survey.mapper.ZsSeatMapper;
import com.camel.survey.model.ZsOption;
import com.camel.survey.model.ZsPhoneInformation;
import com.camel.survey.mapper.ZsPhoneInformationMapper;
import com.camel.survey.model.ZsQuestion;
import com.camel.survey.model.ZsSeat;
import com.camel.survey.service.ZsPhoneInformationService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.survey.utils.ApplicationToolsUtils;
import com.camel.survey.utils.ExcelUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private ApplicationToolsUtils applicationToolsUtils;

    @Override
    public PageInfo<ZsPhoneInformation> pageQuery(ZsPhoneInformation zsPhoneInformation) {
        PageInfo pageInfo = PageHelper.startPage(zsPhoneInformation.getPageNum(), zsPhoneInformation.getPageSize()).doSelectPageInfo(()-> mapper.list(zsPhoneInformation));
        return pageInfo;
    }

    @Override
    public boolean importPhoneInformation(MultipartFile file, Integer surveyId) {
        SysUser user = applicationToolsUtils.currentUser();
        List<ZsPhoneInformation> zsPhoneInformations = ExcelUtil.readExcelPI(file, surveyId, user);
        if(insertBatch(zsPhoneInformations, 200)){
            return mapper.removeBySurveyId(surveyId) > 0;
        }
        return true;
    }

    @Override
    public List<ZsPhoneInformation> selectByMobileAndSurvey(ZsPhoneInformation zsPhoneInformation) {
        Wrapper zsPhoneInformationWrapper = new EntityWrapper<>();
        zsPhoneInformationWrapper.eq("survey_id", zsPhoneInformation.getSurveyId());
        zsPhoneInformationWrapper.eq("mobile", zsPhoneInformation.getMobile());
        zsPhoneInformationWrapper.eq("status", 1);
        zsPhoneInformationWrapper.orderDesc(CollectionUtils.arrayToList(new String[]{"created_at"}));
        return selectList(zsPhoneInformationWrapper);
    }

    @Override
    public List<Object> selectBySurveyId(Integer surveyId, Integer pageNum) {
        Wrapper<ZsPhoneInformation> wrapper = new EntityWrapper();
        wrapper.eq("survey_id", surveyId);
        return PageHelper.startPage(pageNum, 1000).doSelectPageInfo(()-> selectList(wrapper)).getList();
    }

    @Override
    public Integer selectBySurveyIdCount(Integer surveyId) {
        Wrapper wrapper = new EntityWrapper();
        wrapper.eq("survey_id", surveyId);
        return selectCount(wrapper);
    }
}
