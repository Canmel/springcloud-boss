package com.camel.survey.model;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.survey.enums.ZsStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author baily
 * @since 2020-11-02
 */
@Data
public class RelSeatQueue extends BasePaginationEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer seatId;
    private Integer queueId;
    /**
     * 状态
     */
    @TableLogic
    private ZsStatus status;

    /**
     * 创建时间
     */
    @TableField("created_at")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    @Override
    public String toString() {
        return "RelSeatQueue{" +
        ", seatId=" + seatId +
        ", queueId=" + queueId +
        "}";
    }
}
