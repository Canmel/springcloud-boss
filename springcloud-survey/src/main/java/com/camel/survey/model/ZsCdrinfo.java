package com.camel.survey.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.camel.core.entity.BasePaginationEntity;
import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

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
    @TableField("record_file")
    private String recordFile;
    private String grade;
    private String taskid;
    private String taskname;
    private String ivr_dtmf;
    private Integer uid;
    private Integer surveyId;


    @Override
    public String toString() {
        return "ZsCdrinfo{" +
                "id='" + id + '\'' +
                ", call_uuid='" + call_uuid + '\'' +
                ", uuids='" + uuids + '\'' +
                ", call_type='" + call_type + '\'' +
                ", caller_num='" + caller_num + '\'' +
                ", callee_num='" + callee_num + '\'' +
                ", start_time='" + start_time + '\'' +
                ", call_lasts_time='" + call_lasts_time + '\'' +
                ", agent_duration=" + agent_duration +
                ", caller_agent_num='" + caller_agent_num + '\'' +
                ", callee_agent_num='" + callee_agent_num + '\'' +
                ", caller_agent_group_name='" + caller_agent_group_name + '\'' +
                ", recordFile='" + recordFile + '\'' +
                ", grade='" + grade + '\'' +
                ", taskid='" + taskid + '\'' +
                ", taskname='" + taskname + '\'' +
                ", ivr_dtmf='" + ivr_dtmf + '\'' +
                ", uid=" + uid +
                ", surveyId=" + surveyId +
                '}';
    }

    public String loadRecordFileName() {
        SimpleDateFormat full = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat hhmmss = new SimpleDateFormat("HHmmss");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getCaller_agent_num());
        stringBuilder.append("_");
        stringBuilder.append(this.getCallee_num());
        stringBuilder.append("_");
        try {
            Date date = full.parse(this.getStart_time());
            stringBuilder.append(yyyyMMdd.format(date));
            stringBuilder.append("_");
            stringBuilder.append(hhmmss.format(date));
            stringBuilder.append("_");
            stringBuilder.append(UUID.randomUUID());
            return stringBuilder.toString();
        }catch (ParseException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
