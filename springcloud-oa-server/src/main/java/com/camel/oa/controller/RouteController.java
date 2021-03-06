package com.camel.oa.controller;


import com.baomidou.mybatisplus.service.IService;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.oa.model.Imperfect;
import com.camel.oa.model.Route;
import com.camel.oa.service.ImperfectService;
import com.camel.oa.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
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
 * <往返路线>
 * @author baily
 * @since 1.0
 * @date 2019/7/18
 **/
@RestController
@RequestMapping("/route")
public class RouteController extends BaseCommonController {

    @Autowired
    private RouteService service;

    @Autowired
    private ImperfectService imperfectService;

    @GetMapping
    public Result index(Route entity) {
        return ResultUtil.success(service.selectPage(entity));
    }

    /**
     * 当这个行程已经补齐了往返路线后，执行更新
     * 否则执行新建
     * @param route
     * @return
     */
    @PostMapping
    public Result save(@RequestBody Route route) {
        route.doSetDays();
        // route.getImperfectId() 实际我放的是errandId;
        Imperfect imperfect = imperfectService.getByErrand(route.getImperfectId());
        route.setImperfectId(imperfect.getId());
        Route r = service.selectOne(new Route(imperfect.getId()));
        if(ObjectUtils.isEmpty(r)) {
            return super.save(route);
        }else{
            route.setId(r.getId());
            return super.update(route);
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return super.delete(id);
    }

    @Override
    public IService getiService() {
        return service;
    }

    @Override
    public String getMouduleName() {
        return "出差线路";
    }
}

