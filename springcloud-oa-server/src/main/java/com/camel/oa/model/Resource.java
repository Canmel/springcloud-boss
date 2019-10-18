package com.camel.oa.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;
import com.camel.core.entity.BaseProcessPaginationEntity;
import com.camel.core.model.SysUser;
import com.camel.oa.enums.ResourceStatus;
import com.camel.oa.enums.ResourceTyies;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ___====-_  _-====___
 * _--^^^#####//      \\#####^^^--_
 * _-^##########// (    ) \\##########^-_
 * -############//  |\^^/|  \\############-
 * _/############//   (@::@)   \\############\_
 * /#############((     \\//     ))#############\
 * -###############\\    (oo)    //###############-
 * -#################\\  / VV \  //#################-
 * -###################\\/      \//###################-
 * _#/|##########/\######(   /\   )######/\##########|\#_
 * |/ |#/\#/\#/\/  \#/\##\  |  |  /##/\#/  \/\#/\#/\#| \|
 * `  |/  V  V  `   V  \#\| |  | |/#/  V   '  V  V  \|  '
 * `   `  `      `   / | |  | | \   '      '  '   '
 * (  | |  | |  )
 * __\ | |  | | /__
 * (vvv(VVV)(VVV)vvv)
 * <资源类>
 *
 * @author baily
 * @date 2019/7/7
 * @since 1.0
 **/
@Data
public class Resource extends BaseProcessPaginationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 资源类型
     */
    @TableField(value = "type")
    private ResourceTyies type;
    /**
     * 资源名称
     */
    private String name;
    /**
     * 地址
     */
    private String address;
    /**
     * 地图位置
     */
    private String mapPosition;
    /**
     * 备注
     */
    private String remark;

    /**
     * 状态
     */
    @TableLogic
    @TableField(value = "status")
    private ResourceStatus status;
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

    @Override
    public String toString() {
        return "Resource{" +
                ", id=" + id +
                ", type=" + type +
                ", name=" + name +
                ", address=" + address +
                ", mapPosition=" + mapPosition +
                ", remark=" + remark +
                ", createdAt=" + createdAt +
                "}";
    }
}
