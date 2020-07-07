package com.camel.survey.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.model.SysLog;
import com.camel.core.model.SysUser;
import com.camel.core.utils.PaginationUtil;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.enums.ZsStatus;
import com.camel.survey.enums.ZsWorkState;
import com.camel.survey.exceptions.ExcelImportException;
import com.camel.survey.exceptions.SourceDataNotValidException;
import com.camel.survey.mapper.ZsSurveyMapper;
import com.camel.survey.mapper.ZsWorkMapper;
import com.camel.survey.model.ZsSurvey;
import com.camel.survey.model.ZsWork;
import com.camel.survey.service.ZsSurveyService;
import com.camel.survey.service.ZsWorkService;
import com.camel.survey.utils.ApplicationToolsUtils;
import com.camel.survey.utils.ExcelUtil;
import com.camel.survey.vo.ProjectReport;
import com.github.pagehelper.PageInfo;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 访员工作记录 服务实现类
 * </p>
 *
 * @author baily
 * @since 2020-03-14
 */
@Service
public class ZsWorkServiceImpl extends ServiceImpl<ZsWorkMapper, ZsWork> implements ZsWorkService {
    @Autowired
    private ZsWorkMapper mapper;

    @Autowired
    private ZsSurveyMapper surveyMapper;

    @Autowired
    private ZsSurveyService zsSurveyService;

    @Autowired
    private ApplicationToolsUtils applicationToolsUtils;

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Override
    public Result importExcel(MultipartFile file) {
        try {
            List<ZsWork> works = ExcelUtil.readExcelObject(file, ZsWork.class);
            List<ZsSurvey> zsSurveyList = surveyMapper.all();
            for (ZsWork work : works) {
                ZsSurvey zsSurvey = zsSurveyService.getByNameFromList(work.getPname(), zsSurveyList);
                if (!ObjectUtils.isEmpty(zsSurvey)) {
                    work.setProjectId(zsSurvey.getId());
                }
            }

            if (insertBatch(works)) {
                return ResultUtil.success("数据导入成功");
            }
            return ResultUtil.error(ResultEnum.NOT_VALID_PARAM.getCode(), "导入失败");
        } catch (ExcelImportException e) {
            return ResultUtil.error(ResultEnum.NOT_VALID_PARAM.getCode(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error(ResultEnum.NOT_VALID_PARAM.getCode(), "导入时发生错误");
        }
    }

    @Override
    public PageInfo<ZsWork> selectPage(ZsWork entity, String[] ids, OAuth2Authentication oAuth2Authentication) {
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            if (!ObjectUtils.isEmpty(ids) && ids[0].equals("0")) {
                mapper.list(entity, null);
            } else {
                mapper.list(entity, ids);
            }
        });
        return pageInfo;
    }

    /**
     * 根据当日和地点 删除之前导入的数据
     *
     * @return
     */
    private boolean deleteByPlaceAndDate(List<ZsWork> works) {
        if (CollectionUtils.isEmpty(works)) {
            return true;
        }
        Wrapper<ZsWork> workWrapper = new EntityWrapper<>();
        SimpleDateFormat sf = new SimpleDateFormat("YYYY-MM-dd");
        workWrapper.eq("place", works.get(0).getPlace());
        workWrapper.eq("work_date", sf.format(works.get(0).getWorkDate()));
        if (mapper.selectCount(workWrapper) > 0) {
            return mapper.delete(workWrapper) > 0;
        }
        return true;
    }


    @Override
    public Result report(ZsWork zsWork) {
        SysUser currentUser = applicationToolsUtils.currentUser();
        zsWork.buildNecessaryAttribute(zsSurveyService, currentUser);
        if(insert(zsWork)) {
            return ResultUtil.success("保存工作记录成功");
        }
        return ResultUtil.error(ResultEnum.NOT_VALID_PARAM.getCode(), "保存工作记录失败");
    }

    @Override
    public PageInfo me(ZsWork entity) {
        SysUser me = applicationToolsUtils.currentUser();
        entity.setUid(me.getUid());
        return selectPage(entity, null, null);
    }

    @Override
    public ProjectReport projectReport(Integer proId) {
        return mapper.projectReport(proId);
    }

    @Override
    public void updateInvalidNumOrMeals(ZsWork entity) throws UnknownHostException {
        long beginTime = System.currentTimeMillis();
        if(!ObjectUtils.isEmpty(entity.getId())) {
            ZsWork zsWork = selectById(entity.getId());
            if(!ObjectUtils.isEmpty(zsWork)) {
                if(!zsWork.getState().equals(ZsWorkState.APPLYED) || !zsWork.getStatus().equals(ZsStatus.CREATED)) {
                    throw new SourceDataNotValidException("请不要更新已经提交或审核的数据");
                }
            }
            updateById(entity);
            SysUser sysUser = applicationToolsUtils.currentUser();
            String arg=null;
            String num ="";
            if(entity.getInvalidNum()!=null){
                arg="作废量";
                num=entity.getInvalidNum().toString();
            }
            else{
                arg="餐补";
                num=entity.getMeals().toString();
            }
            long time = System.currentTimeMillis()-beginTime;
            String operation =sysUser.getUsername()+"修改"+zsWork.getUname()+"在"+zsWork.getWorkDate()+"关于"+zsWork.getPname()+"的"+arg+"为"+num;
            InetAddress ip4 = Inet4Address.getLocalHost();
            List<Object> log = new ArrayList<>();
            log.add(sysUser.getUid());
            log.add(sysUser.getUsername());
            log.add(operation);
            log.add(time);
            log.add(ip4.toString());
            log.add("ZsWorkController.updateInvalidNumOrMeals(..)");
            log.add(entity.toString());
            log.add("工作记录");
            addLog(log);
        }
    }

    public void addLog(List<Object> log) {
        JSONObject json = new JSONObject();
        json.put("log", log);
        this.jmsMessagingTemplate.convertAndSend(new ActiveMQTopic("ActiveMQ.Log.Add.Topic"), json);
    }
}
