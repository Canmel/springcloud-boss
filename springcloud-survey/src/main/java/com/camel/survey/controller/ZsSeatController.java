package com.camel.survey.controller;


import com.baomidou.mybatisplus.service.IService;
import com.camel.common.entity.Member;
import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
import com.camel.core.utils.ApplicationUtils;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.model.ZsSeat;
import com.camel.survey.service.ZsSeatService;
import com.camel.survey.utils.ApplicationToolsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import com.camel.core.controller.BaseCommonController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baily
 * @since 2020-02-19
 */
@RestController
@RequestMapping("/zsSeat")
public class ZsSeatController extends BaseCommonController {
    @Autowired
    private ZsSeatService service;

    @Autowired
    private ApplicationToolsUtils applicationUtils;

    @PostMapping
    public Result dis(ZsSeat zsSeat) {
        ZsSeat seat = service.selectByUid(zsSeat.getUid());
        if(ObjectUtils.isEmpty(seat)) {
            return super.save(zsSeat);
        }
        zsSeat.setId(seat.getId());
        return super.update(zsSeat);
    }

    @GetMapping("/current")
    public Result current() {
        SysUser member = applicationUtils.currentUser();
        return ResultUtil.success(service.selectByUid(member.getUid()));
    }

    @Override
    public IService getiService() {
        return service;
    }

    @Override
    public String getMouduleName() {
        return "坐席";
    }
}

