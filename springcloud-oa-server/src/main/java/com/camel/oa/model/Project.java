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
 * <项目 服务实现类>
 * @author baily
 * @since 1.0
 * @date 2019-10-21
 **/
public class Project extends BasePaginationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 名称
     */
    private String pname;
    /**
     * 编号
     */
    private String code;
    /**
     * 当前份数
     */
    private Integer currentCopies;
    /**
     * 总份数
     */
    private Integer copies;
    /**
     * 需要收集的有效份数
     */
    private Integer collectCopies;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 调查类型
     */
    private Integer type;
    /**
     * 时间戳
     */
    private Date createdAt;
    /**
     * 创建人
     */
    private Integer creator;
    private Date releaseAt;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getCurrentCopies() {
        return currentCopies;
    }

    public void setCurrentCopies(Integer currentCopies) {
        this.currentCopies = currentCopies;
    }

    public Integer getCopies() {
        return copies;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }

    public Integer getCollectCopies() {
        return collectCopies;
    }

    public void setCollectCopies(Integer collectCopies) {
        this.collectCopies = collectCopies;
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

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Date getReleaseAt() {
        return releaseAt;
    }

    public void setReleaseAt(Date releaseAt) {
        this.releaseAt = releaseAt;
    }

    @Override
    public String toString() {
        return "Project{" +
                ", id=" + id +
                ", pname=" + pname +
                ", code=" + code +
                ", currentCopies=" + currentCopies +
                ", copies=" + copies +
                ", collectCopies=" + collectCopies +
                ", status=" + status +
                ", type=" + type +
                ", createdAt=" + createdAt +
                ", creator=" + creator +
                ", releaseAt=" + releaseAt +
                "}";
    }
}
