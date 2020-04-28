package com.camel.survey.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.camel.core.utils.PaginationUtil;
import com.camel.survey.enums.ZsStatus;
import com.camel.survey.enums.ZsSurveySignResult;
import com.camel.survey.enums.ZsYesOrNo;
import com.camel.survey.model.ZsWorkRecord;
import com.camel.survey.model.ZsWorkShift;
import com.camel.survey.mapper.ZsWorkShiftMapper;
import com.camel.survey.model.ZsWorkWorkshift;
import com.camel.survey.service.ZsWorkRecordService;
import com.camel.survey.service.ZsWorkShiftService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.survey.service.ZsWorkWorkshiftService;
import com.camel.survey.utils.ApplicationToolsUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
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
    private ZsWorkRecordService service;

    @Override
    public PageInfo<ZsWorkShift> selectPage(ZsWorkShift entity, Principal principal) {

        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.list();
        });
        Wrapper<ZsWorkRecord> zsWorkRecordWrapper = new EntityWrapper<>();
        zsWorkRecordWrapper.eq("creator",entity.getUId());
        List<ZsWorkRecord> workRecord = service.selectList(zsWorkRecordWrapper);
        List<ZsWorkShift> list = pageInfo.getList();
        list.forEach(e -> {

            if(workRecord.size()>0){
                workRecord.forEach(v -> {
                    if (e.getId().equals(v.getWsId()) && v.getResult().equals(ZsSurveySignResult.SUCCESS)){
                        e.setStatusUserId(v.getId());
                        e.setStatusUser(ZsYesOrNo.YES);
                    }
                });
            }
            String startTime =  e.getStartTime();
            String endTime =  e.getEndTime();
            e.setWtime(startTime+":00-"+endTime+":00");
        });

        return pageInfo;
    }

}
