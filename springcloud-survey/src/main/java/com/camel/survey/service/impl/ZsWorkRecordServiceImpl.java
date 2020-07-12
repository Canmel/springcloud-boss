package com.camel.survey.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
import com.camel.core.utils.PaginationUtil;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.model.ZsDelivery;
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
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.text.SimpleDateFormat;
import java.util.*;

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
    private ZsWorkShiftService zsWorkShiftservice;

    @Autowired
    private ZsSignService zsSignService;

    @Autowired
    private ApplicationToolsUtils applicationToolsUtils;

    @Override
    public PageInfo<ZsWorkRecord> selectPage(ZsWorkRecord entity) {
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.list(entity);
        });
        List<ZsWorkRecord> workrecords = pageInfo.getList();
        workrecords.forEach(zsWorkRecord -> {
            zsWorkRecord.setCreator(applicationToolsUtils.getUser(zsWorkRecord.getCreatorId()));
            zsWorkRecord.setWorkshift(zsWorkShiftservice.selectById(zsWorkRecord.getWsId()));
        });
        return pageInfo;
    }

    @Override
    public List<ZsWorkRecord> selectZsWorkRListByUid(Integer id) {
        Wrapper<ZsWorkRecord> wrapper = new EntityWrapper<>();
        wrapper.eq("creator",id);
        wrapper.eq("result",1);
        return mapper.selectList(wrapper);
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
        ZsWorkShift zsWorkShift = zsWorkShiftservice.selectById(entity.getWsId());
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
    public Result deleteSingW(ZsWorkRecord entity) {
        SysUser user = applicationToolsUtils.getUser(entity.getCreatorId());
        Wrapper<ZsWorkRecord> wrapper = new EntityWrapper<>();
        wrapper.eq("ws_id",entity.getWsId());
        wrapper.eq("cid_num",user.getIdNum());
        return ResultUtil.success("退出成功",mapper.delete(wrapper));
    }

    @Override
    public Result selectWorkR(String idNum) {
        Wrapper<ZsWorkRecord> wrapper = new EntityWrapper<>();
        wrapper.eq("cid_num",idNum);
        wrapper.eq("result",1);
        List<ZsWorkRecord> zsWorkRecords = mapper.selectList(wrapper);
        if(zsWorkRecords.size()<1){
            return ResultUtil.success("没有预约班次");
        }
        List<ZsWorkShift> zsWorkShifts = new ArrayList<>();
        //便利班次-预约表，拿到用户预约的所有的班次
        for (ZsWorkRecord zsWorkRecord : zsWorkRecords) {
            ZsWorkShift zsWorkShift = zsWorkShiftservice.selectById(zsWorkRecord.getWsId());
            if(!ObjectUtils.isEmpty(zsWorkShift)){
                zsWorkShifts.add(zsWorkShift);
            }
        }
        //当成功预约后，班次被删除，提示信息
        if(zsWorkShifts.size()<1){
            return ResultUtil.success("班次已移除");
        }
        Map<Integer,ZsWorkShift> zsWorkShiftMap = new HashMap<>();
        for (int i = 0; i < zsWorkShifts.size(); i++) {
            zsWorkShiftMap.put(i+1,zsWorkShifts.get(i));
        }
        Map<Integer, ZsWorkShift> shiftMap = dateCompare(zsWorkShiftMap);
        if(shiftMap.get(0).getZsAccessMsg().equals("日期校验通过")){
            Map<Integer, String> stringMap = timeCompare(shiftMap);
            if(stringMap.get(0).equals("认证成功")){
                return ResultUtil.success("认证成功");
            }else if(stringMap.get(0).equals("班次时间尚未开始")){
                return ResultUtil.success("班次时间尚未开始");
            }else{
                return ResultUtil.success("班次已超时");
            }
        } else if(shiftMap.get(0).getZsAccessMsg().equals("班次日期尚未开始")){
            return ResultUtil.success("班次日期尚未开始");
        } else{
            return ResultUtil.success("班次已过期");
        }
    }
    private Map<Integer,ZsWorkShift> dateCompare(Map<Integer,ZsWorkShift> zsWorkShiftMap){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String nowDate = dateFormat.format(now);
        ZsWorkShift workShift = new ZsWorkShift();
        int mapSize = zsWorkShiftMap.size();
        for (int i = 0; i <mapSize; i++) {
            //比较日期
            long l = Long.valueOf(zsWorkShiftMap.get(i+1).getStartDate().replaceAll("-", "")) - Long.parseLong(nowDate.replaceAll("-", ""));
            if(l==0){
                workShift.setZsAccessMsg("日期校验通过");
                zsWorkShiftMap.put(0,workShift);
            }else if(l>0){
                zsWorkShiftMap.remove(i+1);
                if(zsWorkShiftMap.get(0) == null) {
                    workShift.setZsAccessMsg("班次日期尚未开始");
                    zsWorkShiftMap.put(0,workShift);
                }
                if(!"日期校验通过".equals(zsWorkShiftMap.get(0).getZsAccessMsg())){
                    workShift.setZsAccessMsg("班次日期尚未开始");
                    zsWorkShiftMap.put(0,workShift);
                }
            }else{
                zsWorkShiftMap.remove(i+1);
                if(zsWorkShiftMap.get(0) == null){
                    workShift.setZsAccessMsg("班次已过期");
                    zsWorkShiftMap.put(0,workShift);
                }else if(zsWorkShiftMap.get(0) == null&&!"日期校验通过".equals(zsWorkShiftMap.get(0).getZsAccessMsg()) && !"班次日期尚未开始".equals(zsWorkShiftMap.get(0).getZsAccessMsg())){
                    workShift.setZsAccessMsg("班次已过期");
                    zsWorkShiftMap.put(0,workShift);
                }
            }
        }
        return zsWorkShiftMap;
    }
    private Map<Integer,String> timeCompare(Map<Integer,ZsWorkShift> zsWorkShiftMap){
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        Date now = new Date();
        long time = 30*60*1000;//30分钟
        String format = timeFormat.format(now);
        Map<Integer,String> map = new HashMap<>();
        for (Map.Entry<Integer,ZsWorkShift> zsWorkShift:zsWorkShiftMap.entrySet()) {

            try {
                //比较时间
                Date nowtime =  timeFormat.parse(format);
                Date compareTimeS = timeFormat.parse(zsWorkShift.getValue().getStartTime()+":00");
                Date compareTimeE = timeFormat.parse(zsWorkShift.getValue().getEndTime()+":00");
                //拿当前时间和开始时间作比较
                if(nowtime.getTime()<compareTimeS.getTime()){
                    Date sumTime = new Date(nowtime.getTime()+time);
                    //当前时间小于开始时间，则当前时间加30分钟，继续比较
                    if(sumTime.getTime()<compareTimeS.getTime()){
                        map.put(0,"班次时间尚未开始");
                    }else{
                        map.put(0,"认证成功");
                        break;
                    }
                }else{
                    //当前时间大于开始时间，则当前时间和结束时间比较
                    if(nowtime.getTime()>compareTimeE.getTime()){
                        if (zsWorkShiftMap.get(0) == null){
                            map.put(0,"班次时间已经结束");
                        }else if(!"班次时间尚未开始".equals(map.get(0))){
                            map.put(0,"班次已超时");
                        }
                    }else{
                        map.put(0,"认证成功");
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }

}
