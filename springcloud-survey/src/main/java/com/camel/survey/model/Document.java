package com.camel.survey.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.core.model.SysUser;
import com.camel.survey.enums.ZsStatus;
import lombok.Data;

import java.io.Serializable;

/**
 *
 *                       .::::.
 *                     .::::::::.
 *                    :::::::::::
 *                 ..:::::::::::'
 *              '::::::::::::'
 *                .::::::::::
 *           '::::::::::::::..
 *                ..::::::::::::.
 *              ``::::::::::::::::
 *               ::::``:::::::::'        .:::.
 *              ::::'   ':::::'       .::::::::.
 *            .::::'      ::::     .:::::::'::::.
 *           .:::'       :::::  .:::::::::' ':::::.
 *          .::'        :::::.:::::::::'      ':::::.
 *         .::'         ::::::::::::::'         ``::::.
 *     ...:::           ::::::::::::'              ``::.
 *    ```` ':.          ':::::::::'                  ::::..
 *                       '.:::::'                    ':'````..
 * < 服务实现类>
 * @author baily
 * @since 1.0
 * @date 2019-12-11
 **/
@Data
public class Document extends ZsSurveyBaseEntity implements Serializable {

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

    public Document() {
    }

    public Document(Integer id, ZsStatus status) {
        this.id = id;
    }

    public Document(Integer id, String dname, String address, Double dsize, String dtype, Date createdAt, Integer creator) {
        this.id = id;
        this.dname = dname;
        this.address = address;
        this.dsize = dsize;
        this.dtype = dtype;
        this.setCreatedAt(createdAt);
        this.setCreator(new SysUser(creator));
        this.setCreatorId(creator);
    }

    public Document(Integer id, String dname, String address, Double dsize, String dtype, Date createdAt, Integer creator, ZsStatus status) {
        this.id = id;
        this.dname = dname;
        this.address = address;
        this.dsize = dsize;
        this.dtype = dtype;
        this.setCreatedAt(createdAt);
        this.setCreator(new SysUser(creator));
        this.setCreatorId(creator);
        this.setStatus(status);
    }

    public Document(String dname, String address, Double dsize, String dtype, Integer creator) {
        this.dname = dname;
        this.address = address;
        this.dsize = dsize;
        this.dtype = dtype;
        this.setCreator(new SysUser(creator));
        this.setCreatorId(creator);
    }

    @Override
    public String toString() {
        return "Document{" +
                ", id=" + id +
                ", dname=" + dname +
                ", address=" + address +
                ", dsize=" + dsize +
                ", dtype=" + dtype +
                "}";
    }
}
