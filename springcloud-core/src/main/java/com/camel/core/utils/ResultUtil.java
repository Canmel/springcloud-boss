package com.camel.core.utils;

import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;

import javax.servlet.http.HttpServletResponse;

/** @author baily */
public class ResultUtil {
    public ResultUtil() {
    }

    public static Result success(Object data) {
        return success(ResultEnum.SUCCESS.getMsg(), data);
    }

    public static Result success(String msg, Object data) {
        return new Result(ResultEnum.SUCCESS.getCode(), msg, data, true);
    }

    public static Result success(String msg) {
        return success(msg, null);
    }

    public static Result success(String msg, boolean success) {
        Result result =  success(msg, null);
        result.setSuccess(success);
        return result;
    }

    public static Result success(ResultEnum resultEnum) {
        return new Result(resultEnum.getCode(), resultEnum.getMsg(), null, true);
    }

    public static Result error(Integer code, String msg) {
        return new Result(code, msg, null, false);
    }

    public static Result error(ResultEnum resultEnum) {
        return error(resultEnum.getCode(), resultEnum.getMsg());
    }

    public static Result notFound() {
        HttpServletResponse response = ApplicationUtils.getHttpServletResponse();
        response.setStatus(ResultEnum.RESOURCESNOTFOUND.getCode());
        return error(ResultEnum.RESOURCESNOTFOUND);
    }

    public static Result notFound(String msg) {
        HttpServletResponse response = ApplicationUtils.getHttpServletResponse();
        response.setStatus(ResultEnum.RESOURCESNOTFOUND.getCode());
        return error(ResultEnum.RESOURCESNOTFOUND.getCode(), msg);
    }

    public static Result processNotFound(String msg) {
        HttpServletResponse response = ApplicationUtils.getHttpServletResponse();
        response.setStatus(ResultEnum.RESOURCESNOTFOUND.getCode());
        return error(ResultEnum.RESOURCESNOTFOUND.getCode(), msg);
    }

    public static Result createSuccess(String entityName) {
        return success("新增" + entityName + "成功");
    }

    public static Result updateSuccess(String entityName) {
        return success("修改" + entityName + "成功");
    }

    public static Result deleteSuccess(String entityName) {
        return success("删除" + entityName + "成功");
    }

    public static Result createError(String entityName) {
        return success("新增" + entityName + "失败");
    }

    public static Result updateError(String entityName) {
        return success("修改" + entityName + "失败");
    }

    public static Result deleteError(String entityName) {
        return success("删除" + entityName + "失败");
    }

    public static Result updateError(String entityName,String remarks ) {
        return success("修改" + entityName + "失败," + remarks);
    }
}
