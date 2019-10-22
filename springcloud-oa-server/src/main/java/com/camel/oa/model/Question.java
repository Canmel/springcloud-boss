package com.camel.oa.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.core.model.SysUser;
import com.camel.oa.enums.QuestionStatus;
import com.camel.oa.enums.QuestionTyies;
import com.camel.oa.enums.QuestionnaireStatus;
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
 * @date 2019-10-21
 * @since 1.0
 **/
@Data
public class Question extends BasePaginationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 问题标题
     */
    private String title;
    /**
     * 类型
     */
    private QuestionTyies type;
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

    /**
     * 状态
     */
    @TableLogic
    private QuestionStatus status;

    @Override
    public String toString() {
        return "Question{" +
                ", id=" + id +
                ", title=" + title +
                ", type=" + type +
                ", createdAt=" + createdAt +
                ", creator=" + creator +
                ", status=" + status +
                "}";
    }
}
