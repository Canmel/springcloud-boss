package com.camel.survey.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.camel.core.entity.BasePaginationEntity;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author baily
 * @since 2021-04-27
 */
@Data
public class SysCompany extends BasePaginationEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String contacts;
    private String tel;


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
