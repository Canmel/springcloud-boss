package com.camel.oa.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
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
public class Document implements Serializable {

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
    private Integer creator;

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
