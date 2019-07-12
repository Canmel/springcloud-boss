package com.camel.oa.model;

import com.camel.core.entity.BasePaginationEntity;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2019-07-12
 */
public class Trip extends BasePaginationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer imperfetId;
    private String start;
    private String finish;
    private Double amount;
    private Date startAt;
    private Date finishAt;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getImperfetId() {
        return imperfetId;
    }

    public void setImperfetId(Integer imperfetId) {
        this.imperfetId = imperfetId;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getStartAt() {
        return startAt;
    }

    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

    public Date getFinishAt() {
        return finishAt;
    }

    public void setFinishAt(Date finishAt) {
        this.finishAt = finishAt;
    }

    @Override
    public String toString() {
        return "Trip{" +
        ", id=" + id +
        ", imperfetId=" + imperfetId +
        ", start=" + start +
        ", finish=" + finish +
        ", amount=" + amount +
        ", startAt=" + startAt +
        ", finishAt=" + finishAt +
        "}";
    }
}
