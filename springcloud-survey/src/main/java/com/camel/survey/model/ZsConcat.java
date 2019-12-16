package com.camel.survey.model;

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
 * @date 2019-12-16
 **/
public class ZsConcat extends BasePaginationEntity implements Serializable {

private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
                    @TableId(value = "id", type = IdType.AUTO)
                private Integer id;
    /**
     * 问卷ID
     */
        private Integer surveyId;
    /**
     * 联系人
     */
        private String name;
    /**
     * 手机号
     */
        private String phone;
    /**
     * 状态
     */
        private Integer status;
    /**
     * 创建人
     */
        private Integer creator;
    /**
     * 创建时间
     */
        private Date createdAt;


    public Integer getId() {
            return id;
            }

        public void setId(Integer id) {
            this.id = id;
            }

    public Integer getSurveyId() {
            return surveyId;
            }

        public void setSurveyId(Integer surveyId) {
            this.surveyId = surveyId;
            }

    public String getName() {
            return name;
            }

        public void setName(String name) {
            this.name = name;
            }

    public String getPhone() {
            return phone;
            }

        public void setPhone(String phone) {
            this.phone = phone;
            }

    public Integer getStatus() {
            return status;
            }

        public void setStatus(Integer status) {
            this.status = status;
            }

    public Integer getCreator() {
            return creator;
            }

        public void setCreator(Integer creator) {
            this.creator = creator;
            }

    public Date getCreatedAt() {
            return createdAt;
            }

        public void setCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
            }

@Override
public String toString() {
        return "ZsConcat{" +
                ", id=" + id +
                ", surveyId=" + surveyId +
                ", name=" + name +
                ", phone=" + phone +
                ", status=" + status +
                ", creator=" + creator +
                ", createdAt=" + createdAt +
        "}";
        }
        }
