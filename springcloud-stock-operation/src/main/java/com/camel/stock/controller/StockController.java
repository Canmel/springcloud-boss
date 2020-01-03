package com.camel.stock.controller;

import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import org.springframework.jms.annotation.JmsListener;
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
 * <库存>
 * @author baily
 * @since 1.0
 * @date 2020/1/2
 **/
@RestController
public class StockController {
    public static final String QUEUE_NAME = "ActiveMQ.Stock.Topic";

    @JmsListener(destination = QUEUE_NAME)
    public Result push(String msg) {
        System.out.println(msg);
        return ResultUtil.success("推送成功", msg);
    }
}
