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
 * <流程操作>
 * @author baily
 * @since 1.0
 * @date 2019/8/28
 **/
@RestController
@RequestMapping("process")
public class ProcessController {
    @GetMapping("pass")
    public Result pass(){
        return ResultUtil.success("审核成功");
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
