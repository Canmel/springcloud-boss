package com.camel.oa.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.camel.core.entity.BaseProcessPaginationEntity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 *                       .::::.
 *                     .::::::::.
 *                    :::::::::::
 *                 ..:::::::::::'      报销
 *              '::::::::::::'
 *                .::::::::::
 *           '::::::::::::::..
 *                ..::::::::::::.
 *              ``::::::::::::::::
 *               ::::``:::::::::'        .:::.
 *              ::::'   ':::::'       .::::::::.
 *            .::::'      ::::     .:::::::'::::.
 *           .:::'       :::::  .:::::::::' ':::::.
 *          .::'        :::::.:::::::::'      ':::::.
 *         .::'         ::::::::::::::'         ``::::.
 *     ...:::           ::::::::::::'              ``::.
 *    ```` ':.          ':::::::::'                  ::::..
 *                       '.:::::'                    ':'````..
 * @author baily 
 * @since 2019/7/18
 **/
@Data
public class Reimbursement extends BaseProcessPaginationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 申请名称
     */
    private String name;
    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 创建人
     */
    private Integer creator;

    @TableField(exist = false)
    private String creatorName;

    private Integer status;
    /**
     * 是否删除
     */
    private Integer isDel;
    /**
     * 备注
     */
    private String description;

    public Reimbursement(Integer id) {
        this.id = id;
    }

    public Reimbursement(Integer id, Integer status) {
        this.id = id;
        this.status = status;
    }

    public Reimbursement() {
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
        ", id=" + id +
        ", name=" + name +
        ", amount=" + amount +
        ", creator=" + creator +
        ", status=" + status +
        ", isDel=" + isDel +
        ", description=" + description +
        "}";
    }
}
