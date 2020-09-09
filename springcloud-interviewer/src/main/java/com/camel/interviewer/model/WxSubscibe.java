package com.camel.interviewer.model;

import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.interviewer.enums.ZsStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * <p>
 * 关注用户
 * </p>
 *
 * @author baily
 * @since 2020-03-04
 */
@Data
public class WxSubscibe extends BasePaginationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 发送方帐号（一个OpenID）
     */
    private String user;

    /**
     * 分享者
     */
    private String shareUser;

    /**
     * 状态
     */
    @TableLogic
    private ZsStatus status;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    public WxSubscibe(String user, String shareUser) {
        this.user = user;
        this.shareUser = shareUser;
    }

    public WxSubscibe() {
    }

    @Override
    public String toString() {
        return "WxSubscibe{" +
        ", id=" + id +
        ", user=" + user +
        ", createdAt=" + createdAt +
        "}";
    }
}
