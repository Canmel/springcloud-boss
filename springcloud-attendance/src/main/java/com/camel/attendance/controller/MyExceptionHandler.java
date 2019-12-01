package com.camel.attendance.controller;

import com.camel.attendance.exceptions.NotSignInTimeException;
import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.utils.ResultUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
 * @date 2019/11/30
 **/
@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(value = NotSignInTimeException.class)
    public Result notSignInHandler(NotSignInTimeException e){
        return ResultUtil.error(ResultEnum.NOT_VALID_PARAM.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = IndexOutOfBoundsException.class)
    public Result indexOutOfBoundsHandler(IndexOutOfBoundsException ex) {
        return ResultUtil.error(ResultEnum.BAD_REQUEST);
    }
}
