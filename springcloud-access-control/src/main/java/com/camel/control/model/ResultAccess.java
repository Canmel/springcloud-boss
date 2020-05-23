package com.camel.control.model;

import lombok.Data;

@Data
public class ResultAccess {
    //机器状态码，默认为200
    private int code;
    //返回机器消息
    private String msg;
    //指令开门 0:开门  2:不开门
    private String personnelType;
}
