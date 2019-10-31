package com.camel.activiti.controller;

import com.camel.core.entity.Result;
import com.camel.activiti.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
 * <Feign>
 * @author baily
 * @since 1.0
 * @date 2019/10/31
 **/
@RestController
public class FeignController {

    @Autowired
    private FeignService feignService;

    /**
     * 查询所有角色
     * @return
     */
    @GetMapping("/role/all")
    public Result allRole(){
        return feignService.allRole();
    }

    /**
     * 查询指定角色的用户
     * @param id
     * @return
     */
    @GetMapping("/user/role/{id}")
    public Result usersByRoleId(@PathVariable Integer id){
        return feignService.usersByRole(id);
    }
}
