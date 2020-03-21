package com.camel.control.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.camel.core.entity.BasePaginationEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author baily
 * @since 2020-03-20
 */
@Data
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
    private String signOffice;
    /**
     * 有效日期
     */
    private String legalDate;
    /**
     * 认证结果： 0：未通过，1：通过
     */
    private String result;
    /**
     * 对比相似度，如0.989
     */
    private String similarDegree;
    /**
     * 身份证图片（base64格式）
     */
    private String idcardImage;
    /**
     * 设备Number
     */
    private String deviceNumber;
    /**
     * 认证时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date authTime;

    /**
     * 创建时间
     */
    private Date createdAt;

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
