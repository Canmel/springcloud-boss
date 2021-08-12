package com.camel.realname.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.camel.core.entity.BaseEntity;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.core.model.SysUser;
import com.camel.realname.enums.ZsStatus;
import com.camel.realname.enums.ZsYesOrNo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApproveOrder extends BasePaginationEntity {
    /**
     * 订单编号,使用雪花算法生成
     */
    @NotNull(message = "订单编号不能为空",groups = {Insert.class})
    private Long id;
    /**
     * 用户id
     */
//    @NotNull(message = "用户id不能为空",groups = {Select.class})
    @NotNull(message = "用户id不能为空",groups = {Insert.class})
    private Integer userId;
//    /**
//     * 账号
//     */
//    private String account;
    /**
     * 价格,必填
     */
    @NotNull(message = "价格不能为空",groups = {Insert.class})
    private BigDecimal price;
    /**
     * 商品描述,必填
     */
    @NotBlank(message = "商品描述不能为空",groups = {Insert.class})
    private String body;
    /**
     * 订单名称,必填
     */
    @NotBlank(message = "订单名称不能为空",groups = {Insert.class})
    private String subject;
    /**
     * 订单创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    /**
     * 是否付款，0：否，1：是
     */
    private ZsYesOrNo isPay;
    /**
     * 是否有效，0：无效，1：有效
     */
    private ZsStatus status;
    /*超时时间参数*/
    private String timeout_express="10m";
    private String product_code="FAST_INSTANT_TRADE_PAY";

    @TableField(exist = false)
    private SysUser sysUser;

}
