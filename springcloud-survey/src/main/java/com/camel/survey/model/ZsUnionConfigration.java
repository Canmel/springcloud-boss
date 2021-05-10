package com.camel.survey.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.camel.core.entity.BasePaginationEntity;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author baily
 * @since 2021-05-05
 */
@Data
public class ZsUnionConfigration extends BasePaginationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 选项（多个以逗号隔开, 无空格）
     */
    private String options;
    /**
     * 配额数量（默认： 0）
     */
    private Integer config;

    private Integer surveyId;

    public List<Integer> getOptionsIntValue() {
        List<Integer> list = new ArrayList<>();
        if(StringUtils.isEmpty(this.options)) {
            return list;
        }
        String[] ss = options.split(",");
        for (String s: ss) {
            list.add(Integer.parseInt(s));
        }
        return list;
    }

    @Override
    public String toString() {
        return "ZsUnionConfigration{" +
        ", id=" + id +
        ", options=" + options +
        ", config=" + config +
        "}";
    }
}
