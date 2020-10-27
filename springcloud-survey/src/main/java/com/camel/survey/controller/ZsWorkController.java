package com.camel.survey.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.model.SysUser;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.annotation.AuthIgnore;
import com.camel.survey.enums.ZsGain;
import com.camel.survey.enums.ZsStatus;
import com.camel.survey.enums.ZsWorkState;
import com.camel.survey.exceptions.SourceDataNotValidException;
import com.camel.survey.model.*;
import com.camel.survey.service.*;
import com.camel.survey.utils.ApplicationToolsUtils;
import com.camel.survey.vo.ProjectReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 访员工作记录 前端控制器
 * </p>
 *
 * @author baily
 * @since 2020-03-14
 */
@RestController
@RequestMapping("/zsWork")
public class ZsWorkController extends BaseCommonController {
    @Autowired
    private ZsWorkService service;

    @Autowired
    private ZsAgencyFeeService agencyFeeService;

    @Autowired
    private ApplicationToolsUtils applicationUtils;

    @Autowired
    private ZsSurveyService zsSurveyService;

    @Autowired
    private ZsOtherSurveyService zsOtherSurveyService;

    @Autowired
    private ArgsService argsService;

    @Autowired
    private ZsWorkShiftService workShiftService;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @PutMapping
    public Result update(@RequestBody ZsWork entity) throws UnknownHostException {
        long beginTime = System.currentTimeMillis();
        if (!ObjectUtils.isEmpty(entity.getId())) {
            ZsWork zsWork = service.selectById(entity.getId());
            if (!ObjectUtils.isEmpty(zsWork)) {
                if (!zsWork.getState().equals(ZsWorkState.APPLYED) || !zsWork.getStatus().equals(ZsStatus.CREATED)) {
                    throw new SourceDataNotValidException("请不要更新已经提交或审核的数据");
                }
                // 设置餐费与作废量
                if (!ObjectUtils.isEmpty(entity.getInvalidNum())) {
                    zsWork.setInvalidNum(entity.getInvalidNum());
                    zsWork.setValidNum(zsWork.getSuccessNum() - zsWork.getInvalidNum());
                }
                if (!ObjectUtils.isEmpty(entity.getMeals())) {
                    zsWork.setMeals(entity.getMeals());
                }
                if (!ObjectUtils.isEmpty(entity.getBenchmark())) {
                    zsWork.setBenchmark(entity.getBenchmark());
                }
                zsWork.resetSuccessRate();
                entity.setSuccessRate(zsWork.getSuccessRate());
                if (!ObjectUtils.isEmpty(entity.getAvgNum()) && !entity.getAvgNum().equals(new Double(0))) {
                    entity.setAvgNum(entity.getAvgNum());
                } else {
                    entity.setAvgNum(zsWork.getAvgNum());
                }
                super.update(zsWork);
                long time = System.currentTimeMillis() - beginTime;
                SysUser sysUser = applicationUtils.currentUser();
                String arg = null;
                String num = "";
                if (entity.getInvalidNum() != null) {
                    arg = "作废量";
                    num = entity.getInvalidNum().toString();
                } else {
                    arg = "餐补";
                    num = entity.getMeals().toString();
                }
                String operation = sysUser.getUsername() + " 修改 " + zsWork.getUname() + " 在 " + sdf.format(zsWork.getWorkDate()) + " 关于 " + zsWork.getPname() + " 的 " + arg + " 为 " + num;
                InetAddress ip4 = Inet4Address.getLocalHost();
                List<Object> log = new ArrayList<>();
                log.add(sysUser.getUid());
                log.add(sysUser.getUsername());
                log.add(operation);
                log.add(time);
                log.add(ip4.toString());
                log.add("ZsWorkController.update(..)");
                log.add(entity.toString());
                log.add("工作记录");
                service.addLog(log);
            }
        }
        return ResultUtil.success("修改成功");
    }

    /**
     * 上报记录总列表
     *
     * @param entity
     * @param zsWorkId
     * @param oAuth2Authentication
     * @return
     */
    @GetMapping
    public Result index(ZsWork entity, @RequestParam("zsWorkId[]") String[] zsWorkId, OAuth2Authentication oAuth2Authentication) {
        return ResultUtil.success(service.selectPage(entity, zsWorkId, oAuth2Authentication));
    }

    /**
     * 上报记录
     *
     * @param work
     * @param bindingResult
     * @return
     */
    @PostMapping("/report")
    public Result report(@Valid ZsWork work, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new SourceDataNotValidException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        return ResultUtil.success(service.report(work));
    }

    /**
     * 我的上报记录
     *
     * @param entity
     * @return
     */
    @GetMapping("/me")
    public Result me(ZsWork entity) {
        return ResultUtil.success(service.me(entity));
    }

    /**
     * 查询可提现记录
     *
     * @param idNum
     * @param uname
     * @return
     */
    @GetMapping("/cash")
    @AuthIgnore
    public Result current(String idNum, String uname) {
        Wrapper<ZsWork> zsWorkWrapper = new EntityWrapper<>();
        zsWorkWrapper.eq("uname", uname);
        zsWorkWrapper.eq("id_num", idNum);
        zsWorkWrapper.eq("gain", ZsGain.NORMAL.getCode());
        zsWorkWrapper.eq("state", ZsWorkState.SUCCESS.getValue());
        return ResultUtil.success(service.selectList(zsWorkWrapper));
    }

    /**
     * 上传工作记录
     *
     * @param file
     */
    @PostMapping("/upload")
    public Result upLoad(@RequestParam MultipartFile file) {
        return service.importExcel(file);
    }

    /**
     * 获取当前用户收支查询
     *
     * @param entity
     * @param oAuth2Authentication
     */
    @GetMapping("/current")
    public Result current(ZsWork entity, OAuth2Authentication oAuth2Authentication) {
        SysUser sysUser = applicationUtils.currentUser();
        if (ObjectUtils.isEmpty(sysUser.getUid()) || ObjectUtils.isEmpty(sysUser.getUsername())) {
            return ResultUtil.error(ResultEnum.NOT_VALID_PARAM.getCode(), "对不起，清先完善身份信息");
        }
        entity.setIdNum(sysUser.getIdNum());
        entity.setUname(sysUser.getUsername());
        return ResultUtil.success(service.selectPage(entity, null, oAuth2Authentication));
    }

    @PostMapping("/pass/batch")
    @PreAuthorize("hasAnyRole('ADMIN','DEVOPS')")
    public Result passBatch(String idstr) throws UnknownHostException {
        List<ZsWork> ws = service.selectBatchIds(CollectionUtils.arrayToList(idstr.split(",")));
        for (ZsWork work : ws) {
            work.setState(ZsWorkState.SUCCESS);
            saveNewAgencyFee(work);
        }
        service.updateBatchById(ws);
        return ResultUtil.success("批量审核成功");
    }

    /**
     * 通过审核
     *
     * @param id
     */
    @GetMapping("/pass/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DEVOPS')")
    public Result pass(@PathVariable Integer id) throws UnknownHostException {
        long beginTime = System.currentTimeMillis();
        ZsWork work = service.selectById(id);
        ZsOtherSurvey survey = zsOtherSurveyService.selectById(work.getProjectId());
        if (!ObjectUtils.isEmpty(survey)) {
            if (ObjectUtils.isEmpty(survey.getRatio())) {
                throw new SourceDataNotValidException("该问卷还没有设置难度系数");
            }
            ProjectReport projectReport = service.selectTotalInfoByWork(work);
            if (ObjectUtils.isEmpty(projectReport)) {
                throw new SourceDataNotValidException("暂未设置项目基准数据");
            }
            work.setBenchmark(projectReport.loadBenchmark(survey));
        }
        work.setState(ZsWorkState.SUCCESS);
        // 平均
        work.setAvgNum(work.getAvgNum());
        // 基本工资
        if (ObjectUtils.isEmpty(work.getBaseSalary())) {
            work.setBaseSalary(work.loadBaseSalary());
        }
        // 工资
        work.setExamRatio(work.loadExamRatio());
        work.setRoyalty(work.loadRoyalty());
        work.setInvalidCost(work.loadInvalidCost());
        work.resetSalary();
        service.updateById(work);
        // 保存一条介绍费
        saveNewAgencyFee(work);

        long time = System.currentTimeMillis() - beginTime;
        SysUser sysUser = applicationUtils.currentUser();
        String operation = sysUser.getUsername() + " 通过 " + work.getUname() + " 在 " + sdf.format(work.getWorkDate()) + " 关于 " + work.getPname() + " 的工作记录审核";
        InetAddress ip4 = Inet4Address.getLocalHost();
        List<Object> log = new ArrayList<>();
        log.add(sysUser.getUid());
        log.add(sysUser.getUsername());
        log.add(operation);
        log.add(time);
        log.add(ip4.toString());
        log.add("ZsWorkController.pass(..)");
        log.add(work.toString());
        log.add("工作记录");
        service.addLog(log);
        return ResultUtil.success("通过成功");
    }

    /**
     * 驳回审核
     *
     * @param id
     */
    @GetMapping("/reject/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DEVOPS')")
    public Result reject(@PathVariable Integer id) throws UnknownHostException {
        long beginTime = System.currentTimeMillis();
        ZsWork work = service.selectById(id);
        work.setState(ZsWorkState.FAILD);
        service.updateById(work);
        long time = System.currentTimeMillis() - beginTime;
        SysUser sysUser = applicationUtils.currentUser();
        String operation = sysUser.getUsername() + " 驳回 " + work.getUname() + " 在 " + sdf.format(work.getWorkDate()) + " 关于 " + work.getPname() + " 的工作记录审核";
        InetAddress ip4 = Inet4Address.getLocalHost();
        List<Object> log = new ArrayList<>();
        log.add(sysUser.getUid());
        log.add(sysUser.getUsername());
        log.add(operation);
        log.add(time);
        log.add(ip4.toString());
        log.add("ZsWorkController.reject(..)");
        log.add(work.toString());
        log.add("工作记录");
        service.addLog(log);
        return ResultUtil.success("驳回成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return super.delete(id);
    }

    // TODO
    @GetMapping("/precaculate/{id}")
    public Result pre(@PathVariable Integer id) {
        ZsWork zsWork = service.selectById(id);
        if (zsWork.getState().equals(ZsWorkState.APPLYED)) {
            ProjectReport projectReport = service.selectTotalInfoByWork(zsWork);
            if (ObjectUtils.isEmpty(projectReport) && ObjectUtils.isEmpty(zsWork.getBenchmark())) {
                throw new SourceDataNotValidException("暂未设置项目基准数据");
            }
            ZsOtherSurvey otherSurvey = zsOtherSurveyService.selectById(zsWork.getProjectId());
            // 基准
            if (ObjectUtils.isEmpty(zsWork.getBenchmark())) {
                zsWork.setBenchmark(projectReport.loadBenchmark(otherSurvey));
            }
            // 平均
            zsWork.setAvgNum(zsWork.getAvgNum());
            // 基本工资
            if (ObjectUtils.isEmpty(zsWork.getBaseSalary())) {
                zsWork.setBaseSalary(zsWork.loadBaseSalary());
            }
            // 工资
            zsWork.setExamRatio(zsWork.loadExamRatio());
            zsWork.setRoyalty(zsWork.loadRoyalty());
            zsWork.setInvalidCost(zsWork.loadInvalidCost());
            zsWork.resetSalary();
        }
        return ResultUtil.success(zsWork);
    }

    /**
     * 获取工作记录时间范围
     *
     * @return
     */
    @GetMapping("/selectTimeRange")
    public Result selectTimeRange() {
        return ResultUtil.success("", service.selectTimeRange());
    }

    /**
     * 获取service
     */
    @Override
    public IService getiService() {
        return service;
    }

    /**
     * 获取模块名称
     */
    @Override
    public String getMouduleName() {
        return "工作记录";
    }

    void saveNewAgencyFee(ZsWork zsWork) {
        Wrapper<Args> argsWrapper = new EntityWrapper<>();
        argsWrapper.eq("code", "SUEVRY_AGENCY_PERCENT");
        Args args = argsService.selectOne(argsWrapper);
        if (ObjectUtils.isEmpty(args)) {
            throw new SourceDataNotValidException("SUEVRY_AGENCY_PERCENT 参数未设置");
        }
        Map<String, String> res = service.selectSharer(zsWork.getUname(), zsWork.getIdNum());
        if (!ObjectUtils.isEmpty(res)) {
            ZsAgencyFee agencyFee = new ZsAgencyFee(zsWork, (String) res.get("username"), (String) res.get("phone"), (String) res.get("id_num"), Double.parseDouble(args.getValue()));
            agencyFeeService.insert(agencyFee);
        }
    }
}

