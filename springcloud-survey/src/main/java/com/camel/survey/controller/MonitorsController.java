package com.camel.survey.controller;

import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.model.ZsMonitor;
import com.camel.survey.model.ZsSign;
import com.camel.survey.service.ZsSeatService;
import com.camel.survey.service.ZsSignService;
import com.camel.survey.utils.ApplicationToolsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/monitor")
public class MonitorsController {

    @Autowired
    private ZsSeatService seatService;

    @Autowired
    private ZsSignService signService;

    @Autowired
    private ApplicationToolsUtils applicationToolsUtils;

    @GetMapping("/all")
    private Result all(ZsMonitor monitor) {
        List<SysUser> users = new ArrayList<>();
        List<ZsSign> zsSigns = signService.allInSurvey(monitor.getSurveyId());
        for (ZsSign zsSign : zsSigns) {
            zsSign.getSurvey();
            zsSign.getSurveyId();
            SysUser user = applicationToolsUtils.getUser(zsSign.getCreatorId());
            if(!ObjectUtils.isEmpty(user)) {
                users.add(user);
            }

        }
        return ResultUtil.success(users);
    }

    @GetMapping("/user")
    private Result monitorByUser(SysUser user) {
        return ResultUtil.success(seatService.selectByUid(user.getUid()));
    }

}
