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
import com.camel.survey.model.ZsWork;
import com.camel.survey.service.ZsWorkService;
import com.camel.survey.utils.ApplicationToolsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

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

    @PutMapping
    public Result update(@RequestBody ZsWork entity) {
        if (!ObjectUtils.isEmpty(entity.getId())) {
            ZsWork zsWork = service.selectById(entity.getId());
            if (!ObjectUtils.isEmpty(zsWork)) {
                if (!zsWork.getState().equals(ZsWorkState.APPLYED) || !zsWork.getStatus().equals(ZsStatus.CREATED)) {
                    throw new SourceDataNotValidException("请不要更新已经提交或审核的数据");
                }
                // 设置餐费与作废量
                zsWork.setInvalidNum(entity.getInvalidNum());
                zsWork.setMeals(entity.getMeals());
                zsWork.resetSuccessRate();
                entity.setSuccessRate(zsWork.getSuccessRate());
            }
        }
        return super.update(entity);
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
    public Result pass(@PathVariable Integer id) {
        ZsWork work = service.selectById(id);
        work.setState(ZsWorkState.SUCCESS);
        service.updateById(work);
        return ResultUtil.success("通过成功");
    }

    /**
     * 驳回审核
     * @param id
     */
    @GetMapping("/reject/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DEVOPS')")
    public Result reject(@PathVariable Integer id) {
        ZsWork work = service.selectById(id);
        work.setState(ZsWorkState.FAILD);
        service.updateById(work);
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

