package com.camel.gateway.controller;

import com.netflix.client.ClientException;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

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
 * <全局异常处理>
 * @author baily
 * @since 1.0
 * @date 2019/12/14
 **/
@Controller
@ControllerAdvice
public class MyExceptionHandler {
    /**
     * ClientException
     * @param e
     * @return
     */
    @ExceptionHandler(ClientException.class)
    public String customHandler(ClientException e) {
        return "clientConnectionException.html";
    }

    /**
     * IllegalStateException
     * @param e
     * @return
     */
    @ExceptionHandler(IllegalStateException.class)
    public String illegalStateException(IllegalStateException e) {
        return "clientConnectionException.html";
    }

    /**
     * ZuulException
     * @param e
     * @return
     */
    @ExceptionHandler(ZuulException.class)
    public String zuulException(ZuulException e, HttpServletResponse response) {
        response.setStatus(200);
        return "clientConnectionException.html";
    }
}
