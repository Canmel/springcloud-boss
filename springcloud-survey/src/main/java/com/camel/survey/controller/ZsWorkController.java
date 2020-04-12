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
import com.camel.survey.model.ZsWork;
import com.camel.survey.service.ZsWorkService;
import com.camel.survey.utils.ApplicationToolsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;

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

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Result index(ZsWork entity, OAuth2Authentication oAuth2Authentication) {
        return ResultUtil.success(service.selectPage(entity, oAuth2Authentication));
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
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Result current(String idNum, String uname) {
        Wrapper<ZsWork> zsWorkWrapper = new EntityWrapper<>();
        zsWorkWrapper.eq("uname", uname);
        zsWorkWrapper.eq("id_num", idNum);
        zsWorkWrapper.eq("gain", ZsGain.NORMAL.getCode());
        return ResultUtil.success(service.selectList(zsWorkWrapper));
    }

    @PostMapping("/upload")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Result upLoad(@RequestParam MultipartFile file) {
        service.importExcel(file);
        return ResultUtil.success("上传成功");
    }

    @GetMapping("/current")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Result current(ZsWork entity, OAuth2Authentication oAuth2Authentication) {
        SysUser sysUser = applicationUtils.currentUser();
        if(ObjectUtils.isEmpty(sysUser.getUid()) || ObjectUtils.isEmpty(sysUser.getUsername())) {
            return ResultUtil.error(ResultEnum.NOT_VALID_PARAM.getCode(), "对不起，清先完善身份信息");
        }
        entity.setIdNum(sysUser.getIdNum());
        entity.setUname(sysUser.getUsername());
        return ResultUtil.success(service.selectPage(entity, oAuth2Authentication));
    }

    @Override
    public IService getiService() {
        return service;
    }

    @Override
    public String getMouduleName() {
        return "工作记录";
    }
}

