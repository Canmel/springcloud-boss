package com.camel.survey.model;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.camel.core.entity.BasePaginationEntity;
import lombok.Data;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author baily
 * @since 2020-04-18
 */
@Data
public class ZsCdrinfo extends BasePaginationEntity {

    private static final long serialVersionUID = 1L;

    @TableField(value = "call_uuid")
    private String call_uuid;
    private String uuids;
    @TableField(value = "call_type")
    private String call_type;
    @TableField(value = "caller_num")
    private String caller_num;
    @TableField(value = "callee_num")
    private String callee_num;
    @TableField(value = "start_time")
    private String start_time;
    @TableField(value = "call_lasts_time")
    private String call_lasts_time;
    @TableField(value = "agent_duration")
    private Integer agent_duration;
    @TableField(value = "caller_agent_num")
    private String caller_agent_num;
    @TableField(value = "callee_agent_num")
    private String callee_agent_num;
    @TableField(value = "caller_agent_group_name")
    private String caller_agent_group_name;
    @TableField(value = "record_file")
    private String recordFile;
    private Integer grade;
    private Integer taskid;
    private String taskname;
    @TableField(value = "ivr_dtmf")
    private String ivr_dtmf;


    @Override
    public String toString() {
        return "ZsCdrinfo{" +
                ", callUuid=" + call_uuid +
                ", uuids=" + uuids +
                ", grade=" + grade +
                ", taskid=" + taskid +
                ", taskname=" + taskname +
                "}";
    }
}
