package com.camel.survey.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.camel.core.entity.BasePaginationEntity;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author baily
 * @since 2020-02-19
 */
@Data
public class ZsSeat extends BasePaginationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 坐席号
     */
    @TableField("seatNum")
    private String seatNum;

    private Integer uid;

    /**
     * 坐席密码
     */
    private String password;

    @Override
    public String toString() {
        return "ZsSeat{" +
        ", id=" + id +
        ", seatNum=" + seatNum +
        ", password=" + password +
        "}";
    }
}
