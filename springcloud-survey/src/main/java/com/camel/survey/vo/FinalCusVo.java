package com.camel.survey.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinalCusVo {
    private Integer uid;
    private String username;
    private Integer isBind;
    private Integer telProId;
    private String tel;
}
