package com.camel.exceptions;

/**
 @author baily
 */
public class ProcessNotFoundException extends RuntimeException {
    public ProcessNotFoundException() {
        super("未找到相关流程！");
    }
}
