package com.camel.survey.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.core.model.SysUser;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author baily
 * @since 2020-06-10
 */
@Data
public class ZsPhoneInformation extends BasePaginationEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 手机号码
     */
    private String mobile;
    private Date createdAt;
    @TableId(value = "creator")
    private Integer creatorId;

    private SysUser creator;
  /**
     * 问卷ID
     */
    private Integer surveyId;
    /**
     * 状态
     */
    private Integer status;
    private String information;


    @Override
    public String toString() {
        return "ZsPhoneInformation{" +
        ", id=" + id +
        ", mobile=" + mobile +
        ", createdAt=" + createdAt +
        ", creatorId=" + creatorId +
        ", surveyId=" + surveyId +
        ", status=" + status +
        ", information=" + information +
        "}";
    }
}
