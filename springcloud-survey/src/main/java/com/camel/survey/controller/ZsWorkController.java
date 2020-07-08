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
import com.camel.survey.enums.ZsYesOrNo;
import com.camel.survey.exceptions.SourceDataNotValidException;
import com.camel.survey.model.ZsSeat;
import com.camel.survey.model.ZsSurvey;
import com.camel.survey.model.ZsWork;
import com.camel.survey.service.ZsSurveyService;
import com.camel.survey.service.ZsWorkService;
import com.camel.survey.utils.ApplicationToolsUtils;
import com.camel.survey.vo.ProjectReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
    private ApplicationToolsUtils applicationUtils;

    @Autowired
    private ZsSurveyService zsSurveyService;

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
                if(!ObjectUtils.isEmpty(entity.getInvalidNum())) {
                    zsWork.setInvalidNum(entity.getInvalidNum());
                }
                if(!ObjectUtils.isEmpty(entity.getMeals())) {
                    zsWork.setMeals(entity.getMeals());
                }
                if(!ObjectUtils.isEmpty(entity.getBenchmark())) {
                    zsWork.setBenchmark(entity.getBenchmark());
                }
                zsWork.resetSuccessRate();
                entity.setSuccessRate(zsWork.getSuccessRate());
                if(!ObjectUtils.isEmpty(entity.getAvgNum()) && !entity.getAvgNum().equals(new Double(0))) {
                    entity.setAvgNum(entity.getAvgNum());
                }else {
                    entity.setAvgNum(zsWork.getAvgNum());
                }
                super.update(entity);
                long time = System.currentTimeMillis()-beginTime;
                SysUser sysUser = applicationUtils.currentUser();
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
                String operation =sysUser.getUsername()+" 修改 "+zsWork.getUname()+" 在 "+sdf.format(zsWork.getWorkDate())+" 关于 "+zsWork.getPname()+" 的 "+arg+" 为 "+num;
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
     * @param entity
     * @return
     */
    @GetMapping("/me")
    public Result me(ZsWork entity) {
        return ResultUtil.success(service.me(entity));
    }

    /**
     *
     * 查询可提现记录
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
     * @param file
     */
    @PostMapping("/upload")
    public Result upLoad(@RequestParam MultipartFile file) {
        service.importExcel(file);
        return ResultUtil.success("上传成功");
    }

    /**
     * 获取当前用户收支查询
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

    /**
     * 通过审核
     * @param id
     */
    @GetMapping("/pass/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DEVOPS')")
    public Result pass(@PathVariable Integer id) throws UnknownHostException {
        long beginTime = System.currentTimeMillis();
        ZsWork work = service.selectById(id);
        work.setState(ZsWorkState.SUCCESS);
        ProjectReport projectReport = service.projectReport(work.getProjectId());
        work.setBenchmark(projectReport.getBenchmark());
        work.setBaseSalary(projectReport.getBaseSalary(work));
        work.setInvalidCost(work.loadInvalidCost());
        work.resetSalary();
        work.setAvgNum(projectReport.getAvgNum());
        service.updateById(work);
        long time = System.currentTimeMillis()-beginTime;
        SysUser sysUser = applicationUtils.currentUser();
        String operation =sysUser.getUsername()+" 通过 "+work.getUname()+" 在 "+sdf.format(work.getWorkDate())+" 关于 "+work.getPname()+" 的工作记录审核";
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
     * @param id
     */
    @GetMapping("/reject/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DEVOPS')")
    public Result reject(@PathVariable Integer id) throws UnknownHostException {
        long beginTime = System.currentTimeMillis();
        ZsWork work = service.selectById(id);
        work.setState(ZsWorkState.FAILD);
        service.updateById(work);
        long time = System.currentTimeMillis()-beginTime;
        SysUser sysUser = applicationUtils.currentUser();
        String operation =sysUser.getUsername()+" 驳回 "+work.getUname()+" 在 "+sdf.format(work.getWorkDate())+" 关于 "+work.getPname()+" 的工作记录审核";
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
        if(zsWork.getState().equals(ZsWorkState.APPLYED)) {
            ProjectReport projectReport = service.projectReport(zsWork.getProjectId());
            // 基准
            if(ObjectUtils.isEmpty(zsWork.getBenchmark())) {
                zsWork.setBenchmark(projectReport.getBenchmark());
            }
            // 平均
            zsWork.setAvgNum(zsWork.getAvgNum());
            // 基本工资
            if(ObjectUtils.isEmpty(zsWork.getBaseSalary())) {
                zsWork.setBaseSalary(projectReport.getBaseSalary(zsWork));
            }
            // 工资
            zsWork.resetSalary();
            zsWork.setInvalidCost(zsWork.loadInvalidCost());
        }
        return ResultUtil.success(zsWork);
    }

    // TODO
    @GetMapping("/caculate/{id}")
    public Result caculate(@PathVariable Integer id) {
        ZsWork zsWork = service.selectById(id);

        // 基准
        zsWork.setBenchmark(0.0);
        // 平均
        zsWork.setAvgNum(0.0);
        // 工资
        zsWork.setSalary(0.0);
        return ResultUtil.success(zsWork);
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
}

