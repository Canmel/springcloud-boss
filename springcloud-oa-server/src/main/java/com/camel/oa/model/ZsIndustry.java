package com.camel.oa.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.core.model.SysUser;
import com.camel.oa.enums.ZsIndustryStatus;
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
public class ZsIndustry extends BasePaginationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 编号
     */
    private String code;

    /**
     * 状态
     */
    @TableLogic
    private ZsIndustryStatus status;

    /**
     * 创建者
     */
    @TableField(value = "creator")
    private Integer creatorId;

    @TableField(exist = false)
    private SysUser creator;

    /**
     * 创建时间
     */
    private Date createdAt;

    @Override
    public String toString() {
        return "ZsIndustry{" +
                ", id=" + id +
                ", name=" + name +
                ", code=" + code +
                ", status=" + status +
                ", creator=" + creator +
                ", createdAt=" + createdAt +
                "}";
    }
}
