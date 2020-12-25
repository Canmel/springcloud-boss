package com.camel.survey.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.camel.core.entity.Result;
import com.camel.core.model.ZsAgency;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.annotation.AuthIgnore;
import com.camel.survey.enums.ZsGain;
import com.camel.survey.enums.ZsWorkFeeState;
import com.camel.survey.enums.ZsWorkState;
import com.camel.survey.enums.ZsYesOrNo;
import com.camel.survey.model.ZsAgencyFee;
import com.camel.survey.model.ZsWork;
import com.camel.survey.service.ZsAgencyFeeService;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import com.camel.core.controller.BaseCommonController;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baily
 * @since 2020-09-28
 */
@RestController
@RequestMapping("/zsAgencyFee")
public class ZsAgencyFeeController extends BaseCommonController {

    @Autowired
    private ZsAgencyFeeService service;

    @GetMapping
    public Result index(ZsAgencyFee entity) {
        return ResultUtil.success(service.selectPage(entity));
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
        Wrapper<ZsAgencyFee> agencyFeeWrapper = new EntityWrapper<>();
        agencyFeeWrapper.eq("username", uname);
        agencyFeeWrapper.eq("id_num", idNum);
        agencyFeeWrapper.eq("gain", ZsGain.NORMAL.getCode());
        agencyFeeWrapper.eq("state", ZsWorkFeeState.SUCCESS);
        return ResultUtil.success(service.selectList(agencyFeeWrapper));
    }

    @GetMapping("/agree/{id}")
    public Result agree(@PathVariable Integer id) {
        ZsAgencyFee fee = new ZsAgencyFee(id);
        fee.setState(ZsWorkFeeState.SUCCESS);
        service.updateById(fee);
        return ResultUtil.success("操作成功");
    }

    @GetMapping("/refuse/{id}")
    public Result refuse(@PathVariable Integer id) {
        ZsAgencyFee fee = new ZsAgencyFee(id);
        fee.setState(ZsWorkFeeState.FAILD);
        service.updateById(fee);
        return ResultUtil.success("操作成功");
    }

    @Override
    public IService getiService() {
        return service;
    }

    @Override
    public String getMouduleName() {
        return "介绍费";
    }
}

