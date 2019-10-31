package com.camel.util.exception;

import com.camel.util.Status;
import com.camel.util.ToWeb;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by liuruijie on 2016/12/28.
 * 全局异常处理，捕获所有Controller中抛出的异常。
 * @author baily
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 处理自定义的异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(BaseException.class)
	@ResponseBody
	public Object customHandler(BaseException e){
		return ToWeb.buildResult().status(e.getCode()).msg(e.getMessage());
	}

	/**
	 * 其他未处理的异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Object exceptionHandler(Exception e){
		e.printStackTrace();
		return ToWeb.buildResult().status(Status.FAIL).msg("系统错误");
	}
}
