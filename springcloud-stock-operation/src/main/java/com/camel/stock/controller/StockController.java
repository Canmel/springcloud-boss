package com.camel.stock.controller;

import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

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
    public static final String REDUCE_QUEUE_NAME = "ActiveMQ.Stock.Reduce.Topic";

    public static final String ADD_QUEUE_NAME = "ActiveMQ.Stock.Add.Topic";

    @Autowired
    private StockService stockService;

    @JmsListener(destination = REDUCE_QUEUE_NAME)
    public void reduce(HashMap msg) {
        stockService.reduce(msg);
    }

    @JmsListener(destination = ADD_QUEUE_NAME)
    public void add(HashMap msg) {
        stockService.add(msg);
    }
}
