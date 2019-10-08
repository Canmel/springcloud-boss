package com.camel.system.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2019-10-08
 */
public class Traffic implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer errandId;
    /**
     * 出发地点
     */
    private String from;
    /**
     * 到达地点
     */
    private String to;
    /**
     * 出发时间
     */
    private Date fromAt;
    /**
     * 到达时间
     */
    private Date toAt;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getErrandId() {
        return errandId;
    }

    public void setErrandId(Integer errandId) {
        this.errandId = errandId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Date getFromAt() {
        return fromAt;
    }

    public void setFromAt(Date fromAt) {
        this.fromAt = fromAt;
    }

    public Date getToAt() {
        return toAt;
    }

    public void setToAt(Date toAt) {
        this.toAt = toAt;
    }

    @Override
    public String toString() {
        return "Traffic{" +
        ", id=" + id +
        ", errandId=" + errandId +
        ", from=" + from +
        ", to=" + to +
        ", fromAt=" + fromAt +
        ", toAt=" + toAt +
        "}";
    }
}
