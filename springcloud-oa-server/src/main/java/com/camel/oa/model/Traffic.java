package com.camel.oa.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.camel.core.entity.BasePaginationEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author baily
 * @since 2019-10-08
 */
@Data
public class Traffic extends BasePaginationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer imperfectId;
    /**
     * 出发地点
     */
    private String come;
    /**
     * 到达地点
     */
    private String comeTo;
    /**
     * 出发时间
     */
    private Date comeAt;
    /**
     * 到达时间
     */
    private Date toAt;
    /**
     * 票面金额
     */
    private Integer amount;

    public Traffic(Integer id, Integer imperfectId, String come, String comeTo, Date comeAt, Date toAt, Integer amount) {
        this.id = id;
        this.imperfectId = imperfectId;
        this.come = come;
        this.comeTo = comeTo;
        this.comeAt = comeAt;
        this.toAt = toAt;
        this.amount = amount;
    }

    public Traffic() {
    }

    @Override
    public String toString() {
        return "Traffic{" +
        ", id=" + id +
        ", imperfectId=" + imperfectId +
        ", come=" + come +
        ", to=" + comeTo +
        ", comeAt=" + comeAt +
        ", toAt=" + toAt +
        "}";
    }
}
