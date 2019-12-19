package com.camel.survey.controller;

import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
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
 * <首页>
 * @author baily
 * @since 1.0
 * @date 2019/12/13
 **/
@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private HomeService homeService;
    /**
     * 统计数据
     * @return
     */
    @GetMapping("/total")
    public Result total() {
        return homeService.total();
    }

    /**
     * 调查中的问卷
     * @return
     */
    @GetMapping("collecting")
    public Result surving() {
        return homeService.collecting();
    }

    /**
     * 首页折线图
     * @return
     */
    @GetMapping("/lineChart")
    public Result lineChart(){
        return homeService.lineChart();
    }

    @GetMapping("/pieChart")
    public Result pieChart() {
        return homeService.pieChart();
    }

}
