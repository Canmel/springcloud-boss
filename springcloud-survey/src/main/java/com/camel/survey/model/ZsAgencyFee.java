package com.camel.survey.model;

import java.util.Date;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.core.model.ZsAgency;
import com.camel.survey.enums.ZsStatus;
import com.camel.survey.enums.ZsWorkFeeState;
import com.camel.survey.enums.ZsYesOrNo;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author baily
 * @since 2020-09-28
 */
@Data
public class ZsAgencyFee extends BasePaginationEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 介绍人
     */
    private String username;
    /**
     * 介绍人手机
     */
    private String phone;
    /**
     * 介绍人 身份证
     */
    private String idNum;
    /**
     * 介绍费
     */
    private Double salary;
    /**
     * 状态
     */
    @TableLogic
    private ZsStatus status;
    /**
     * 是否提取
     */
    private Integer gain;
    /**
     * 提取时间
     */
    private Date gainTime;
    /**
     * 被介绍人工作记录
     */
    private Integer workId;
    /**
     * 被介绍人工资
     */
    private Double workSalary;

    /**
     * 被介绍人工作时长
     */
    private Double workHours;
    /**
     * 被介绍人
     */
    private String workName;

    /**
     * 审核是否通过
     */
    private ZsWorkFeeState state;

    /**
     * 工作人 身份证
     */
    private String workIdNum;

    public ZsAgencyFee() {
    }

    public ZsAgencyFee(Integer id) {
        this.id = id;
    }

    public ZsAgencyFee(ZsWork zsWork, String username, String phone, String idNum, ZsAgency agency, String workIdNum) {
        this.username = username;
        this.phone = phone;
        this.idNum = idNum;
        if(ObjectUtil.isNotEmpty(agency)) {
            if(agency.getType().equals(1)) {
                this.salary = zsWork.getSalary() * agency.getSvalue();
            }
            if(agency.getType().equals(0)) {
                this.salary = zsWork.getWorkHours() * agency.getSvalue();
            }
        }

        this.workId = zsWork.getId();
        this.workSalary = zsWork.getSalary();
        this.workName = zsWork.getUname();
        this.workHours = zsWork.getWorkHours();
        this.workIdNum = workIdNum;
    }

    public ZsAgencyFee(Integer id, Integer gain) {
        this.id = id;
        this.gain = gain;
        this.gainTime = new Date();
    }
}
