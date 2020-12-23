package com.camel.core.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.camel.core.entity.BasePaginationEntity;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author baily
 * @since 2020-12-08
 */
@Data
public class ZsAgency extends BasePaginationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 推荐费结算方式名称
     */
    private String name;
    /**
     * 结算方式 0工资比例 1时长
     */
    private Integer type;
    private Date createdAt;

    /**
     * 最大提取量
     */
    private Double maxValue;
    /**
     * 值
     */
    private Double svalue;

    @Override
    public String toString() {
        return "ZsAgency{" +
        ", id=" + id +
        ", name=" + name +
        ", type=" + type +
        ", createdAt=" + createdAt +
        ", svalue=" + svalue +
        "}";
    }
}
