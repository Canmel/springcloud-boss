package com.camel.oa.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.camel.oa.enums.ZsGender;
import com.camel.oa.enums.ZsTalentederStatus;
import lombok.Data;

import java.io.Serializable;

/**
 *
 *                 ___====-_  _-====___
 *           _--^^^#####//      \\#####^^^--_
 *        _-^##########// (    ) \\##########^-_
 *       -############//  |\^^/|  \\############-
 *     _/############//   (@::@)   \\############\_
 *    /#############((     \\//     ))#############\
 *   -###############\\    (oo)    //###############-
 *  -#################\\  / VV \  //#################-
 * -###################\\/      \//###################-
 *_#/|##########/\######(   /\   )######/\##########|\#_
 *|/ |#/\#/\#/\/  \#/\##\  |  |  /##/\#/  \/\#/\#/\#| \|
 *`  |/  V  V  `   V  \#\| |  | |/#/  V   '  V  V  \|  '
 *   `   `  `      `   / | |  | | \   '      '  '   '
 *                    (  | |  | |  )
 *                   __\ | |  | | /__
 *                  (vvv(VVV)(VVV)vvv)
 * <人才服务实现类>>
 * @author baily
 * @since 1.0
 * @date 2019/11/5
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
    private String contactPhone;
    /**
     * 备注
     */
    private String remark;
    /**
     * 适合项目
     */
    @TableField(exist = false)
    private ZsProject project;

    @TableField(value = "project")
    private Integer projectId;
    /**
     * 状态
     */
    private ZsTalentederStatus status;

    /**
     * 男女
     */
    private ZsGender gender;

    @Override
    public String toString() {
        return "ZsTalenteder{" +
                ", id=" + id +
                ", name=" + name +
                ", remark=" + remark +
                ", status=" + status +
                "}";
    }
}
