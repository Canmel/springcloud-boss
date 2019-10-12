package com.camel.core.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.camel.core.entity.BasePaginationEntity;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 *                       .::::.
 *                     .::::::::.
 *                    :::::::::::
 *                 ..:::::::::::'      菜单模型
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
public class SysMenu extends BasePaginationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    @TableId(value = "menu_id", type = IdType.AUTO)
    private Integer menuId;
    /**
     * 父菜单ID，一级菜单为0
     */
    private Integer parentId;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单url
     */
    private String url;
    /**
     * 授权(多个用逗号分隔，如：user:list,user:create)
     */
    private String perms;
    /**
     * 类型   0：目录   1：菜单   2：按钮
     */
    private Integer type;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 排序号
     */
    private Integer orderNum;
    /**
     * 创建时间
     */
    private String gmtCreate;
    /**
     * 更新时间
     */
    private Date gmtModifiled;
    /**
     * 状态(0：已删除，1：正常)
     */
    private String status;

    public SysMenu(Integer menuId, String status) {
        this.menuId = menuId;
        this.status = status;
    }

    public SysMenu() {
    }

    @Override
    public String toString() {
        return "SysMenu{" +
        ", menuId=" + menuId +
        ", parentId=" + parentId +
        ", name=" + name +
        ", url=" + url +
        ", perms=" + perms +
        ", type=" + type +
        ", icon=" + icon +
        ", orderNum=" + orderNum +
        ", gmtCreate=" + gmtCreate +
        ", gmtModifiled=" + gmtModifiled +
        ", status=" + status +
        "}";
    }
}
