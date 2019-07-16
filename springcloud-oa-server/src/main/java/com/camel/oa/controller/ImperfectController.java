package com.camel.oa.controller;


import com.baomidou.mybatisplus.service.IService;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.oa.model.Imperfect;
import com.camel.oa.service.ImperfectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

/**
 *
 *                 ___====-_  _-====___
 *           _--^^^#####//      \\#####^^^--_
 *        _-^##########// (    ) \\##########^-_
 *       -############//  |\^^/|  \\############-
 *     _/############//   (@::@)   \\############\_
 *    /#############((     \\//     ))#############\
 *   -###############\\    (oo)    //###############-
 *  -#################\\  / VV \  //#################-
 * -###################\\/      \//###################-
 *_#/|##########/\######(   /\   )######/\##########|\#_
 *|/ |#/\#/\#/\/  \#/\##\  |  |  /##/\#/  \/\#/\#/\#| \|
 *`  |/  V  V  `   V  \#\| |  | |/#/  V   '  V  V  \|  '
 *   `   `  `      `   / | |  | | \   '      '  '   '
 *                    (  | |  | |  )
 *                   __\ | |  | | /__
 *                  (vvv(VVV)(VVV)vvv)
 * <>
 * @author baily
 * @since 1.0
 * @date 2019/7/16
 **/
@RestController
@RequestMapping("/imperfect")
public class ImperfectController extends BaseCommonController {

    @Autowired
    private ImperfectService service;

    @GetMapping("/errand/valid/{errandId}")
    public Result valid(@PathVariable Integer errandId){
        return ResultUtil.success(service.valid(errandId));
    }

    @GetMapping
    public Result index(Imperfect entity) {
        return ResultUtil.success(service.selectPage(entity));
    }

    @GetMapping("/{id}")
    public Result imperfect(@PathVariable Integer id){
        return ResultUtil.success(super.details(id));
    }
    
    @GetMapping("/errand/{id}")
    public Result getByErrand(@PathVariable Integer id) {
        return ResultUtil.success(service.getByErrand(id));
    }

    @GetMapping("/trips/{id}")
    public Result trips(@PathVariable Integer id) {
        return ResultUtil.success(service.trips(id));
    }

    @Override
    public IService getiService() {
        return service;
    }

    @Override
    public String getMouduleName() {
        return "完善行程";
    }
}

