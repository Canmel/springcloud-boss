package com.camel.oa.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.core.model.SysUser;
import com.camel.oa.enums.ZsGroundStatus;
import com.camel.oa.enums.ZsYesOrNo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 *                 ___====-_  _-====___
 *           _--^^^#####//      \\#####^^^--_
 *        _-^##########// (    ) \\##########^-_
 *       -############//  |\^^/|  \\############-
 *     _/############//   (@::@)   \\############\_
 *    /#############((     \\//     ))#############\
 *   -###############\\    (oo)    //###############-
 *  -#################\\  / VV \  //#################-
 * -###################\\/      \//###################-
 *_#/|##########/\######(   /\   )######/\##########|\#_
 *|/ |#/\#/\#/\/  \#/\##\  |  |  /##/\#/  \/\#/\#/\#| \|
 *`  |/  V  V  `   V  \#\| |  | |/#/  V   '  V  V  \|  '
 *   `   `  `      `   / | |  | | \   '      '  '   '
 *                    (  | |  | |  )
 *                   __\ | |  | | /__
 *                  (vvv(VVV)(VVV)vvv)
 * <地块信息>
 * @author baily
 * @since 1.0
 * @date 2019/10/30
 **/
@Data
public class ZsGround extends BaseOaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 地块名称
     */
    private String name;
    /**
     * 所属区域
     */
    private String areaName;
    /**
     * 是否可租
     */
    @TableField(value = "is_rentable")
    private ZsYesOrNo rentable;
    /**
     * 是否可售
     */
    @TableField(value = "is_saleable")
    private ZsYesOrNo saleable;
    /**
     * 行业
     */
    @TableField(value = "industry")
    private Integer industryId;

    @TableField(exist = false)
    private ZsIndustry industry;
    /**
     * 周边情况
     */
    private String periphery;
    /**
     * 合作方式
     */
    private String cooperation;
    /**
     * 状态
     */
    @TableLogic
    private ZsGroundStatus status;
    /**
     * 创建时间
     */
    private Date createdAt;

    public ZsGround() {
    }

    @Override
    public String toString() {
        return "ZsGround{" +
                ", id=" + id +
                ", name=" + name +
                ", areaName=" + areaName +
                ", industryId=" + industryId +
                ", periphery=" + periphery +
                ", cooperation=" + cooperation +
                ", status=" + status +
                ", createdAt=" + createdAt +
                "}";
    }
}
