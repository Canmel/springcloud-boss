package com.camel.core.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;

/** @author baily */
@Data
public class
BasePaginationEntity extends BaseEntity{

    @TableField(exist = false)
    private Integer pageNum;

    @TableField(exist = false)
    private Integer pageSize;

    public BasePaginationEntity(Integer pageNum, Integer pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public BasePaginationEntity() {
        this.pageNum = 1;
        this.pageSize = 10;
    }
}
