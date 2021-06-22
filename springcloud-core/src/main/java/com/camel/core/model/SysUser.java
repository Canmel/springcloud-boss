package com.camel.core.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.core.enums.GradeStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 *                       .::::.
 *                     .::::::::.
 *                    :::::::::::
 *                 ..:::::::::::'      用户模型
 *              '::::::::::::'
 *                .::::::::::
 *           '::::::::::::::..
 *                ..::::::::::::.
 *              ``::::::::::::::::
 *               ::::``:::::::::'        .:::.
 *              ::::'   ':::::'       .::::::::.
 *            .::::'      ::::     .:::::::'::::.
 *           .:::'       :::::  .:::::::::' ':::::.
 *          .::'        :::::.:::::::::'      ':::::.
 *         .::'         ::::::::::::::'         ``::::.
 *     ...:::           ::::::::::::'              ``::.
 *    ```` ':.          ':::::::::'                  ::::..
 *                       '.:::::'                    ':'````..
 * @author baily
 * @since 2019/7/4
 **/
@Data
public class SysUser extends BasePaginationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID主键
     */
    @TableId(value = "uid", type = IdType.AUTO)
    private Integer uid;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 昵称/姓名
     */
    private String nickname;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 状态(0：已删除，1：正常)
     */
    @TableLogic
    private String status;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 修改时间
     */
    private Date gmtModified;
    /**
     * 最后一次登录时间
     */
    private String lastLoginTime;
    /**
     * 密码生效时间
     */
    private Date passwordExpiredTime;
    /**
     * 用户登录失败次数
     */
    private Integer loginFailureCount;
    /**
     * 所在部门
     */
    private String szbm;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 性别(0：男，1：女)
     */
    private String gender;
    /**
     * 所属单位编号
     */
    private String orgNo;
    /**
     * 所属单位名称
     */
    private String orgName;

    /**
     * 地址
     */
    private String address;

    /**
     * 备注
     */
    private String remark;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 身份证号
     */
    private String idNum;

    /**
     * 座位号
     */
    private String seatNum;

    /**
     * 工号
     */
    private String workNum;

    private Integer companyId;

    /**
     * 访员级别(编号)
     */
    private Integer grade;

    /**
     * 访员级别(名称)
     */
    @TableField(exist = false)
    private String gradeName;

    @TableField(exist = false)
    private SysCompany company;

    @TableField(exist = false)
    private ZsAgency agency;

    private Integer agencyId;

    @TableField(exist = false)
    private List roleIds;

    @TableField(exist = false)
    @JsonIgnore
    private List<SysRole> sysRoles;

    @TableField(exist = false)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonIgnore
    private SysUser sysUser;

    public SysUser(Integer uid) {
        this.uid = uid;
    }

    public SysUser(Integer uid, String username) {
        this.uid = uid;
    }

    public SysUser() {
    }

    public SysUser(Integer uid, String username, String password, String nickname, String email, String mobile, String idNum) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.mobile = mobile;
        this.idNum = idNum;
    }

    @Override
    public String toString() {
        return "SysUser{" +
        ", uid=" + uid +
        ", username=" + username +
        ", password=" + password +
        ", nickname=" + nickname +
        ", bpm=" + email +
        ", mobile=" + mobile +
        ", status=" + status +
        ", gmtCreate=" + gmtCreate +
        ", gmtModified=" + gmtModified +
        ", lastLoginTime=" + lastLoginTime +
        ", passwordExpiredTime=" + passwordExpiredTime +
        ", loginFailureCount=" + loginFailureCount +
        ", szbm=" + szbm +
        ", age=" + age +
        ", gender=" + gender +
        ", orgNo=" + orgNo +
        ", orgName=" + orgName +
                ", grade=" + grade +
        "}";
    }
}
