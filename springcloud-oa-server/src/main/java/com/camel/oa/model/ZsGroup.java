package com.camel.oa.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.oa.enums.ZsGroupStatus;
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
 * <项目组 服务实现类>
 *
 * @author baily
 * @date 2019-10-22
 * @since 1.0
 **/
@Data
public class ZsGroup extends BasePaginationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 状态
     */
    @TableLogic
    private ZsGroupStatus status;
    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createdAt;

    @Override
    public String toString() {
        return "ZsGroup{" +
                ", id=" + id +
                ", name=" + name +
                ", status=" + status +
                ", remark=" + remark +
                "}";
    }
}
