package com.camel.survey.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.camel.core.model.SysUser;
import lombok.Data;

/**
 * <p>
 * 坐席表
 * </p>
 *
 * @author baily
 * @since 2020-05-30
 */
@Data
public class SysSeat extends ZsSurveyBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 分配的用户ID ID唯一
     */
    private Integer uid;

    @TableField(exist = false)
    private SysUser user;
    /**
     * 坐席号
     */
    private String seat;
    /**
     * 坐席密码
     */
    private String password;

    public SysSeat() {
    }

    @Override
    public String toString() {
        return "SysSeat{" +
                ", id=" + id +
                ", uid=" + uid +
                ", seat=" + seat +
                ", password=" + password +
                "}";
    }
}
