package com.camel.oa.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.core.model.SysUser;
import com.camel.oa.enums.ZsCommentStatus;
import com.camel.oa.enums.ZsCommentTyies;
import com.camel.oa.enums.ZsProjectStatus;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

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
 * <评论>
 * @author baily
 * @since 1.0
 * @date 2019/10/31
 **/
@Data
public class ZsComment extends BaseOaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 评论
     */
    private String comment;
    /**
     * 类型 0： 评论， 1： 回复
     */
    private ZsCommentTyies type;
    /**
     * 状态
     */
    @TableLogic
    private ZsCommentStatus status;
    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 创建者
     */
    @TableField(value = "creator")
    private Integer creatorId;

    @TableField(exist = false)
    private SysUser creator;

    @TableField(value = "project")
    private Integer projectId;

    @TableField(exist = false)
    private ZsProject project;

    @Override
    public String toString() {
        return "ZsComment{" +
                ", int=" + id +
                ", comment=" + comment +
                ", type=" + type +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", creator=" + creator +
                "}";
    }
}
