package com.camel.interviewer.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.interviewer.enums.ZsStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 微信用户信息
 * </p>
 *
 * @author baily
 * @since 2020-03-09
 */
@Data
public class WxUserForm extends WxUser {
    private String tjName;
}
