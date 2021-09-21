package com.camel.survey.controller;

import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.model.SysUser;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.service.PstnnumberService;
import com.camel.survey.service.SysUserService;
import com.camel.survey.service.TelProtectionService;
import com.camel.survey.utils.ApplicationToolsUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/pstnnumber")
public class PstnnumberController {
    @Autowired
    private PstnnumberService service;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private TelProtectionService telProtectionService;

    @GetMapping("/all")
    private Result all() {
        return ResultUtil.success(service.all());
    }

    /**
     *  根据项目id和最终客户id 获取对应的 外呼号码
     * @param projectId 项目id
     * @return
     */
    @GetMapping("/myTel")
    private Result getMyTel(Integer projectId){
        Integer uid = ApplicationToolsUtils.getInstance().currentUser().getUid();
        List<Integer> roleIds = sysUserService.getRoleIdsByUserId(uid);
        if(roleIds != null && roleIds.size() > 0 && roleIds.contains(1)){
            return ResultUtil.success(service.all());
        }
        if(StringUtils.isEmpty(projectId.toString())){
            return ResultUtil.error(ResultEnum.BAD_REQUEST.getCode(),"项目id不能为空");
        }
        List<String> list = telProtectionService.getTelListByProId(projectId);
        return ResultUtil.success("查询成功",list);
    }

}
