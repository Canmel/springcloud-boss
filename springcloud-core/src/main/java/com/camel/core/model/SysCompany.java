package com.camel.core.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.camel.core.entity.BasePaginationEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author baily
 * @since 2021-04-27
 */
@Data
public class SysCompany extends BasePaginationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String contacts;
    private String tel;

    /**
     * 号码是否绑定公司
     */
    @TableField(exist = false)
    private Integer isBind;

    @Override
    public String toString() {
        return "SysCompany{" +
        ", id=" + id +
        ", name=" + name +
        ", contacts=" + contacts +
        ", tel=" + tel +
        "}";
    }
}
