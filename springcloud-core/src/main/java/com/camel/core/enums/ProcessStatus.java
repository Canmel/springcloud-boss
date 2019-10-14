package com.camel.core.enums;

/**
 @author baily
 流程状态 */
public enum ProcessStatus implements BaseEnum {
    /**
     流转中
     */
    PROCESS(1, "流转中"),
    /**
     已结束
     */
    END(0, "已结束"),
    /**
     未开始
     */
    NOPROCESS(2, "未开始");

    /**
     状态码
     */
    private Integer code;
    /**
     状态描述
     */
    private String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    ProcessStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    ProcessStatus() {
    }


    @Override
    public Integer getValue() {
        return getCode();
    }

    public BaseEnum getEnumByValue(Integer value) {
        return null;
    }
}
