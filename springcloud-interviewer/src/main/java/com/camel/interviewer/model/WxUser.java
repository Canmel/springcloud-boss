package com.camel.interviewer.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.interviewer.enums.ZsStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * <p>
 * 微信用户信息
 * </p>
 *
 * @author baily
 * @since 2020-03-09
 */
@Data
public class WxUser extends BasePaginationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 真实姓名
     */
    private String username;
    /**
     * 微信用户id
     */
    private String openid;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 身份证号码
     */
    private String idNum;
    /**
     * 学校
     */
    private String school;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
    /**
     * 状态
     */
    @TableLogic
    private ZsStatus status;

    @TableField(exist = false)
    private String validCode;

    public WxUser(String openid) {
        this.openid = openid;
    }

    public WxUser() {
    }

    @Override
    public String toString() {
        return "WxUser{" +
        ", id=" + id +
        ", username=" + username +
        ", openid=" + openid +
        ", sex=" + sex +
        ", idNum=" + idNum +
        ", school=" + school +
        ", phone=" + phone +
        ", email=" + email +
        ", createdAt=" + createdAt +
        ", status=" + status +
        "}";
    }
}
