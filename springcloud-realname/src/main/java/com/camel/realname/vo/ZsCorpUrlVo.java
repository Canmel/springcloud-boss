package com.camel.realname.vo;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.camel.core.entity.BasePaginationEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 企业实名认证路径集合对象
 */
public class ZsCorpUrlVo implements Serializable {
    /**
     *  企业营业执照 URL
     */
    private String businessLicenseUrl;
    /**
     *  法人身份证 URL
     */
    private String corporateIdUrl;
    /**
     *法人身份证手持 URL
     */
    private String corporateIdHurl;
    /**
     * 组织机构代码证 URL
     */
    private String organizationCodeUrl;
    /**
     * 介绍信或者委托函 URL
     */
    private String entrustmentLetterUrl;
    /**
     * 承诺书 URL
     */
    private String acceptanceUrl;
    /**
     *  经办人身份证头像 URL
     */
    private String agentIdTurl;
    /**
     *  经办人身份证 正反面 URL
     */
    private String agentIdUrl;
    /**
     *  经办人手持 URL
     */
    private String agentIdHurl;
    /**
     * 使用人身份证正反面 url
     */
    private String contactIdUrl;
    /**
     * 客户申请表 url
     */
    private String applySheetUrl;

    public ZsCorpUrlVo() {
    }

    public ZsCorpUrlVo(String businessLicenseUrl, String corporateIdUrl, String corporateIdHurl, String organizationCodeUrl, String entrustmentLetterUrl,
                       String acceptanceUrl, String agentIdTurl, String agentIdUrl, String agentIdHurl,String contactIdUrl,String applySheetUrl) {
        this.businessLicenseUrl = businessLicenseUrl;
        this.corporateIdUrl = corporateIdUrl;
        this.corporateIdHurl = corporateIdHurl;
        this.organizationCodeUrl = organizationCodeUrl;
        this.entrustmentLetterUrl = entrustmentLetterUrl;
        this.acceptanceUrl = acceptanceUrl;
        this.agentIdTurl = agentIdTurl;
        this.agentIdUrl = agentIdUrl;
        this.agentIdHurl = agentIdHurl;
        this.contactIdUrl = contactIdUrl;
        this.applySheetUrl = applySheetUrl;
    }

    public String getContactIdUrl() {
        return contactIdUrl;
    }

    public void setContactIdUrl(String contactIdUrl) {
        this.contactIdUrl = contactIdUrl;
    }

    public String getApplySheetUrl() {
        return applySheetUrl;
    }

    public void setApplySheetUrl(String applySheetUrl) {
        this.applySheetUrl = applySheetUrl;
    }

    public String getBusinessLicenseUrl() {
        return businessLicenseUrl;
    }

    public void setBusinessLicenseUrl(String businessLicenseUrl) {
        this.businessLicenseUrl = businessLicenseUrl;
    }

    public String getCorporateIdUrl() {
        return corporateIdUrl;
    }

    public void setCorporateIdUrl(String corporateIdUrl) {
        this.corporateIdUrl = corporateIdUrl;
    }

    public String getCorporateIdHurl() {
        return corporateIdHurl;
    }

    public void setCorporateIdHurl(String corporateIdHurl) {
        this.corporateIdHurl = corporateIdHurl;
    }

    public String getOrganizationCodeUrl() {
        return organizationCodeUrl;
    }

    public void setOrganizationCodeUrl(String organizationCodeUrl) {
        this.organizationCodeUrl = organizationCodeUrl;
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

    public String getAgentIdHurl() {
        return agentIdHurl;
    }

    public void setAgentIdHurl(String agentIdHurl) {
        this.agentIdHurl = agentIdHurl;
    }
}
