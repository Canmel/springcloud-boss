package com.camel.realname.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.realname.enums.ZsApplyStatus;
import com.camel.realname.enums.ZsStatus;
import com.camel.realname.vo.ZsCorpUrlVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 企业实名认证表
 */
@Data
public class ZsCorp extends BasePaginationEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     *  请求流水号编码，由调用方生成，在响应时原样返回，格式为“17 位系统时间戳（精确到毫秒）
     * yyyyMMddHHmmssfff + 6 位随机数”，如
     * “20170101010101111000001”
     */
    private String requestId;
    /**
     *是否三证合一，0 否，1 是
     */
    private Integer isMerge;
    /**
     *企业名称
     */
    private String enterpriseName;
    /**
     *企业营业执照代码，大写形式输入
     */
    private String businessLicenseCode;
    /**
     *企业营业执照 URL
     */
    private String businessLicenseUrl;
    /**
     *法人
     */
    private String corporateName;
    /**
     *法人身份证 URL
     */
    private String corporateIdUrl;
    /**
     *企业成立日期 格式：2017-01-01
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date businessStartDate;
    /**
     *企业开始营业日期 格式：2017-01-01
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date businessTermStartDate;
    /**
     *企业结束营业日期 格式：2017-01-01 当永久时，传 0 值
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date businessTermEndDate;
    /**
     *单位地址
     */
    private String businessAddress;
    /**
     *单位联系电话
     */
    private String businessNo;
    /**
     *组织机构号代码。当 isMerge == 0 时 必填
     */
    private String organizationCode;
    /**
     *组织机构代码证 URL。当 isMerge == 0 时 必填。
     */
    private String organizationCodeUrl;
    /**
     * 组织名称。当 isMerge == 0 时 必填
     */
    private String organizationName;
    /**
     * 机构类型.当 isMerge == 0 时 必填
     */
    private Integer organizationType;
    /**
     * 组织机构 有效日期 当 isMerge == 0 时 必填。 格式：2017-01-01
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date organizationEffectiveDate;
    /**
     *组织机构 失效日期 当 isMerge == 0 时 必填。 格式：2017-01-01
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date organizationExpirationDate;
    /**
     *介绍信或者委托函 URL
     */
    private String entrustmentLetterUrl;
    /**
     *承诺书 URL
     */
    private String acceptanceUrl;
    /**
     *经办人姓名
     */
    private String agentName;
    /**
     *经办人身份证号码
     */
    private String agentPid;
    /**
     *经办人身份证头像 URL
     */
    private String agentIdTurl;
    /**
     *经办人身份证正面 URL
     */
    private String agentIdUrl;
    /**
     *经办人身份证反面 URL
     */
    private String agentIdFurl;
    /**
     *经办人手持身份证 URL
     */
    private String agentIdHurl;
    /**
     *经办人身份证地址
     */
    private String agentIdAddress;
    /**
     *经办人性别 1 男 0 女
     */
    private Integer agentIdGender;
    /**
     *经办人民族
     */
    private String agentIdNation;
    /**
     *经办人生日 格式：2017-01-01
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date agentIdBirthDay;
    /**
     *经办人签证机关
     */
    private String agentIdIssuingAuthority;
    /**
     *经办人证件生效日期 格式：2017-01-01
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date agentIdCertValidDate;
    /**
     *经办人证件失效日期 格式：2017-01-01
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date agentIdCertExpDate;
    /**
     *经办人手机号码
     */
    private String agentContactNo;
    /**
     *经办人联系地址
     */
    private String agentAddress;
    /**
     *联系人姓名
     */
    private String contactName;
    /**
     *联系电话
     */
    private String contactNo;
    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 企业实名认证路径集合对象
     */
    @TableField(exist = false)
    private ZsCorpUrlVo zsCorpUrlVo;

    /**
     * 企业认证状态 0, "已删除",1, "创建", 2, "申请中", 3, "成功", 4, "失败"
     */
    private ZsApplyStatus apply;

    /**
     * 状态 0 无效 1 有效
     */
    @TableLogic
    private ZsStatus status;

    /**
     * 提交日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date submitDate;

}
