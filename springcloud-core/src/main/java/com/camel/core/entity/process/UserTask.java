package com.camel.core.entity.process;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 @author baily
 */
@Data
public class UserTask {
    /**
     id
     */
    private String id;
    /**
     名称
     */
    private String name;
    /**
     备注
     */
    private String description;
    /**
     是否结束
     */
    private boolean isEnd;
    /**
     用户名，这个任务的操作人
     */
    private String username;
    /**
     是否通过
     */
    private boolean isPass;
    /**
     评论与回复
     */
    private List<Comment> comment;

    /**
     流程实例ID
     */
    private String processInstanceId;

    /**
     流程定义ID
     */
    private String processDefinitionId;

    /**
     * 创建时间
     */
    private Date createdTime;
}
