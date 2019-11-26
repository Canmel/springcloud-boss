package com.camel.attendance.model;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.core.model.SysUser;

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
public class Args extends BasePaginationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String value;

    private String code;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public SysUser getCreator() {
        return creator;
    }

    public void setCreator(SysUser creator) {
        this.creator = creator;
    }

    public Integer getUpdatorId() {
        return updatorId;
    }

    public void setUpdatorId(Integer updatorId) {
        this.updatorId = updatorId;
    }

    public SysUser getUpdator() {
        return updator;
    }

    public void setUpdator(SysUser updator) {
        this.updator = updator;
    }

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
