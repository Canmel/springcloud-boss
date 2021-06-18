package com.camel.survey.service.impl;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
import com.camel.core.utils.PaginationUtil;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.enums.TaskStatus;
import com.camel.survey.exceptions.SourceDataNotValidException;
import com.camel.survey.mapper.ZsCallPlanMapper;
import com.camel.survey.model.ZsCallPlan;
import com.camel.survey.model.ZsPhoneInformation;
import com.camel.survey.service.ZsCallPlanService;
import com.camel.survey.service.ZsPhoneInformationService;
import com.camel.survey.service.ZsSurveyService;
import com.camel.survey.utils.ApplicationToolsUtils;
import com.camel.survey.utils.HttpUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author baily
 * @since 2021-02-01
 */
@Service
public class ZsCallPlanServiceImpl extends ServiceImpl<ZsCallPlanMapper, ZsCallPlan> implements ZsCallPlanService {
    @Value("${cti.baseUrl}")
    public String baseUrl;

    @Autowired
    private ZsCallPlanMapper mapper;

    @Autowired
    private ApplicationToolsUtils applicationToolsUtils;

    @Autowired
    private ZsPhoneInformationService phoneInformationService;

    @Autowired
    private ZsSurveyService surveyService;

    @Override
    public PageInfo<ZsCallPlan> list(ZsCallPlan entity) {
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.list(entity);
        });
        return pageInfo;
    }

    @Override
    @Transactional
    public Result save(ZsCallPlan entity) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        entity.setTaskStatus(TaskStatus.WAITING);
        entity.setTaskName(entity.getName());
        entity.setTaskId(formatter.format(new Date()));
        uploadCallPlan(entity);
        SysUser currentUser = applicationToolsUtils.currentUser();
        entity.setCreatorId(currentUser.getUid());
        if (insert(entity)) {
            return ResultUtil.success("保存成功");
        }
        return ResultUtil.success("保存失败");
    }

    /**
     * 向CTI提交外呼计划
     *
     * @param entity
     * @return
     */
    public void uploadCallPlan(ZsCallPlan entity) {
        JSONObject jsonObject = JSONUtil.createObj();
        jsonObject.set("taskid", entity.getTaskId());
        jsonObject.set("tel", "");
        jsonObject.set("transType", entity.getTransType().getValue().toString());
        jsonObject.set("taskStatus", entity.getTaskStatus().getValue().toString());
        jsonObject.set("pstnNumber", entity.getPstnNumber());
        jsonObject.set("max_concurrent_num", entity.getMaxConcurrentNum().toString());
        jsonObject.set("transName", entity.getTransName());
        jsonObject.set("xintf", entity.getXintf());
//        jsonObject.set("", "http://diaocha.natapp1.cc/");
        HttpUtils.post(jsonObject, baseUrl, "/yscrm/20150101/setting/createtask.json");
    }

    @Override
    public void start(Integer id) {
        ZsCallPlan entity = selectById(id);
        JSONObject jsonObject = JSONUtil.createObj();
        jsonObject.set("taskid", entity.getTaskId());
        jsonObject.set("status", TaskStatus.IMMEDIATELY.getValue().toString());
        HttpUtils.post(jsonObject, baseUrl, "/yscrm/20150101/setting/settaskstatus.json");
        entity.setTaskStatus(TaskStatus.STARTED);
        refresh(id);
    }

    @Override
    public void end(Integer id) {
        ZsCallPlan entity = selectById(id);
        JSONObject jsonObject = JSONUtil.createObj();
        jsonObject.set("taskid", entity.getTaskId());
        jsonObject.set("status", "1");
        HttpUtils.post(jsonObject, baseUrl, "/yscrm/20150101/setting/settaskstatus.json");
        entity.setTaskStatus(TaskStatus.STARTED);
        refresh(id);
    }

    @Override
    public void del(Integer id) {
        ZsCallPlan entity = selectById(id);
        try {
            JSONObject jsonObject = JSONUtil.createObj();
            jsonObject.set("taskid", entity.getTaskId());
            HttpUtils.post(jsonObject, baseUrl, "/yscrm/20150101/setting/deletetask.json");
            deleteById(id);
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            if ("任务标识不存在".equals(e.getMessage())) {
                deleteById(id);
            } else {
                throw new SourceDataNotValidException(e.getMessage());
            }
        }
    }

    @Override
    public void uploadNumers(MultipartFile file, Integer id) {
        ZsCallPlan entity = selectById(id);
        JSONObject jsonObject = JSONUtil.createObj();
        jsonObject.set("taskid", entity.getTaskId());
        jsonObject.set("telList", "018357162602");
        HttpUtils.post(jsonObject, baseUrl, "/yscrm/20150101/setting/importtel.json");
    }

    @Override
    @Transactional
    public Boolean refresh(Integer id) {
        ZsCallPlan entity = selectById(id);
        String resp = "";
        try {
            JSONObject jsonObject = JSONUtil.createObj();
            jsonObject.set("taskid", entity.getTaskId());
            JSON json = HttpUtils.post(jsonObject, baseUrl, "/yscrm/20150101/query/gettask.json");
            JSONArray jsonArray = (JSONArray) json;
            if (jsonArray.size() == 0) {
                throw new SourceDataNotValidException("未获取到相关外呼计划");
            }
            JSONObject object = (JSONObject) jsonArray.get(0);
            System.out.println(object.toString());
            entity.setTotCallsuc(object.getStr("tot_callsuc"));
            entity.setTotFailure(object.getStr("tot_failure"));
            entity.setTotCallsucRate(object.getStr("tot_callsuc_rate"));
            entity.setTotCust(object.getStr("tot_cust"));
            entity.setTotUncall(object.getStr("tot_uncall"));
            entity.setTotUncallRate(object.getStr("tot_uncall_rate"));
            entity.setTotSuccessRate(object.getStr("tot_success_rate"));
            entity.setPstnNumber(object.getStr("called_number"));
            entity.setTotSuccess(object.getStr("tot_success"));
            entity.setTotTocustomer(object.getStr("tot_tocustomer"));
            entity.setTotTocustomerRate(object.getStr("tot_tocustomer_rate"));
            entity.setTaskStatus(TaskStatus.getEnum(object.getStr("task_status")));
            updateById(entity);
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            throw new SourceDataNotValidException(e.getMessage());
        }
    }

    @Override
    public Boolean uploadFromSurvey(ZsCallPlan callPlan) {
        Integer count = phoneInformationService.selectBySurveyIdCount(callPlan.getSurveyId());

        for (int i = 0; i <= Math.ceil(count / 1000.0); i++) {
            List<Object> informations = phoneInformationService.selectBySurveyId(callPlan.getSurveyId(), i);
            StringBuilder builder = new StringBuilder();
            for (Object item : informations) {
                ZsPhoneInformation information = (ZsPhoneInformation) item;
                builder.append(information.getMobile());
                builder.append(",");
            }
            JSONObject jsonObject = JSONUtil.createObj();
            jsonObject.set("taskid", callPlan.getTaskId());
            jsonObject.set("telList", StringUtils.trimTrailingCharacter(builder.toString(), ','));
            HttpUtils.post(jsonObject, baseUrl, "/yscrm/20150101/setting/importtel.json");
        }
        return true;
    }

    @Override
    public ZsCallPlan selectByTaskName(String taskname) {
        ZsCallPlan callPlan = new ZsCallPlan();
        callPlan.setTaskId(taskname);
        return mapper.selectOne(callPlan);
    }

    @Override
    public void updatePlan(ZsCallPlan zsCallPlan) {
        if(StringUtils.isEmpty(zsCallPlan.getTaskId())) {
            throw new SourceDataNotValidException("外呼计划不能为空");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("taskid", zsCallPlan.getTaskId());
        jsonObject.set("hitRate", zsCallPlan.getHitRate().getValue());
        jsonObject.set("acwTime", zsCallPlan.getAcwTime());
        jsonObject.set("redialTimes", zsCallPlan.getRedialTimes());
        jsonObject.set("minRedialInterval", zsCallPlan.getMinRedialInterval());
        jsonObject.set("maxRingTime", zsCallPlan.getMaxRingTime());
        jsonObject.set("pstnNumber", zsCallPlan.getPstnNumber());
        jsonObject.set("xintf", zsCallPlan.getXintf());
        jsonObject.set("max_concurrent_num", zsCallPlan.getMaxConcurrentNum());
        HttpUtils.post(jsonObject, baseUrl, "/yscrm/20150101/setting/importtel.json");
    }
}
