package com.camel.survey.vo;

import com.camel.core.entity.BasePaginationEntity;
import com.camel.core.utils.PaginationUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NumberVo extends BasePaginationEntity {

    /**
     * 电话数组
     */
    private List<String> number;

    /**
     * 电话
     */
    private String tel;


}
