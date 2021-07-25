package com.camel.survey.service.impl;

import cn.hutool.core.util.ObjectUtil;
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
import com.camel.survey.utils.ExcelUtil;
import com.camel.survey.utils.HttpUtils;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
        SysUser currentUser = applicationToolsUtils.currentUser();
        entity.setCompanyId(currentUser.getCompanyId());
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
        entity.setCompanyId(currentUser.getCompanyId());
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
        List<String> tels = ExcelUtil.readExcelPhone(file);
        StringBuilder builder = new StringBuilder();
        for (String item : tels) {
            builder.append(item);
            builder.append(",");
        }
        JSONObject jsonObject = JSONUtil.createObj();
        jsonObject.set("taskid", entity.getTaskId());
        jsonObject.set("telList", StringUtils.trimTrailingCharacter(builder.toString(), ','));
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
        ZsCallPlan callPlan = selectById(zsCallPlan.getId());
        if(ObjectUtil.isNotEmpty(callPlan) && StringUtils.isEmpty(callPlan.getTaskId())) {
            throw new SourceDataNotValidException("外呼计划不能为空");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("taskid", callPlan.getTaskId());
        if(ObjectUtil.isNotEmpty(zsCallPlan.getHitRate())) {
            callPlan.setHitRate(zsCallPlan.getHitRate());
        }
        if(ObjectUtil.isNotEmpty(zsCallPlan.getAcwTime())) {
            callPlan.setAcwTime(zsCallPlan.getAcwTime());
        }
        if(ObjectUtil.isNotEmpty(zsCallPlan.getRedialTimes())) {
            callPlan.setRedialTimes(zsCallPlan.getRedialTimes());
        }
        if(ObjectUtil.isNotEmpty(zsCallPlan.getMinRedialInterval())) {
            callPlan.setMinRedialInterval(zsCallPlan.getMinRedialInterval());
        }
        if(ObjectUtil.isNotEmpty(zsCallPlan.getMaxRingTime())) {
            callPlan.setMaxRingTime(zsCallPlan.getMaxRingTime());
        }
        if(ObjectUtil.isNotEmpty(zsCallPlan.getPstnNumber())) {
            callPlan.setPstnNumber(zsCallPlan.getPstnNumber());
        }
        if(ObjectUtil.isNotEmpty(zsCallPlan.getXintf())) {
            callPlan.setXintf(zsCallPlan.getXintf());
        }
        if(ObjectUtil.isNotEmpty(zsCallPlan.getMaxConcurrentNum())) {
            callPlan.setMaxConcurrentNum(zsCallPlan.getMaxConcurrentNum());
        }
        updateById(callPlan);
        jsonObject.set("hitRate", callPlan.getHitRate().getValue().toString());
        jsonObject.set("acwTime", callPlan.getAcwTime().toString());
        jsonObject.set("redialTimes", callPlan.getRedialTimes().toString());
        jsonObject.set("minRedialInterval", callPlan.getMinRedialInterval().getValue().toString());
        jsonObject.set("maxRingTime", callPlan.getMaxRingTime().getValue().toString());
        jsonObject.set("pstnNumber", callPlan.getPstnNumber());
        jsonObject.set("xintf", callPlan.getXintf());
        jsonObject.set("max_concurrent_num", callPlan.getMaxConcurrentNum().toString());
        HttpUtils.post(jsonObject, baseUrl, "/yscrm/20150101/setting/settaskconfig.json");
    }

    @Override
    public Workbook download(Integer id) {
        ZsCallPlan callPlan = selectById(id);
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(callPlan.getName());
        Integer index = 0;
        Row row = sheet.createRow(index++);
        row.createCell(0).setCellValue("号码");
        row.createCell(1).setCellValue("上次呼叫时间");
        row.createCell(2).setCellValue("呼叫结果");
        row.createCell(3).setCellValue("当前状态");
        row.createCell(4).setCellValue("已呼叫次数");
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("taskid", callPlan.getTaskId());
        JSON json = HttpUtils.post(jsonObject, baseUrl, "/yscrm/ 20150101/query/gettaskinfo.json");
        if(ObjectUtil.isNotEmpty(json)) {
            JSONArray jsonArray = (JSONArray) json;
            for (Object obj: jsonArray) {
                Row r = sheet.createRow(index++);
                JSONObject phone = (JSONObject) obj;
                Integer cellIndex = 0;
                r.createCell(cellIndex++).setCellValue(phone.getStr("tel"));
                r.createCell(cellIndex++).setCellValue(phone.getStr("calltime"));
                r.createCell(cellIndex++).setCellValue(phone.getStr("callresult"));
                r.createCell(cellIndex++).setCellValue(phone.getStr("status"));
                r.createCell(cellIndex++).setCellValue(phone.getStr("callcount"));
            }
        }
        System.out.println(json);
        return wb;
    }
}
