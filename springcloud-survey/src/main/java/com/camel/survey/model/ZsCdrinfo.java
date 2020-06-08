package com.camel.survey.model;

import com.baomidou.mybatisplus.annotations.TableId;
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

    @TableId(value = "id")
    private String id;
    private String call_uuid;
    private String uuids;
    private String call_type;
    private String caller_num;
    private String callee_num;
    private String start_time;
    private String call_lasts_time;
    private Integer agent_duration;
    private String caller_agent_num;
    private String callee_agent_num;
    private String caller_agent_group_name;
    private String record_file;
    private String grade;
    private String taskid;
    private String taskname;
    private String ivr_dtmf;


    @Override
    public String toString() {
        return "ZsCdrinfo{" +
                ", uuids=" + uuids +
                ", grade=" + grade +
                ", taskid=" + taskid +
                ", taskname=" + taskname +
                "}";
    }
}
