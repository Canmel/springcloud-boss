package com.camel.survey.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.camel.survey.enums.ZsYesOrNo;
import lombok.Data;

/**
 * <p>
 * 客户组
 * </p>
 *
 * @author baily
 * @since 2020-12-13
 */
@Data
public class CustomerGroup extends ZsSurveyBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 客户组名称
     */
    private String name;

    /**
     * 是否可选
     */
    private ZsYesOrNo state;

    public CustomerGroup(String name) {
        this.name = name;
    }

    public CustomerGroup() {
    }

    @Override
    public String toString() {
        return "CustomerGroup{" +
        ", id=" + id +
        ", name=" + name +
        "}";
    }
}
