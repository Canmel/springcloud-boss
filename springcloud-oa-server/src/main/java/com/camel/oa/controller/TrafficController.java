package com.camel.oa.controller;


import com.baomidou.mybatisplus.service.IService;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.BaseEntity;
import com.camel.core.entity.Result;
import com.camel.oa.model.Imperfect;
import com.camel.oa.model.Traffic;
import com.camel.oa.service.ImperfectService;
import com.camel.oa.service.TrafficService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2019-10-08
 */
@RestController
@RequestMapping("/traffic")
public class TrafficController extends BaseCommonController {
    @Autowired
    private TrafficService trafficService;

    @Autowired
    private ImperfectService imperfectService;

    @PostMapping
    public Result save(@RequestBody Traffic entity) {
        Imperfect imperfect = imperfectService.getByErrand(entity.getImperfectId());
        entity.setImperfectId(imperfect.getId());
        return super.save(entity);
    }

    @Override
    public IService getiService() {
        return trafficService;
    }

    @Override
    public String getMouduleName() {
        return "市内交通";
    }
}

