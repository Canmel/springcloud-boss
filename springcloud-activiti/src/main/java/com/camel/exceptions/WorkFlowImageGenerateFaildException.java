package com.camel.exceptions;

/**
 @author baily
 */
public class WorkFlowImageGenerateFaildException extends RuntimeException {
    public WorkFlowImageGenerateFaildException() {
        super("流程追踪图生成失败");
    }
}
