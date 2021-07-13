package com.camel.realname.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.realname.vo.ZsCorpUrlVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 企业实名认证表
 */
public class ZsCorp extends BasePaginationEntity {

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
    private Integer status;

    /**
     * 提交日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date submitDate;


    public ZsCorp(Integer id, String requestId, Integer isMerge, String enterpriseName,
                  String businessLicenseCode, String businessLicenseUrl, String corporateName,
                  String corporateIdUrl, Date businessStartDate, Date businessTermStartDate,
                  Date businessTermEndDate, String businessAddress, String businessNo,
                  String organizationCode, String organizationCodeUrl, String organizationName,
                  Integer organizationType, Date organizationEffectiveDate, Date organizationExpirationDate,
                  String entrustmentLetterUrl, String acceptanceUrl, String agentName, String agentPid,
                  String agentIdTurl, String agentIdUrl, String agentIdFurl, String agentIdHurl,
                  String agentIdAddress, Integer agentIdGender, String agentIdNation, Date agentIdBirthDay,
                  String agentIdIssuingAuthority, Date agentIdCertValidDate, Date agentIdCertExpDate,
                  String agentContactNo, String agentAddress, String contactName, String contactNo,
                  Integer userId, ZsCorpUrlVo zsCorpUrlVo, Integer status, Date submitDate) {
        this.id = id;
        this.requestId = requestId;
        this.isMerge = isMerge;
        this.enterpriseName = enterpriseName;
        this.businessLicenseCode = businessLicenseCode;
        this.businessLicenseUrl = businessLicenseUrl;
        this.corporateName = corporateName;
        this.corporateIdUrl = corporateIdUrl;
        this.businessStartDate = businessStartDate;
        this.businessTermStartDate = businessTermStartDate;
        this.businessTermEndDate = businessTermEndDate;
        this.businessAddress = businessAddress;
        this.businessNo = businessNo;
        this.organizationCode = organizationCode;
        this.organizationCodeUrl = organizationCodeUrl;
        this.organizationName = organizationName;
        this.organizationType = organizationType;
        this.organizationEffectiveDate = organizationEffectiveDate;
        this.organizationExpirationDate = organizationExpirationDate;
        this.entrustmentLetterUrl = entrustmentLetterUrl;
        this.acceptanceUrl = acceptanceUrl;
        this.agentName = agentName;
        this.agentPid = agentPid;
        this.agentIdTurl = agentIdTurl;
        this.agentIdUrl = agentIdUrl;
        this.agentIdFurl = agentIdFurl;
        this.agentIdHurl = agentIdHurl;
        this.agentIdAddress = agentIdAddress;
        this.agentIdGender = agentIdGender;
        this.agentIdNation = agentIdNation;
        this.agentIdBirthDay = agentIdBirthDay;
        this.agentIdIssuingAuthority = agentIdIssuingAuthority;
        this.agentIdCertValidDate = agentIdCertValidDate;
        this.agentIdCertExpDate = agentIdCertExpDate;
        this.agentContactNo = agentContactNo;
        this.agentAddress = agentAddress;
        this.contactName = contactName;
        this.contactNo = contactNo;
        this.userId = userId;
        this.zsCorpUrlVo = zsCorpUrlVo;
        this.status = status;
        this.submitDate = submitDate;
    }

    public ZsCorp() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Integer getIsMerge() {
        return isMerge;
    }

    public void setIsMerge(Integer isMerge) {
        this.isMerge = isMerge;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getBusinessLicenseCode() {
        return businessLicenseCode;
    }

    public void setBusinessLicenseCode(String businessLicenseCode) {
        this.businessLicenseCode = businessLicenseCode;
    }

    public String getBusinessLicenseUrl() {
        return businessLicenseUrl;
    }

    public void setBusinessLicenseUrl(String businessLicenseUrl) {
        this.businessLicenseUrl = businessLicenseUrl;
    }

    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    public String getCorporateIdUrl() {
        return corporateIdUrl;
    }

    public void setCorporateIdUrl(String corporateIdUrl) {
        this.corporateIdUrl = corporateIdUrl;
    }

    public Date getBusinessStartDate() {
        return businessStartDate;
    }

    public void setBusinessStartDate(Date businessStartDate) {
        this.businessStartDate = businessStartDate;
    }

    public Date getBusinessTermStartDate() {
        return businessTermStartDate;
    }

    public void setBusinessTermStartDate(Date businessTermStartDate) {
        this.businessTermStartDate = businessTermStartDate;
    }

    public Date getBusinessTermEndDate() {
        return businessTermEndDate;
    }

    public void setBusinessTermEndDate(Date businessTermEndDate) {
        this.businessTermEndDate = businessTermEndDate;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getOrganizationCodeUrl() {
        return organizationCodeUrl;
    }

    public void setOrganizationCodeUrl(String organizationCodeUrl) {
        this.organizationCodeUrl = organizationCodeUrl;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public Integer getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(Integer organizationType) {
        this.organizationType = organizationType;
    }

    public Date getOrganizationEffectiveDate() {
        return organizationEffectiveDate;
    }

    public void setOrganizationEffectiveDate(Date organizationEffectiveDate) {
        this.organizationEffectiveDate = organizationEffectiveDate;
    }

    public Date getOrganizationExpirationDate() {
        return organizationExpirationDate;
    }

    public void setOrganizationExpirationDate(Date organizationExpirationDate) {
        this.organizationExpirationDate = organizationExpirationDate;
    }

    public String getEntrustmentLetterUrl() {
        return entrustmentLetterUrl;
    }

    public void setEntrustmentLetterUrl(String entrustmentLetterUrl) {
        this.entrustmentLetterUrl = entrustmentLetterUrl;
    }

    public String getAcceptanceUrl() {
        return acceptanceUrl;
    }

    public void setAcceptanceUrl(String acceptanceUrl) {
        this.acceptanceUrl = acceptanceUrl;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentPid() {
        return agentPid;
    }

    public void setAgentPid(String agentPid) {
        this.agentPid = agentPid;
    }

    public String getAgentIdTurl() {
        return agentIdTurl;
    }

    public void setAgentIdTurl(String agentIdTurl) {
        this.agentIdTurl = agentIdTurl;
    }

    public String getAgentIdUrl() {
        return agentIdUrl;
    }

    public void setAgentIdUrl(String agentIdUrl) {
        this.agentIdUrl = agentIdUrl;
    }

    public String getAgentIdFurl() {
        return agentIdFurl;
    }

    public void setAgentIdFurl(String agentIdFurl) {
        this.agentIdFurl = agentIdFurl;
    }

    public String getAgentIdHurl() {
        return agentIdHurl;
    }

    public void setAgentIdHurl(String agentIdHurl) {
        this.agentIdHurl = agentIdHurl;
    }

    public String getAgentIdAddress() {
        return agentIdAddress;
    }

    public void setAgentIdAddress(String agentIdAddress) {
        this.agentIdAddress = agentIdAddress;
    }

    public Integer getAgentIdGender() {
        return agentIdGender;
    }

    public void setAgentIdGender(Integer agentIdGender) {
        this.agentIdGender = agentIdGender;
    }

    public String getAgentIdNation() {
        return agentIdNation;
    }

    public void setAgentIdNation(String agentIdNation) {
        this.agentIdNation = agentIdNation;
    }

    public Date getAgentIdBirthDay() {
        return agentIdBirthDay;
    }

    public void setAgentIdBirthDay(Date agentIdBirthDay) {
        this.agentIdBirthDay = agentIdBirthDay;
    }

    public String getAgentIdIssuingAuthority() {
        return agentIdIssuingAuthority;
    }

    public void setAgentIdIssuingAuthority(String agentIdIssuingAuthority) {
        this.agentIdIssuingAuthority = agentIdIssuingAuthority;
    }

    public Date getAgentIdCertValidDate() {
        return agentIdCertValidDate;
    }

    public void setAgentIdCertValidDate(Date agentIdCertValidDate) {
        this.agentIdCertValidDate = agentIdCertValidDate;
    }

    public Date getAgentIdCertExpDate() {
        return agentIdCertExpDate;
    }

    public void setAgentIdCertExpDate(Date agentIdCertExpDate) {
        this.agentIdCertExpDate = agentIdCertExpDate;
    }

    public String getAgentContactNo() {
        return agentContactNo;
    }

    public void setAgentContactNo(String agentContactNo) {
        this.agentContactNo = agentContactNo;
    }

    public String getAgentAddress() {
        return agentAddress;
    }

    public void setAgentAddress(String agentAddress) {
        this.agentAddress = agentAddress;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public ZsCorpUrlVo getZsCorpUrlVo() {
        return zsCorpUrlVo;
    }

    public void setZsCorpUrlVo(ZsCorpUrlVo zsCorpUrlVo) {
        this.zsCorpUrlVo = zsCorpUrlVo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    @Override
    public String toString() {
        return "ZsCorp{" +
                "id=" + id +
                ", requestId='" + requestId + '\'' +
                ", isMerge=" + isMerge +
                ", enterpriseName='" + enterpriseName + '\'' +
                ", businessLicenseCode='" + businessLicenseCode + '\'' +
                ", businessLicenseUrl='" + businessLicenseUrl + '\'' +
                ", corporateName='" + corporateName + '\'' +
                ", corporateIdUrl='" + corporateIdUrl + '\'' +
                ", businessStartDate=" + businessStartDate +
                ", businessTermStartDate=" + businessTermStartDate +
                ", businessTermEndDate=" + businessTermEndDate +
                ", businessAddress='" + businessAddress + '\'' +
                ", businessNo='" + businessNo + '\'' +
                ", organizationCode='" + organizationCode + '\'' +
                ", organizationCodeUrl='" + organizationCodeUrl + '\'' +
                ", organizationName='" + organizationName + '\'' +
                ", organizationType=" + organizationType +
                ", organizationEffectiveDate=" + organizationEffectiveDate +
                ", organizationExpirationDate=" + organizationExpirationDate +
                ", entrustmentLetterUrl='" + entrustmentLetterUrl + '\'' +
                ", acceptanceUrl='" + acceptanceUrl + '\'' +
                ", agentName='" + agentName + '\'' +
                ", agentPid='" + agentPid + '\'' +
                ", agentIdTurl='" + agentIdTurl + '\'' +
                ", agentIdUrl='" + agentIdUrl + '\'' +
                ", agentIdFurl='" + agentIdFurl + '\'' +
                ", agentIdHurl='" + agentIdHurl + '\'' +
                ", agentIdAddress='" + agentIdAddress + '\'' +
                ", agentIdGender=" + agentIdGender +
                ", agentIdNation='" + agentIdNation + '\'' +
                ", agentIdBirthDay=" + agentIdBirthDay +
                ", agentIdIssuingAuthority='" + agentIdIssuingAuthority + '\'' +
                ", agentIdCertValidDate=" + agentIdCertValidDate +
                ", agentIdCertExpDate=" + agentIdCertExpDate +
                ", agentContactNo='" + agentContactNo + '\'' +
                ", agentAddress='" + agentAddress + '\'' +
                ", contactName='" + contactName + '\'' +
                ", contactNo='" + contactNo + '\'' +
                ", userId=" + userId +
                ", zsCorpUrlVo=" + zsCorpUrlVo +
                ", status=" + status +
                ", submitDate=" + submitDate +
                '}';
    }
}
