package com.camel.oa.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.camel.core.entity.BasePaginationEntity;
import lombok.Data;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Data
public class Route extends BasePaginationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String come;
    private String comeTo;
    private Date comeAt;
    private Date comeToAt;
    private String back;
    private String backTo;
    private Date backAt;
    private Date backToAt;
    private Float days;
    private Integer imperfectId;

    public Route() {
    }

    public Route(Integer imperfectId) {
        this.imperfectId = imperfectId;
    }

    public void doSetDays(){
        if(!ObjectUtils.isEmpty(this.comeAt) && !ObjectUtils.isEmpty(this.backToAt)) {
            Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();
            c1.setTime(this.comeAt);
            c2.setTime(this.backToAt);
            int d1 = c1.get(Calendar.DAY_OF_MONTH);
            int d2 = c2.get(Calendar.DAY_OF_MONTH);
            int h1 = c1.get(Calendar.HOUR_OF_DAY);
            int h2 = c2.get(Calendar.HOUR_OF_DAY);
            this.days = 0f;
            if( DateUtils.isSameDay(this.comeAt, this.backToAt)) {
                if(h1 + h2 < 24) {
                    this.days = 0.5f;
                }else{
                    this.days = 1.0f;
                }
            }else {
                this.days += h1 < 12 ? 1f : 0.5f;
                this.days += h2 > 12 ? 1f : 0.5f;
                this.days += Math.abs(d2-d1) > 1 ? Math.abs(d2 - d1) - 1 : 0;
            }
        }
    }

    @Override
    public String toString() {
        return "Route{" +
                ", id=" + id +
                ", come=" + come +
                ", comeTo=" + comeTo +
                ", comeAt=" + comeAt +
                ", comeToAt=" + comeToAt +
                ", back=" + back +
                ", backTo=" + backTo +
                ", backAt=" + backAt +
                ", backToAt=" + backToAt +
                ", days=" + days +
                "}";
    }
}
