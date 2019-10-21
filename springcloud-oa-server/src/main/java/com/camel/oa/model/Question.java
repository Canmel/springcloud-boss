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
 * @date 2019-10-21
 **/
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
        private Integer type;
    /**
     * 创建时间
     */
        private Date createdAt;
    /**
     * 创建人
     */
        private Integer creator;
    /**
     * 状态
     */
        private Integer status;


    public Integer getId() {
            return id;
            }

        public void setId(Integer id) {
            this.id = id;
            }

    public String getTitle() {
            return title;
            }

        public void setTitle(String title) {
            this.title = title;
            }

    public Integer getType() {
            return type;
            }

        public void setType(Integer type) {
            this.type = type;
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

    public Integer getStatus() {
            return status;
            }

        public void setStatus(Integer status) {
            this.status = status;
            }

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
