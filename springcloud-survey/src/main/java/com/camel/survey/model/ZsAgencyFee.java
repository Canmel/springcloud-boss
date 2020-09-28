package com.camel.survey.model;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.survey.enums.ZsStatus;
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
     * 被介绍人
     */
    private String workName;

    public ZsAgencyFee() {
    }

    public ZsAgencyFee(ZsWork zsWork, String username, String phone, String idNum) {
        this.username = username;
        this.phone = phone;
        this.idNum = idNum;
        this.salary = zsWork.getSalary() * 0.05;
        this.workId = zsWork.getId();
        this.workSalary = zsWork.getSalary();
        this.workName = zsWork.getUname();
    }

    public ZsAgencyFee(Integer id, Integer gain) {
        this.id = id;
        this.gain = gain;
    }
}
