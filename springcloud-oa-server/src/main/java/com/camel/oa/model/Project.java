package com.camel.oa.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.core.model.SysUser;
import com.camel.oa.enums.ProjectStatus;
import com.camel.oa.enums.ProjectTyies;
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
 * <项目 服务实现类>
 * @author baily
 * @since 1.0
 * @date 2019-10-21
 **/
@Data
public class Project extends BasePaginationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 名称
     */
    private String pname;
    /**
     * 编号
     */
    private String code;
    /**
     * 当前份数
     */
    private Integer currentCopies;
    /**
     * 总份数
     */
    private Integer copies;
    /**
     * 需要收集的有效份数
     */
    private Integer collectCopies;
    /**
     * 状态
     */
    @TableLogic
    @TableField(value = "status")
    private ProjectStatus status;
    /**
     * 调查类型
     */
    @TableField(value = "type")
    private ProjectTyies type;
    /**
     * 时间戳
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
     * 发布时间
     */
    private Date releaseAt;

    public Project(Integer id, ProjectStatus status) {
        this.id = id;
        this.status = status;
    }

    public Project() {
    }

    public Project(Integer id, String pname, String code, Integer currentCopies, Integer copies, Integer collectCopies, ProjectStatus status, ProjectTyies type, Date createdAt, Integer creatorId, SysUser creator, Date releaseAt) {
        this.id = id;
        this.pname = pname;
        this.code = code;
        this.currentCopies = currentCopies;
        this.copies = copies;
        this.collectCopies = collectCopies;
        this.status = status;
        this.type = type;
        this.createdAt = createdAt;
        this.creatorId = creatorId;
        this.creator = creator;
        this.releaseAt = releaseAt;
    }

    @Override
    public String toString() {
        return "Project{" +
                ", id=" + id +
                ", pname=" + pname +
                ", code=" + code +
                ", currentCopies=" + currentCopies +
                ", copies=" + copies +
                ", collectCopies=" + collectCopies +
                ", status=" + status +
                ", type=" + type +
                ", createdAt=" + createdAt +
                ", creator=" + creator +
                ", releaseAt=" + releaseAt +
                "}";
    }
}
