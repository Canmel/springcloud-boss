package com.camel.oa.controller;

import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.qiniu.common.QiniuException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

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
 * @date 2019/10/12
 **/
@RestController
@ControllerAdvice
public class ExceptionExceptionAdvice {

    /**
     * FileNotFoundException全局处理
     * @param e
     * @return
     */
    @ExceptionHandler(FileNotFoundException.class)
    public Result customHandler(FileNotFoundException e){
		return ResultUtil.error(HttpStatus.BAD_REQUEST.value(), "未找到相关文件");
    }

    /**
     * 七牛资源管理器 异常
     * @param e
     * @return
     */
    @ExceptionHandler(QiniuException.class)
    public Result qiniuExcepption(QiniuException e) {
        System.out.println(e.getMessage());
        return ResultUtil.error(HttpStatus.BAD_REQUEST.value(), "文件管理错误，请联系管理员");
    }
}
