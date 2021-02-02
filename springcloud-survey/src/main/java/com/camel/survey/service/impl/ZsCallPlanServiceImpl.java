package com.camel.survey.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
import com.camel.core.utils.PaginationUtil;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.enums.CtiResult;
import com.camel.survey.enums.TaskStatus;
import com.camel.survey.exceptions.SourceDataNotValidException;
import com.camel.survey.mapper.ZsCallPlanMapper;
import com.camel.survey.model.ZsCallPlan;
import com.camel.survey.service.ZsCallPlanService;
import com.camel.survey.utils.ApplicationToolsUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

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
        entity.setTaskStatus(TaskStatus.WAITING);
        entity.setTaskId(entity.getName());
        String object = uploadCallPlan(entity);
        SysUser currentUser = applicationToolsUtils.currentUser();
        entity.setCreatorId(currentUser.getUid());
        System.out.println(object);
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
    public String uploadCallPlan(ZsCallPlan entity) {
        String resp = "";
        try {
            JSONObject jsonObject = JSONUtil.createObj();
            jsonObject.set("taskid", entity.getName());
            jsonObject.set("tel", "");
            jsonObject.set("transType", entity.getTransType().getValue().toString());
            jsonObject.set("taskStatus", entity.getTaskStatus().getValue().toString());
            jsonObject.set("pstnNumber", entity.getPstnNumber());
            jsonObject.set("max_concurrent_num", entity.getMaxConcurrentNum().toString());
            jsonObject.set("transName", entity.getTransName());
            String jsonStr = jsonObject.toJSONString(1);
            resp = HttpUtil.createPost("http://" + baseUrl + "/yscrm/20150101/setting/createtask.json").header("Content-Type", "application/json; charset=UTF-8").body(jsonStr, "application/json").execute().body();
            JSONObject respObject = JSONUtil.parseObj(resp);
            String statuscode = respObject.getStr("statuscode");
            if(!statuscode.equals(CtiResult.SUCCESS.getCode())) {
                throw new SourceDataNotValidException(respObject.getStr("body"));
            }
            return respObject.getStr("body");
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            throw new SourceDataNotValidException(e.getMessage());
        }
    }

    @Override
    public void start(Integer id) {
        ZsCallPlan entity = selectById(id);
        String resp = "";
        try {
            JSONObject jsonObject = JSONUtil.createObj();
            jsonObject.set("taskid", entity.getTaskId());
            jsonObject.set("status", "2");
            resp = HttpUtil.createPost("http://" + baseUrl + "/yscrm/20150101/setting/settaskstatus.json").header("Content-Type", "application/json; charset=UTF-8").body(jsonObject.toString(), "application/json").execute().body();
            JSONObject respObject = JSONUtil.parseObj(resp);
            String statuscode = respObject.getStr("statuscode");
            if(!statuscode.equals(CtiResult.SUCCESS.getCode())) {
                throw new SourceDataNotValidException(respObject.getStr("body"));
            }
            entity.setTaskStatus(TaskStatus.STARTED);
            refresh(id);
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            throw new SourceDataNotValidException(e.getMessage());
        }
    }

    @Override
    public void end(Integer id) {
        ZsCallPlan entity = selectById(id);
        String resp = "";
        try {
            JSONObject jsonObject = JSONUtil.createObj();
            jsonObject.set("taskid", entity.getTaskId());
            jsonObject.set("status", "1");
            resp = HttpUtil.createPost("http://" + baseUrl + "/yscrm/20150101/setting/settaskstatus.json").header("Content-Type", "application/json; charset=UTF-8").body(jsonObject.toString(), "application/json").execute().body();
            JSONObject respObject = JSONUtil.parseObj(resp);
            String statuscode = respObject.getStr("statuscode");
            if(!statuscode.equals(CtiResult.SUCCESS.getCode())) {
                throw new SourceDataNotValidException(respObject.getStr("body"));
            }
            entity.setTaskStatus(TaskStatus.STARTED);
            refresh(id);
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            throw new SourceDataNotValidException(e.getMessage());
        }
    }

    @Override
    public void del(Integer id) {

    }

    @Override
    public void uploadNumers(MultipartFile file, Integer id) {
        ZsCallPlan entity = selectById(id);
        String resp = "";
        try {
            JSONObject jsonObject = JSONUtil.createObj();
            jsonObject.set("taskid", entity.getTaskId());
            jsonObject.set("telList", "018357162602");
            resp = HttpUtil.createPost("http://" + baseUrl + "/yscrm/20150101/setting/importtel.json").header("Content-Type", "application/json; charset=UTF-8").body(jsonObject.toString(), "application/json").execute().body();
            JSONObject respObject = JSONUtil.parseObj(resp);
            String statuscode = respObject.getStr("statuscode");
            if(!statuscode.equals(CtiResult.SUCCESS.getCode())) {
                throw new SourceDataNotValidException(respObject.getStr("body"));
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            throw new SourceDataNotValidException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Boolean refresh(Integer id) {
        ZsCallPlan entity = selectById(id);
        String resp = "";
        try {
            JSONObject jsonObject = JSONUtil.createObj();
            jsonObject.set("taskid", entity.getTaskId());
            resp = HttpUtil.createPost("http://" + baseUrl + "/yscrm/20150101/query/gettask.json").header("Content-Type", "application/json; charset=UTF-8").body(jsonObject.toString(), "application/json").execute().body();
            JSONObject respObject = JSONUtil.parseObj(resp);
            String statuscode = respObject.getStr("statuscode");
            if(!statuscode.equals(CtiResult.SUCCESS.getCode())) {
                throw new SourceDataNotValidException(respObject.getStr("body"));
            }
            JSONArray jsonArray = respObject.getJSONArray("body");
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
}
