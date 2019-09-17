package com.camel.system.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.camel.core.entity.BasePaginationEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 *                       .::::.
 *                     .::::::::.
 *                    :::::::::::
 *                 ..:::::::::::'      角色模型
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
 * @since 2019/7/4
 **/
@Data
public class SysRole extends BasePaginationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID 主键
     */
    @TableId(value = "role_id", type = IdType.AUTO)
    private Integer roleId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色标识
     */
    private String roleSign;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 更新时间
     */
    private Date gmtModifiled;
    /**
     * 状态(0：已删除，1：正常)
     */
    private String status;

    @TableField(exist = false)
    private List menuIds;

    @TableField(exist = false)
    private List<SysMenu> menus;


    public SysRole() {
    }

    public SysRole(Integer roleId, String status) {
        this.roleId = roleId;
        this.status = status;
    }

    @Override
    public String toString() {
        return "SysRole{" +
                ", roleId=" + roleId +
                ", roleName=" + roleName +
                ", roleSign=" + roleSign +
                ", remark=" + remark +
                ", gmtCreate=" + gmtCreate +
                ", gmtModifiled=" + gmtModifiled +
                ", status=" + status +
                "}";
    }
}
