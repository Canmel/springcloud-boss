package com.camel.system.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.system.enums.MenuStatus;
import com.camel.system.enums.MenuType;
import com.camel.system.enums.RoleStatus;
import com.camel.system.model.SysMenu;
import com.camel.system.model.SysRole;
import com.camel.system.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
 * <菜单控制器>
 * @author baily
 * @since 1.0
 * @date 2019/7/5
 **/
@RestController
@RequestMapping("/sysMenu")
public class SysMenuController extends BaseCommonController {

    @Autowired
    private SysMenuService service;

    @GetMapping
    public Result index(SysMenu sysMenu) {
        sysMenu.setStatus(MenuStatus.NORMAL.getCode().toString());
        return ResultUtil.success(service.selectPage(sysMenu));
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        return super.details(id);
    }

    @PostMapping
    public Result save(@RequestBody SysMenu sysMenu) {
        return super.save(sysMenu);
    }

    @PutMapping
    public Result update(@RequestBody SysMenu sysMenu) {
        return super.update(sysMenu);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResultUtil.deleteSuccess(getMouduleName());
        } else {
            return ResultUtil.deleteError(getMouduleName());
        }
    }

    @GetMapping("/tops")
    public Result tops(SysMenu sysMenu, Principal principal) {
        return ResultUtil.success(service.tops(principal));
    }

    @GetMapping("/subs")
    public Result subs(SysMenu sysMenu, Principal principal) {
        return ResultUtil.success(service.subs(principal));
    }

    @GetMapping("/typies")
    public Result typies() {
        return ResultUtil.success(MenuType.all());
    }

    @GetMapping("/valid/{name}")
    public Result nameValid(@PathVariable String name, Integer id) {
        return ResultUtil.success(service.exist(name, id));
    }

    @GetMapping("/all/list")
    public Result all(){
        Wrapper<SysMenu> menuWrapper = new EntityWrapper<>();
        menuWrapper.eq("status", MenuStatus.NORMAL.getValue());
        return ResultUtil.success(service.selectList(menuWrapper));
    }

    @Override
    public IService getiService() {
        return service;
    }

    @Override
    public String getMouduleName() {
        return "菜单";
    }
}

