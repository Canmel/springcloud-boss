package com.camel.attendance.controller;

import com.camel.attendance.exceptions.NotSignInTimeException;
import com.camel.attendance.exceptions.NotSignOutTimeException;
import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.utils.ResultUtil;
import org.springframework.http.HttpStatus;
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
 * @date 2019/11/30
 **/
@ControllerAdvice
@RestController
public class MyExceptionHandler {
    @ExceptionHandler(value = NotSignInTimeException.class)
    public Result notSignInHandler(NotSignInTimeException e, HttpServletResponse response){
        response.setStatus(HttpStatus.OK.value());
        return ResultUtil.success(e.getMessage(), ResultEnum.NOT_VALID_PARAM);
    }

    @ExceptionHandler(value = NotSignOutTimeException.class)
    public Result notSignOutHandler(NotSignOutTimeException e, HttpServletResponse response){
        response.setStatus(HttpStatus.OK.value());
        return ResultUtil.success(e.getMessage(), ResultEnum.NOT_VALID_PARAM);
    }

    @ExceptionHandler(value = IndexOutOfBoundsException.class)
    public Result indexOutOfBoundsHandler(IndexOutOfBoundsException e, HttpServletResponse response) {
        response.setStatus(HttpStatus.OK.value());
        return ResultUtil.success(e.getMessage(), ResultEnum.BAD_REQUEST.getCode());
    }
}
