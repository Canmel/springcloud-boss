package com.camel.control.config;

import lombok.Data;

import java.util.UUID;

@Data
public class DeviceConfig {

    /**
     * 识别率阀值
     */
    public static final Integer THRESHOLDVALUE = 80;

    /**
     * 设备上报BaseURL
     */
    public static final String DEVICEREPORTBASEURL = "http://192.168.1.7:8080/";

    /**
     * 设备上报SubURL
     */
    public static final String DEVICEREPORTSUBURL = "control/device/add";

    /**
     * 人证认证记录BaseURL
     */
    public static final String CARD_RECORD_BASE_URL = "http://192.168.1.7:8080/";

    /**
     * 人证认证记录SubURL
     */
    public static final String CARD_RECORD_SUB_URL = "control/record/add";

    /**
     * 设备标题
     */
    private String deviceTitle;

    /**
     * 设备Logo（base64）
     */
    private String deviceLogo;

    /**
     * 通过阀值
     */
    private Integer thresholdValue;

    /**
     * 识别距离
     */
    private Integer recognitionDistance;

    /**
     * 开启单目活体
     */
    private Boolean openMonocular;

    /**
     * 设备终端进入设置项里的登录账号
     */
    private String adminAccount;

    /**
     * 设备终端进入设置项里的登录密码
     */
    private String adminPwd;

    /**
     * 是否可配
     */
    private Boolean configurable;

    /**
     * 语音类型
     * 1 = 纯自定义文字播报
     * 2 = 纯姓名播报
     * 3 = 自定义文字+姓名播报
     * 4 = 姓名+自定义文字必报
     */
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

    public DeviceConfig() {
        this.deviceTitle = UUID.randomUUID().toString();
        this.thresholdValue = THRESHOLDVALUE;
        this.recognitionDistance = 1;
        this.openMonocular = false;
        this.adminAccount = "admin";
        this.adminPwd = "admin";
        this.ttsModel = 3;
        this.passStr = "成功";
        this.failedStr = "失败";
        this.deviceReportBaseUrl = DEVICEREPORTBASEURL;
        this.deviceReportSubUrl = DEVICEREPORTSUBURL;
        this.cardRecordBaseUrl = CARD_RECORD_BASE_URL;
        this.cardRecordSubUrl = CARD_RECORD_SUB_URL;
    }

    public DeviceConfig(String deviceTitle) {
        this.deviceTitle = deviceTitle;
    }


    public DeviceConfig(String deviceTitle, Integer thresholdValue, Integer recognitionDistance, Boolean openMonocular, String adminAccount, String adminPwd, Integer ttsModel, String passStr, String failedStr, String deviceReportBaseUrl, String deviceReportSubUrl, String cardRecordBaseUrl, String cardRecordSubUrl) {
        this.deviceTitle = deviceTitle;
        this.thresholdValue = thresholdValue;
        this.recognitionDistance = recognitionDistance;
        this.openMonocular = openMonocular;
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
}
