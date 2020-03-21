package com.camel.control.model;

public class Result {
    private String msg;
    private Integer code;

    public Result() {
    }

    public Result(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }
}
