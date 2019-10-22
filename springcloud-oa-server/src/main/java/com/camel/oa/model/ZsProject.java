package com.camel.oa.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.core.model.SysUser;
import com.camel.oa.enums.ZsProjectStatus;
import lombok.Data;

import java.io.Serializable;

/**
 * .::::.
 * .::::::::.
 * :::::::::::
 * ..:::::::::::'
 * '::::::::::::'
 * .::::::::::
 * '::::::::::::::..
 * ..::::::::::::.
 * ``::::::::::::::::
 * ::::``:::::::::'        .:::.
 * ::::'   ':::::'       .::::::::.
 * .::::'      ::::     .:::::::'::::.
 * .:::'       :::::  .:::::::::' ':::::.
 * .::'        :::::.:::::::::'      ':::::.
 * .::'         ::::::::::::::'         ``::::.
 * ...:::           ::::::::::::'              ``::.
 * ```` ':.          ':::::::::'                  ::::..
 * '.:::::'                    ':'````..
 * <智慧招商项目 服务实现类>
 *
 * @author baily
 * @date 2019-10-22
 * @since 1.0
 **/
@Data
public class ZsProject extends BasePaginationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 项目编号
     */
    private String code;
    /**
     * 金额
     */
    private Double amount;
    /**
     * 业务
     */
    private String business;

    private Integer manager;
    /**
     * 状态
     */
    @TableLogic
    private ZsProjectStatus status;
    /**
     * 创建者
     */
    @TableField(value = "creator")
    private Integer creatorId;

    @TableField(exist = false)
    private SysUser creator;

    private Date createdAt;

    @Override
    public String toString() {
        return "ZsProject{" +
                ", id=" + id +
                ", name=" + name +
                ", code=" + code +
                ", amount=" + amount +
                ", business=" + business +
                ", manager=" + manager +
                ", status=" + status +
                ", creator=" + creator +
                ", createdAt=" + createdAt +
                "}";
    }
}
