package com.camel.oa.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.core.model.SysUser;
import com.camel.oa.enums.ZsProjectIndustryTypies;
import com.camel.oa.enums.ZsProjectLevels;
import com.camel.oa.enums.ZsProjectStatus;
import com.camel.oa.enums.ZsProjectTypies;
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

    /**
     * 项目等级
     */
    private ZsProjectLevels level;

    /**
     * 负责人
     */
    @TableField(exist = false)
    private SysUser manager;

    @TableField(value = "manager")
    private Integer managerId;
    /**
     * 状态
     */
    @TableLogic
    private ZsProjectStatus status;

    /**
     * 项目所在地
     */
    private String place;

    /**
     * 落地时间
     */
    private Date confirmAt;

    /**
     * 开工时间
     */
    private Date startAt;

    /**
     * 项目类型
     */
    private ZsProjectTypies type;

    /**
     * 产业类别
     */
    private ZsProjectIndustryTypies industryType;
    /**
     * 创建者
     */
    @TableField(value = "creator")
    private Integer creatorId;

    @TableField(exist = false)
    private SysUser creator;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 用地面积
     */
    private Double areaSize;

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
