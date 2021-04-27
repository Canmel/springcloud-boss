package com.camel.auth.model;

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
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID主键
     */
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

    private String address;

    private String remark;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 身份证号码
     */
    private String idNum;

    private List<SysRole> sysRoles;

    /**
     * 公司ID
     */
    private Integer companyId;

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
        "}";
    }
}
