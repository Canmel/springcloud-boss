package com.camel.survey.model;

import java.util.Date;
import com.camel.core.entity.BasePaginationEntity;

/**
 * <p>
 * 
 * </p>
 *
 * @author baily
 * @since 2020-04-18
 */
public class ZsCdrinfo extends BasePaginationEntity {

    private static final long serialVersionUID = 1L;

    private String callUuid;
    private String uuids;
    private String callType;
    private String callerNum;
    private String calleeNum;
    private Date startTime;
    private Integer callLastsTime;
    private Integer agentDuration;
    private String callerAgentNum;
    private String calleeAgentNum;
    private String callerAgentGr;
    private String oupName;
    private String recordFile;
    private String legs;
    private Integer grade;
    private Integer taskid;
    private String taskname;
    private String ivrDtmf;


    public String getCallUuid() {
        return callUuid;
    }

    public void setCallUuid(String callUuid) {
        this.callUuid = callUuid;
    }

    public String getUuids() {
        return uuids;
    }

    public void setUuids(String uuids) {
        this.uuids = uuids;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getCallerNum() {
        return callerNum;
    }

    public void setCallerNum(String callerNum) {
        this.callerNum = callerNum;
    }

    public String getCalleeNum() {
        return calleeNum;
    }

    public void setCalleeNum(String calleeNum) {
        this.calleeNum = calleeNum;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getCallLastsTime() {
        return callLastsTime;
    }

    public void setCallLastsTime(Integer callLastsTime) {
        this.callLastsTime = callLastsTime;
    }

    public Integer getAgentDuration() {
        return agentDuration;
    }

    public void setAgentDuration(Integer agentDuration) {
        this.agentDuration = agentDuration;
    }

    public String getCallerAgentNum() {
        return callerAgentNum;
    }

    public void setCallerAgentNum(String callerAgentNum) {
        this.callerAgentNum = callerAgentNum;
    }

    public String getCalleeAgentNum() {
        return calleeAgentNum;
    }

    public void setCalleeAgentNum(String calleeAgentNum) {
        this.calleeAgentNum = calleeAgentNum;
    }

    public String getCallerAgentGr() {
        return callerAgentGr;
    }

    public void setCallerAgentGr(String callerAgentGr) {
        this.callerAgentGr = callerAgentGr;
    }

    public String getOupName() {
        return oupName;
    }

    public void setOupName(String oupName) {
        this.oupName = oupName;
    }

    public String getRecordFile() {
        return recordFile;
    }

    public void setRecordFile(String recordFile) {
        this.recordFile = recordFile;
    }

    public String getLegs() {
        return legs;
    }

    public void setLegs(String legs) {
        this.legs = legs;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getTaskid() {
        return taskid;
    }

    public void setTaskid(Integer taskid) {
        this.taskid = taskid;
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public String getIvrDtmf() {
        return ivrDtmf;
    }

    public void setIvrDtmf(String ivrDtmf) {
        this.ivrDtmf = ivrDtmf;
    }

    @Override
    public String toString() {
        return "ZsCdrinfo{" +
        ", callUuid=" + callUuid +
        ", uuids=" + uuids +
        ", callType=" + callType +
        ", callerNum=" + callerNum +
        ", calleeNum=" + calleeNum +
        ", startTime=" + startTime +
        ", callLastsTime=" + callLastsTime +
        ", agentDuration=" + agentDuration +
        ", callerAgentNum=" + callerAgentNum +
        ", calleeAgentNum=" + calleeAgentNum +
        ", callerAgentGr=" + callerAgentGr +
        ", oupName=" + oupName +
        ", recordFile=" + recordFile +
        ", legs=" + legs +
        ", grade=" + grade +
        ", taskid=" + taskid +
        ", taskname=" + taskname +
        ", ivrDtmf=" + ivrDtmf +
        "}";
    }
}
