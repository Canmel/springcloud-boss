package com.camel.oa.model;

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
 * <地块 服务实现类>
 * @author baily
 * @since 1.0
 * @date 2019-10-29
 **/
public class ZsGround extends BasePaginationEntity implements Serializable {

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
        private Integer isRentable;
    /**
     * 是否可售
     */
        private Integer isSaleable;
    /**
     * 行业
     */
        private Integer industryId;
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
        private Integer status;
    /**
     * 创建时间
     */
        private Date createdAt;
    /**
     * 创建者
     */
        private Integer creator;


    public Integer getId() {
            return id;
            }

        public void setId(Integer id) {
            this.id = id;
            }

    public String getName() {
            return name;
            }

        public void setName(String name) {
            this.name = name;
            }

    public String getAreaName() {
            return areaName;
            }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
            }

    public Integer getIsRentable() {
            return isRentable;
            }

        public void setIsRentable(Integer isRentable) {
            this.isRentable = isRentable;
            }

    public Integer getIsSaleable() {
            return isSaleable;
            }

        public void setIsSaleable(Integer isSaleable) {
            this.isSaleable = isSaleable;
            }

    public Integer getIndustryId() {
            return industryId;
            }

        public void setIndustryId(Integer industryId) {
            this.industryId = industryId;
            }

    public String getPeriphery() {
            return periphery;
            }

        public void setPeriphery(String periphery) {
            this.periphery = periphery;
            }

    public String getCooperation() {
            return cooperation;
            }

        public void setCooperation(String cooperation) {
            this.cooperation = cooperation;
            }

    public Integer getStatus() {
            return status;
            }

        public void setStatus(Integer status) {
            this.status = status;
            }

    public Date getCreatedAt() {
            return createdAt;
            }

        public void setCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
            }

    public Integer getCreator() {
            return creator;
            }

        public void setCreator(Integer creator) {
            this.creator = creator;
            }

@Override
public String toString() {
        return "ZsGround{" +
                ", id=" + id +
                ", name=" + name +
                ", areaName=" + areaName +
                ", isRentable=" + isRentable +
                ", isSaleable=" + isSaleable +
                ", industryId=" + industryId +
                ", periphery=" + periphery +
                ", cooperation=" + cooperation +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", creator=" + creator +
        "}";
        }
        }
