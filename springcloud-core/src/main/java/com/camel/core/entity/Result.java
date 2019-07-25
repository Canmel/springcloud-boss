package com.camel.core.entity;

import lombok.Data;

/** @author baily */
@Data
public class Result {
    private Integer code;
    private String msg;
    private Object data;
    private boolean success;

    public Result(Integer code, String msg, Object data, boolean success) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.success = success;
    }

    public Result() {
    }
}
