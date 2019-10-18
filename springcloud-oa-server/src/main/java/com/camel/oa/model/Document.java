package com.camel.oa.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.camel.core.entity.BaseProcessPaginationEntity;
import com.camel.core.model.SysUser;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author ${author}
 * @since 2019-10-12
 */
@Data
public class Document extends BaseProcessPaginationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 文档名称
     */
    private String dname;
    /**
     * 文档地址
     */
    private String address;
    /**
     * 文档大小
     */
    private Double dsize;
    /**
     * 文档类型
     */
    private String dtype;
    private Date createdAt;
    /**
     * 创建者
     */
    @TableField(exist = false)
    private SysUser creator;

    @TableField(value = "creator")
    private Integer creatorId;

    /**
     * 状态
     */
    private Integer status;

    public Document() {
    }

    public Document(Integer id, Integer status) {
        this.id = id;
        this.status = status;
    }

    public Document(Integer id, String dname, String address, Double dsize, String dtype, Date createdAt, Integer creator) {
        this.id = id;
        this.dname = dname;
        this.address = address;
        this.dsize = dsize;
        this.dtype = dtype;
        this.createdAt = createdAt;
        this.creator = new SysUser(creator);
        this.creatorId = creator;
    }

    public Document(Integer id, String dname, String address, Double dsize, String dtype, Date createdAt, Integer creator, Integer status) {
        this.id = id;
        this.dname = dname;
        this.address = address;
        this.dsize = dsize;
        this.dtype = dtype;
        this.createdAt = createdAt;
        this.creator = new SysUser(creator);
        this.status = status;
        this.creatorId = creator;
    }

    public Document(String dname, String address, Double dsize, String dtype, Integer creator) {
        this.dname = dname;
        this.address = address;
        this.dsize = dsize;
        this.dtype = dtype;
        this.creator = new SysUser(creator);
        this.creatorId = creator;
    }

    @Override
    public String toString() {
        return "Document{" +
                ", id=" + id +
                ", dname=" + dname +
                ", address=" + address +
                ", dsize=" + dsize +
                ", dtype=" + dtype +
                ", createdAt=" + createdAt +
                ", creator=" + creator +
                "}";
    }
}
