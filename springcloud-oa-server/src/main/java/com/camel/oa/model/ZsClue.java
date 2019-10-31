package com.camel.oa.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.core.model.SysUser;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * .::::.
 * .::::::::.
 * :::::::::::
 * ..:::::::::::'
 * '::::::::::::'
 * .::::::::::
 * '::::::::::::::..
 * ..::::::::::::.
 * ``::::::::::::::::
 * ::::``:::::::::'        .:::.
 * ::::'   ':::::'       .::::::::.
 * .::::'      ::::     .:::::::'::::.
 * .:::'       :::::  .:::::::::' ':::::.
 * .::'        :::::.:::::::::'      ':::::.
 * .::'         ::::::::::::::'         ``::::.
 * ...:::           ::::::::::::'              ``::.
 * ```` ':.          ':::::::::'                  ::::..
 * '.:::::'                    ':'````..
 * < 服务实现类>
 *
 * @author baily
 * @date 2019-10-22
 * @since 1.0
 **/
@Data
public class ZsClue extends BaseOaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 线索名称
     */
    private String name;
    /**
     * 描述
     */
    private String business;
    /**
     * 创建者
     */
    @TableField(value = "creator")
    private Integer creatorId;

    @TableField(exist = false)
    private SysUser creator;

    @TableField(exist = false)
    private ZsProject project;

    @TableField(value = "project_id")
    private Integer projectId;

    private Date createdAt;

    @Override
    public String toString() {
        return "ZsClue{" +
                ", id=" + id +
                ", name=" + name +
                ", business=" + business +
                ", creator=" + creator +
                ", createdAt=" + createdAt +
                "}";
    }
}
