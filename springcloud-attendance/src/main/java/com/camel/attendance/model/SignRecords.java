package com.camel.attendance.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.core.model.SysUser;
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
 * <打卡记录 服务实现类>
 *
 * @author baily
 * @date 2019-11-27
 * @since 1.0
 **/
@Data
public class SignRecords extends BasePaginationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 签入签出人员
     */
    private Integer userId;

    @TableField(exist = false)
    private SysUser user;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 时间字符串
     */
    @TableField(exist = false)
    private String createdAtStr;
    /**
     * 坐标
     */
    private String coordinate;
    /**
     * 备注
     */
    private String remark;
    /**
     * 判定 0，1，2迟到，正常，早退
     */
    private Integer determine;
    /**
     * 签到时间
     */
    private String signInTime;
    /**
     * 签退时间
     */
    private String signOutTime;
    /**
     * 可提前打卡时间
     */
    private Integer advanceTime;
    /**
     * 可延迟打卡时间
     */
    private Integer delayTime;

    public SignRecords() {
    }

    public SignRecords(Integer userId, String createdAtStr) {
        this.userId = userId;
        this.createdAtStr = createdAtStr;
        this.setPageSize(0);
    }

    @Override
    public String toString() {
        return "SignRecords{" +
                ", id=" + id +
                ", userId=" + userId +
                ", status=" + status +
                ", type=" + type +
                ", createdAt=" + createdAt +
                ", coordinate=" + coordinate +
                ", remark=" + remark +
                ", determine=" + determine +
                ", signInTime=" + signInTime +
                ", signOutTime=" + signOutTime +
                ", advanceTime=" + advanceTime +
                ", delayTime=" + delayTime +
                "}";
    }
}
