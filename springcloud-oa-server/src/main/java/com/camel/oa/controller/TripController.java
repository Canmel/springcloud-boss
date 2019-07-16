package com.camel.oa.controller;


import com.baomidou.mybatisplus.service.IService;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.oa.model.Imperfect;
import com.camel.oa.model.Trip;
import com.camel.oa.service.ImperfectService;
import com.camel.oa.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2019-07-12
 */
@RestController
@RequestMapping("/trip")
public class TripController extends BaseCommonController {
    @Autowired
    private TripService service;

    @Autowired
    private ImperfectService imperfectService;

    @PostMapping
    public Result save(@RequestBody Trip entity) {
        Imperfect imperfect = imperfectService.getByErrand(entity.getImperfectId());
        entity.setImperfectId(imperfect.getId());
        return super.save(entity);
    }

    @Override
    public IService getiService() {
        return service;
    }

    @Override
    public String getMouduleName() {
        return "车船票";
    }
}

