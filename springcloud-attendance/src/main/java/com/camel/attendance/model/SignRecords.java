package com.camel.attendance.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.camel.core.entity.BasePaginationEntity;
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
 * <打卡记录 服务实现类>
 * @author baily
 * @since 1.0
 * @date 2019-11-27
 **/
public class SignRecords extends BasePaginationEntity implements Serializable {

private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
                    @TableId(value = "id", type = IdType.AUTO)
                private Integer id;
        private Integer userId;
    /**
     * 状态
     */
        private Integer status;
    /**
     * 类型
     */
        private Integer type;
        private Date createdAt;
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
        private Date signInTime;
    /**
     * 签退时间
     */
        private Date signOutTime;
    /**
     * 可提前打卡时间
     */
        private Integer advanceTime;
    /**
     * 可延迟打卡时间
     */
        private Integer delayTime;


    public Integer getId() {
            return id;
            }

        public void setId(Integer id) {
            this.id = id;
            }

    public Integer getUserId() {
            return userId;
            }

        public void setUserId(Integer userId) {
            this.userId = userId;
            }

    public Integer getStatus() {
            return status;
            }

        public void setStatus(Integer status) {
            this.status = status;
            }

    public Integer getType() {
            return type;
            }

        public void setType(Integer type) {
            this.type = type;
            }

    public Date getCreatedAt() {
            return createdAt;
            }

        public void setCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
            }

    public String getCoordinate() {
            return coordinate;
            }

        public void setCoordinate(String coordinate) {
            this.coordinate = coordinate;
            }

    public String getRemark() {
            return remark;
            }

        public void setRemark(String remark) {
            this.remark = remark;
            }

    public Integer getDetermine() {
            return determine;
            }

        public void setDetermine(Integer determine) {
            this.determine = determine;
            }

    public Date getSignInTime() {
            return signInTime;
            }

        public void setSignInTime(Date signInTime) {
            this.signInTime = signInTime;
            }

    public Date getSignOutTime() {
            return signOutTime;
            }

        public void setSignOutTime(Date signOutTime) {
            this.signOutTime = signOutTime;
            }

    public Integer getAdvanceTime() {
            return advanceTime;
            }

        public void setAdvanceTime(Integer advanceTime) {
            this.advanceTime = advanceTime;
            }

    public Integer getDelayTime() {
            return delayTime;
            }

        public void setDelayTime(Integer delayTime) {
            this.delayTime = delayTime;
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
