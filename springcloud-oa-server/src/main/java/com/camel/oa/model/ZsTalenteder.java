package com.camel.oa.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.camel.core.entity.BasePaginationEntity;
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
 *
 * @author baily
 * @date 2019-10-30
 * @since 1.0
 **/
@Data
public class ZsTalenteder extends BaseOaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 人才推荐主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 联系电话
     */
    private String cantactPhone;
    /**
     * 备注
     */
    private String remark;
    /**
     * 适合项目
     */
    private Integer projectId;
    /**
     * 创建者
     */
    @TableField(value = "creator")
    private Integer creatorId;

    @TableField(exist = false)
    private SysUser creator;
    /**
     * 状态
     */
    private Integer status;

    @Override
    public String toString() {
    private ZsTalentederStatus status;

    @Override
    public String toString() {
        return "ZsTalenteder{" +
                ", id=" + id +
                ", name=" + name +
                ", cantactPhone=" + cantactPhone +
                ", remark=" + remark +
                ", projectId=" + projectId +
                ", status=" + status +
                "}";
    }
}
