package com.camel.survey.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.survey.enums.ZsYesOrNo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author baily
 * @since 2021-03-17
 */
@Data
public class ZsReport extends BasePaginationEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 身份证
     */
    private String idNum;
    /**
     * 学校
     */
    private String school;
    private String phone;
    private String address;
    /**
     * 工作日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date workDate;
    /**
     * 班次
     */
    private String workShit;
    /**
     * 绿码地址
     */
    private String greenUrl;
    /**
     * 防疫行程卡
     */
    private String epidemicUrl;

    /**
     * 微信唯一标识
     * @return
     */
    private String openid;

    /**
     * 是否通过
     */
    private ZsYesOrNo isPass;

    @Override
    public String toString() {
        return "ZsReport{" +
        ", id=" + id +
        ", username=" + username +
        ", sex=" + sex +
        ", email=" + email +
        ", idNum=" + idNum +
        ", school=" + school +
        ", phone=" + phone +
        ", adress=" + address +
        ", workDate=" + workDate +
        ", workShit=" + workShit +
        ", greenUrl=" + greenUrl +
        ", epidemicUrl=" + epidemicUrl +
        "}";
    }
}
