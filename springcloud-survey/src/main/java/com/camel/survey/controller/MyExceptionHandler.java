package com.camel.survey.controller;

import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.exceptions.SourceDataNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
 * <全局异常处理>
 * @author baily
 * @since 1.0
 * @date 2019/12/14
 **/
@RestController
@ControllerAdvice
public class MyExceptionHandler {
    /**
     * SourceDataNotValidException
     * @param e
     * @return
     */
    @ExceptionHandler(SourceDataNotValidException.class)
    public Result customHandler(SourceDataNotValidException e){
        return ResultUtil.error(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
}
