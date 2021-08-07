package com.camel.realname.model;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.core.model.SysUser;
import com.camel.realname.enums.NumberStatus;
import com.camel.realname.enums.ZsYesOrNo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

/**
 * <p>
 *
 * </p>
 *
 * @author baily
 * @since 2021-07-05
 */
@Data
public class ApplyNumber extends BasePaginationEntity {

    private static final long serialVersionUID = 1L;

    private static final SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 编号
     */
    private String code;
    /**
     * 申请时间
     */
    private Date applyAt;
    /**
     * 客户申请表_URL
     */
    private String applySheet;
    /**
     * 单位资质（营业执照）
     */
    private String license;
    /**
     * 法人身份证（正
     */
    private String cardLegalZ;
    /**
     * 法人身份证（反
     */
    private String cardLegalF;
    /**
     * 法人手持身份证
     */
    private String cardLegalH;
    /**
     * 经办人身份证(正
     */
    private String cardAgentZ;
    /**
     * 经办人身份证（反
     */
    private String cardAgentF;
    /**
     * 经办人身份证手持照片
     */
    private String handAgent;
    /**
     * 使用人身份证
     */
    private String cardUser;
    /**
     * 电信入网承诺书
     */
    private String enterPromise;
    /**
     * 号码申请公函
     */
    private String applyLetter;

    /**
     * 创建人
     */
    @TableField(value = "creator")
    private Integer creatorId;

    @TableField(exist = false)
    private SysUser creator;

    /**
     * 状态
     */

    private NumberStatus status;

    @TableLogic
    private ZsYesOrNo deleted;

    /**
     * 创建时间
     */
    @TableField("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    @Override
    public String toString() {
        return "ApplyNumber{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", applyAt=" + applyAt +
                ", applySheet='" + applySheet + '\'' +
                ", license='" + license + '\'' +
                ", cardLegalZ='" + cardLegalZ + '\'' +
                ", cardLegalF='" + cardLegalF + '\'' +
                ", cardLegalH='" + cardLegalH + '\'' +
                ", cardAgentZ='" + cardAgentZ + '\'' +
                ", cardAgentF='" + cardAgentF + '\'' +
                ", handAgent='" + handAgent + '\'' +
                ", cardUser='" + cardUser + '\'' +
                ", enterPromise='" + enterPromise + '\'' +
                ", applyLetter='" + applyLetter + '\'' +
                ", creatorId=" + creatorId +
                ", creator=" + creator +
                ", status=" + status +
                ", deleted=" + deleted +
                ", createdAt=" + createdAt +
                '}';
    }

    public void buildCode() {
        this.code = sd.format(new Date());
    }

    public boolean isValid() {
        if (StringUtils.isNotBlank(this.applySheet) && StringUtils.isNotBlank(this.license) && StringUtils.isNotBlank(this.cardLegalZ) && StringUtils.isNotBlank(this.cardLegalF)
                && StringUtils.isNotBlank(this.cardLegalH) && StringUtils.isNotBlank(this.cardAgentZ) && StringUtils.isNotBlank(this.cardAgentF) && StringUtils.isNotBlank(this.handAgent) && StringUtils.isNotBlank(this.cardUser)
                && StringUtils.isNotBlank(this.enterPromise) && StringUtils.isNotBlank(this.applyLetter)) {
            return true;
        }
        return false;
    }
}
