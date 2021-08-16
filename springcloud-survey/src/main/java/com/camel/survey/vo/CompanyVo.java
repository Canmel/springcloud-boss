package com.camel.survey.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyVo {
    private Integer id;
    private String name;
    private String tel;
    private String contacts;
    private Integer telId;
    private Integer isBind;
}
