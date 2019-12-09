package com.camel.survey.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.core.model.SysUser;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

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
 * <问题的选项或答案>
 * @author baily
 * @since 1.0
 * @date 2019-12-09
 **/
@Data
public class ZsOption extends BasePaginationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 名称，显示作用
     */
    private String name;
    /**
     * 序号
     */
    private Integer orderNum;

    /**
     * 问题序号
     */
    @TableField(exist = false)
    private Integer QuestionOrderNum;

    /**
     * 问题
     */
    private Integer questionId;
    /**
     * 状态
     */
    @TableLogic
    private Integer status;
    /**
     * 创建时间
     */
    @TableField("createdAt")
    private Date createdAt;

    @TableField(value = "creator")
    private Integer creatorId;

    @TableField(exist = false)
    private SysUser creator;

    @Override
    public String toString() {
        return "ZsOption{" +
                ", id=" + id +
                ", name=" + name +
                ", orderNum=" + orderNum +
                ", questionId=" + questionId +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", creator=" + creator +
                "}";
    }
}
