package com.camel.control.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.camel.core.entity.BasePaginationEntity;
import lombok.Data;
import org.springframework.util.ObjectUtils;

/**
 * <p>
 * 
 * </p>
 *
 * @author baily
 * @since 2020-03-20
 */
@Data
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

    public DDevice() {
    }

    public DDevice(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public boolean isOnline() {
        if(ObjectUtils.isEmpty(this.updateAt)) {
            return false;
        }
        long timeDistance = this.updateAt.getTime() - new Date().getTime();
        return timeDistance < 40 * 1000;
    }

    public DDevice(Integer id, String deviceNumber, String deviceIp, Date createdAt, Date updateAt, String deviceName, Integer thresholdValue, String adminAccount, String adminPwd, Integer ttsModel, String passStr, String failedStr, String deviceReportBaseUrl, String deviceReportSubUrl, String cardRecordBaseUrl, String cardRecordSubUrl) {
        this.id = id;
        this.deviceNumber = deviceNumber;
        this.deviceIp = deviceIp;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.deviceName = deviceName;
        this.thresholdValue = thresholdValue;
        this.adminAccount = adminAccount;
        this.adminPwd = adminPwd;
        this.ttsModel = ttsModel;
        this.passStr = passStr;
        this.failedStr = failedStr;
        this.deviceReportBaseUrl = deviceReportBaseUrl;
        this.deviceReportSubUrl = deviceReportSubUrl;
        this.cardRecordBaseUrl = cardRecordBaseUrl;
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
