package com.camel.activiti.controller;

import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
 * <测试流程>
 * @author baily
 * @since 1.0
 * @date 2019/9/16
 **/
@RestController
@RequestMapping("/process")
public class ProcessController {
    @GetMapping("/pass")
    public String pass(){
        System.out.println("pass");
        return "审核成功";
    }

    @GetMapping("reject")
    public Result reject() {
        return ResultUtil.success("驳回成功");
    }

    @GetMapping("start")
    public Result start() {
        return ResultUtil.success("发起流程成功");
    }
}
