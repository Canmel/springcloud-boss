package com.camel.attendance.model;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.core.model.SysUser;
import lombok.Data;

import java.io.Serializable;

/**
 *
 *                       .::::.
 *                     .::::::::.
 *                    :::::::::::
 *                 ..:::::::::::'
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
 * < 服务实现类>
 * @author baily
 * @since 1.0
 * @date 2019-11-22
 **/
@Data
public class Args extends BasePaginationEntity implements Serializable {

    public static final String SIGN_IN_TIME_CODE = "sign_in_time";
    public static final String SIGN_OUT_TIME_CODE = "sign_out_time";
    public static final String DELAY_TIME_CODE = "delay_time";
    public static final String ADVANCE_TIME_CODE = "advance_time";

    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String value;

    private String code;

    @TableLogic
    private Integer status;

    private Date createdAt;

    private Date updateAt;

    @TableField(value = "creator")
    private Integer creatorId;

    @TableField(exist = false)
    private SysUser creator;

    @TableField(value = "updator")
    private Integer updatorId;

    @TableField(exist = false)
    private SysUser updator;

    @Override
    public String toString() {
        return "Args{" +
                ", id=" + id +
                ", name=" + name +
                ", value=" + value +
                ", code=" + code +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", creator=" + creator +
                ", updateAt=" + updateAt +
                ", updator=" + updator +
                "}";
    }
}
