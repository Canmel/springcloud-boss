package com.camel.control.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.camel.core.entity.BasePaginationEntity;

/**
 * <p>
 * 
 * </p>
 *
 * @author baily
 * @since 2020-03-20
 */
public class DRecord extends BasePaginationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 身份证号
     */
    private String idNumber;
    /**
     * 性别：1：男   2：女
     */
    private String sex;
    /**
     * 姓名
     */
    private String name;
    /**
     * 头像（base64格式）
     */
    private String avatar;
    /**
     * 生日
     */
    private String birthday;
    /**
     * 民族
     */
    private String nation;
    /**
     * 身份证地址
     */
    private String address;
    /**
     * 签发机关
     */
    @TableField("signOffice")
    private String signOffice;
    /**
     * 有效日期
     */
    @TableField("legalDate")
    private String legalDate;
    /**
     * 认证结果： 0：未通过，1：通过
     */
    private String result;
    /**
     * 对比相似度，如0.989
     */
    @TableField("similarDegree")
    private String similarDegree;
    /**
     * 身份证图片（base64格式）
     */
    @TableField("idcardImage")
    private String idcardImage;
    /**
     * 设备Number
     */
    @TableField("deviceNumber")
    private String deviceNumber;
    /**
     * 认证时间
     */
    @TableField("authTime")
    private Date authTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSignOffice() {
        return signOffice;
    }

    public void setSignOffice(String signOffice) {
        this.signOffice = signOffice;
    }

    public String getLegalDate() {
        return legalDate;
    }

    public void setLegalDate(String legalDate) {
        this.legalDate = legalDate;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSimilarDegree() {
        return similarDegree;
    }

    public void setSimilarDegree(String similarDegree) {
        this.similarDegree = similarDegree;
    }

    public String getIdcardImage() {
        return idcardImage;
    }

    public void setIdcardImage(String idcardImage) {
        this.idcardImage = idcardImage;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public Date getAuthTime() {
        return authTime;
    }

    public void setAuthTime(Date authTime) {
        this.authTime = authTime;
    }

    @Override
    public String toString() {
        return "DRecord{" +
        ", id=" + id +
        ", idNumber=" + idNumber +
        ", sex=" + sex +
        ", name=" + name +
        ", avatar=" + avatar +
        ", birthday=" + birthday +
        ", nation=" + nation +
        ", address=" + address +
        ", signOffice=" + signOffice +
        ", legalDate=" + legalDate +
        ", result=" + result +
        ", similarDegree=" + similarDegree +
        ", idcardImage=" + idcardImage +
        ", deviceNumber=" + deviceNumber +
        ", authTime=" + authTime +
        "}";
    }
}
