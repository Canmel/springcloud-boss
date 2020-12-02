package com.camel.survey.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.camel.core.entity.BasePaginationEntity;
import lombok.Data;

/**
 * <p>
 * 统一地区库
 * </p>
 *
 * @author baily
 * @since 2020-12-02
 */
@Data
public class BaseArea extends BasePaginationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 地址ID
     */
    @TableId(value = "base_areaid", type = IdType.AUTO)
    private Integer baseAreaid;
    /**
     * 地区名字
     */
    private String name;
    /**
     * 上级路径ID
     */
    private Integer parentid;
    /**
     * 顺序
     */
    private Integer vieworder;

    @Override
    public String toString() {
        return "BaseArea{" +
        ", baseAreaid=" + baseAreaid +
        ", name=" + name +
        ", parentid=" + parentid +
        ", vieworder=" + vieworder +
        "}";
    }
}
