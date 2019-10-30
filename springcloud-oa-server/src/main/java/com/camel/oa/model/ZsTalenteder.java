package com.camel.oa.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
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
 * @date 2019-10-30
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
     * 状态
     */
    private Integer status;

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
