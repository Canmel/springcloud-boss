package com.camel.survey.model;

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

    private String callUuid;
    private String uuids;
    private String callType;
    private String callerNum;
    private String calleeNum;
    private String startTime;
    private String callLastsTime;
    private Integer agentDuration;
    private String callerAgentNum;
    private String calleeAgentNum;
    private String callerAgentGroupName;
    private String recordFile;
    private Integer grade;
    private Integer taskid;
    private String taskname;
    private String ivrDtmf;


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
