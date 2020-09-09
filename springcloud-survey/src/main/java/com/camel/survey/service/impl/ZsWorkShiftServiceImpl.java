package com.camel.survey.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
import com.camel.core.utils.PaginationUtil;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.enums.ZsStatus;
import com.camel.survey.enums.ZsSurveySignResult;
import com.camel.survey.enums.ZsYesOrNo;
import com.camel.survey.exceptions.SourceDataNotValidException;
import com.camel.survey.mapper.ZsExamMapper;
import com.camel.survey.model.ZsExam;
import com.camel.survey.model.ZsWorkRecord;
import com.camel.survey.model.ZsWorkShift;
import com.camel.survey.mapper.ZsWorkShiftMapper;
import com.camel.survey.service.ZsSurveyService;
import com.camel.survey.service.ZsWorkRecordService;
import com.camel.survey.service.ZsWorkShiftService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.survey.utils.ApplicationToolsUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baily
 * @since 2020-04-14
 */
@Service
public class ZsWorkShiftServiceImpl extends ServiceImpl<ZsWorkShiftMapper, ZsWorkShift> implements ZsWorkShiftService {

    @Autowired
    private ApplicationToolsUtils applicationToolsUtils;

    @Autowired
    private ZsWorkShiftMapper mapper;

    @Autowired
    private ZsExamMapper zsExamMapper;

    @Autowired
    private ZsWorkRecordService service;

    @Autowired
    private ZsSurveyService zsSurveyService;

    @Override
    public PageInfo<ZsWorkShift> selectPage(ZsWorkShift entity) {
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.list(entity);
        });
        Wrapper<ZsWorkRecord> zsWorkRecordWrapper = new EntityWrapper<>();
        zsWorkRecordWrapper.eq("cid_num",entity.getIdNum());
        List<ZsWorkRecord> workRecord = service.selectList(zsWorkRecordWrapper);
        List<ZsWorkShift> ZsWorkShifts = pageInfo.getList();
        ZsWorkShifts.forEach(ZsWorkShift -> {
            String startTime =  ZsWorkShift.getStartTime();
            String endTime =  ZsWorkShift.getEndTime();
            ZsWorkShift.setWtime(startTime+":00 - "+endTime+":00");
            ZsWorkShift.setSurvey(zsSurveyService.selectById(ZsWorkShift.getSurveyId()));
        });
        return pageInfo;
    }

    @Override
    public Result saveWorkShift(ZsWorkShift entity) {
        return ResultUtil.success("新建班次成功！",mapper.insert(entity));
    }

    @Override
    public Result updateWorkShift(ZsWorkShift entity) {
        return ResultUtil.success("修改班次成功",mapper.updateById(entity));
    }

    @Override
    public ZsWorkShift selectByUidandSurveyId(Integer userId,Integer surveyId) {
        ZsWorkShift ws = new ZsWorkShift();
        ws.setCreatorId(userId);
        ws.setSurveyId(surveyId);
        if(mapper.findWsByShift(ws).size()>0){
            return mapper.findWsByShift(ws).get(0);
        }
        return null;
    }


    @Override
    public List<ZsWorkShift> all(ZsWorkShift entity, String openId) {
        return mapper.list(entity);
    }
}
