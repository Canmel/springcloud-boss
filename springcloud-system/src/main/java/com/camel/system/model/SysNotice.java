package com.camel.system.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.camel.core.entity.BasePaginationEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2019-09-29
 */
@Data
public class SysNotice extends BasePaginationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 名称
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 备注
     */
    private String remark;
    /**
     * 类型
     */
    private Integer targetType;
    /**
     * 值
     */
    private String targetValue;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 更新时间
     */
    private Date createdAt;

    /**
     * 排序
     */
    private Integer oderNum;

    public SysNotice() {
    }

    public SysNotice(Integer id) {
        this.id = id;
    }

    public SysNotice(Integer id, Integer status) {
        this.id = id;
        this.status = status;
    }

    @Override
    public String toString() {
        return "SysNotice{" +
        ", id=" + id +
        ", title=" + title +
        ", content=" + content +
        ", remark=" + remark +
        ", targetType=" + targetType +
        ", targetValue=" + targetValue +
        ", status=" + status +
        ", createdAt=" + createdAt +
        ", oderNum=" + oderNum +
        "}";
    }
}
