package com.camel.oa.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
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
 * @author baily
 * @since 1.0
 * @date 2019-10-23
 **/
public class ZsComment extends BasePaginationEntity implements Serializable {

private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
                    @TableId(value = "int", type = IdType.AUTO)
                private Integer int;
        private String comment;
    /**
     * 类型 0： 评论， 1： 回复
     */
        private Integer type;
    /**
     * 状态
     */
        private Integer status;
    /**
     * 创建时间
     */
        private Date createdAt;
    /**
     * 评论人
     */
        private Integer creator;


    public Integer getInt() {
            return int;
            }

        public void setInt(Integer int) {
            this.int = int;
            }

    public String getComment() {
            return comment;
            }

        public void setComment(String comment) {
            this.comment = comment;
            }

    public Integer getType() {
            return type;
            }

        public void setType(Integer type) {
            this.type = type;
            }

    public Integer getStatus() {
            return status;
            }

        public void setStatus(Integer status) {
            this.status = status;
            }

    public Date getCreatedAt() {
            return createdAt;
            }

        public void setCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
            }

    public Integer getCreator() {
            return creator;
            }

        public void setCreator(Integer creator) {
            this.creator = creator;
            }

@Override
public String toString() {
        return "ZsComment{" +
                ", int=" + int +
                ", comment=" + comment +
                ", type=" + type +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", creator=" + creator +
        "}";
        }
        }
