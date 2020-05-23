package com.camel.survey.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.camel.core.entity.Result;
import com.camel.core.utils.PaginationUtil;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.enums.ZsAccessState;
import com.camel.survey.model.ZsSign;
import com.camel.survey.model.ZsWorkRecord;
import com.camel.survey.mapper.ZsWorkRecordMapper;
import com.camel.survey.model.ZsWorkShift;
import com.camel.survey.service.ZsSignService;
import com.camel.survey.service.ZsSurveyService;
import com.camel.survey.service.ZsWorkRecordService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.survey.service.ZsWorkShiftService;
import com.camel.survey.utils.ApplicationToolsUtils;
import com.camel.survey.utils.TimeCompare;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baily
 * @since 2020-04-27
 */
@Service
public class ZsWorkRecordServiceImpl extends ServiceImpl<ZsWorkRecordMapper, ZsWorkRecord> implements ZsWorkRecordService {

    @Autowired
    private ZsWorkRecordMapper mapper;

    @Autowired
    private ZsSurveyService service;

    @Autowired
    private ZsWorkShiftService zsWorkservice;

    @Autowired
    private ZsSignService zsSignService;

    @Override
    public PageInfo<ZsWorkRecord> selectPage(List<ZsWorkRecord> list) {
        PageInfo pageInfo = PaginationUtil.startPage(new ZsWorkShift(),()->{
            List<ZsWorkRecord> zsWorkRecords = new ArrayList<>();
        });
        pageInfo.setList(list);
        return pageInfo;
    }

    @Override
    public List<ZsWorkRecord> selectZsWorkRList(ZsWorkRecord entity) {
        return mapper.list(entity);
    }

    @Override
    public Result start(ZsWorkRecord entity, OAuth2Authentication oAuth2Authentication) {
        Wrapper<ZsWorkRecord> wrapper = new EntityWrapper<>();
        wrapper.eq("ws_id",entity.getWsId());
        wrapper.eq("cid_num",entity.getCIdNum());
        if(mapper.selectCount(wrapper)>0){
            return ResultUtil.success("您已提交过了，请勿重复提交");
        };
        Result result = service.sign(entity.getSurveyId(), oAuth2Authentication);
        if(result.getMsg().equals("投递成功")){
            mapper.insert(entity);

        }
        if(result.getMsg().equals("您已经投递过了，无需重复提交") || result.getMsg().equals("您已经投递过了，并且已经审核通过，无需重复提交")){
            result.setMsg("投递成功");
            mapper.insert(entity);
        }
        return result;
    }

    @Override
    public Result updateSignW(ZsWorkRecord entity) {
        mapper.updateById(entity);
        ZsWorkShift zsWorkShift = zsWorkservice.selectById(entity.getWsId());
        ZsSign zsSign = new ZsSign();
        Wrapper<ZsSign> wrapper = new EntityWrapper<>();
        if(zsWorkShift != null){
            zsSign.setResult(entity.getResult());
            wrapper.eq("survey_id",zsWorkShift.getSurveyId());
            wrapper.eq("creator",entity.getCreatorId());
        }

        return ResultUtil.success("修改成功",zsSignService.update(zsSign,wrapper));
    }

    @Override
    public Result selectWorkR(String idNum) {
        Wrapper<ZsWorkRecord> wrapper = new EntityWrapper<>();
        wrapper.eq("cid_num",idNum);
        List<ZsWorkRecord> zsWorkRecords = mapper.selectList(wrapper);
        List<ZsWorkShift> zsWorkShifts = new ArrayList<>();
        Map<Integer, String > indexaAndDate = new HashMap<>();
        int i = 0;
        for (ZsWorkRecord zsWorkRecord : zsWorkRecords) {
            ZsWorkShift zsWorkShift = zsWorkservice.selectById(zsWorkRecord.getWsId());
            zsWorkShifts.add(zsWorkShift);
            indexaAndDate.put(i,zsWorkShift.getStartDate());
            i+=1;
        }
        List<Integer> integers = TimeCompare.dateCompare(indexaAndDate);
        Map<Integer, ZsWorkShift> indexaAndTime = new HashMap<>();
        if(integers.size()<1){
            return ResultUtil.success(ZsAccessState.NORMAL.getName());
        }
        else {
        for (int j = 0; j <integers.size() ; j++) {
            indexaAndTime.put(integers.get(j),zsWorkShifts.get(integers.get(j)));
        }
            List<Integer> timeCompare = TimeCompare.timeCompare(indexaAndTime);
            if(timeCompare.size()<1){
                return ResultUtil.success(ZsAccessState.FAILD.getName());
            }else {
                return ResultUtil.success(ZsAccessState.SUCESS.getName());
            }
        }
    }
}
