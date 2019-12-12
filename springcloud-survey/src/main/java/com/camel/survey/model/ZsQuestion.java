package com.camel.survey.model;

import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
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
 * @date 2019-12-09
 **/
@Data
public class ZsQuestion extends BasePaginationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 标题
     */
    private String name;
    /**
     * 序号
     */
    private Integer orderNum;
    /**
     * 问卷ID
     */
    private Integer surveyId;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 状态
     */
    @TableLogic
    private ZsStatus status;
    /**
     * 创建时间
     */
    @TableField("createdAt")
    private Date createdAt;

    @TableField(value = "creator")
    private Integer creatorId;

    @TableField(exist = false)
    private SysUser creator;

    public ZsQuestion() {
    }

    @Override
    public String toString() {
        return "ZsQuestion{" +
                ", id=" + id +
                ", name=" + name +
                ", orderNum=" + orderNum +
                ", surveyId=" + surveyId +
                ", type=" + type +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", creator=" + creator +
                "}";
    }
}
