package com.camel.control.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.camel.core.entity.BasePaginationEntity;

/**
 * <p>
 * 
 * </p>
 *
 * @author baily
 * @since 2020-03-20
 */
public class DDevice extends BasePaginationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 设备编号
     */
    private String deviceNumber;
    /**
     * 设备上的IP
     */
    private String deviceIp;
    /**
     * 创建时间
     */
    private Date createdAt;
    /**
     * 更新时间
     */
    private Date updateAt;
    /**
     * 设备标题
     */
    private String deviceName;
    /**
     * 阀值
     */
    private Integer thresholdValue;
    /**
     * 管理员账户
     */
    private String adminAccount;
    /**
     * 管理员密码
     */
    private String adminPwd;
    private Integer ttsModel;
    /**
     * 成功提示
     */
    private String passStr;
    /**
     * 失败提示
     */
    private String failedStr;
    /**
     * 设备上报BaseURL
     */
    private String deviceReportBaseUrl;
    /**
     * 设备上报SubURL
     */
    private String deviceReportSubUrl;
    /**
     * 人证认证记录BaseURL
     */
    private String cardRecordBaseUrl;
    /**
     * 人证认证记录SubURL
     */
    private String cardRecordSubUrl;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public String getDeviceIp() {
        return deviceIp;
    }

    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Integer getThresholdValue() {
        return thresholdValue;
    }

    public void setThresholdValue(Integer thresholdValue) {
        this.thresholdValue = thresholdValue;
    }

    public String getAdminAccount() {
        return adminAccount;
    }

    public void setAdminAccount(String adminAccount) {
        this.adminAccount = adminAccount;
    }

    public String getAdminPwd() {
        return adminPwd;
    }

    public void setAdminPwd(String adminPwd) {
        this.adminPwd = adminPwd;
    }

    public Integer getTtsModel() {
        return ttsModel;
    }

    public void setTtsModel(Integer ttsModel) {
        this.ttsModel = ttsModel;
    }

    public String getPassStr() {
        return passStr;
    }

    public void setPassStr(String passStr) {
        this.passStr = passStr;
    }

    public String getFailedStr() {
        return failedStr;
    }

    public void setFailedStr(String failedStr) {
        this.failedStr = failedStr;
    }

    public String getDeviceReportBaseUrl() {
        return deviceReportBaseUrl;
    }

    public void setDeviceReportBaseUrl(String deviceReportBaseUrl) {
        this.deviceReportBaseUrl = deviceReportBaseUrl;
    }

    public String getDeviceReportSubUrl() {
        return deviceReportSubUrl;
    }

    public void setDeviceReportSubUrl(String deviceReportSubUrl) {
        this.deviceReportSubUrl = deviceReportSubUrl;
    }

    public String getCardRecordBaseUrl() {
        return cardRecordBaseUrl;
    }

    public void setCardRecordBaseUrl(String cardRecordBaseUrl) {
        this.cardRecordBaseUrl = cardRecordBaseUrl;
    }

    public String getCardRecordSubUrl() {
        return cardRecordSubUrl;
    }

    public void setCardRecordSubUrl(String cardRecordSubUrl) {
        this.cardRecordSubUrl = cardRecordSubUrl;
    }

    @Override
    public String toString() {
        return "DDevice{" +
        ", id=" + id +
        ", deviceNumber=" + deviceNumber +
        ", deviceIp=" + deviceIp +
        ", createdAt=" + createdAt +
        ", updateAt=" + updateAt +
        ", deviceName=" + deviceName +
        ", thresholdValue=" + thresholdValue +
        ", adminAccount=" + adminAccount +
        ", adminPwd=" + adminPwd +
        ", ttsModel=" + ttsModel +
        ", passStr=" + passStr +
        ", failedStr=" + failedStr +
        ", deviceReportBaseUrl=" + deviceReportBaseUrl +
        ", deviceReportSubUrl=" + deviceReportSubUrl +
        ", cardRecordBaseUrl=" + cardRecordBaseUrl +
        ", cardRecordSubUrl=" + cardRecordSubUrl +
        "}";
    }
}
