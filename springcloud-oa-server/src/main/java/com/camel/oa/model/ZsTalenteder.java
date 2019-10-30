package com.camel.oa.model;

import com.baomidou.mybatisplus.enums.IdType;
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
 * @date 2019-10-30
 **/
public class ZsTalenteder extends BasePaginationEntity implements Serializable {

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
        private String cantactPhone;
    /**
     * 备注
     */
        private String remark;
    /**
     * 适合项目
     */
        private Integer projectId;
    /**
     * 推荐人
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

    public String getName() {
            return name;
            }

        public void setName(String name) {
            this.name = name;
            }

    public String getCantactPhone() {
            return cantactPhone;
            }

        public void setCantactPhone(String cantactPhone) {
            this.cantactPhone = cantactPhone;
            }

    public String getRemark() {
            return remark;
            }

        public void setRemark(String remark) {
            this.remark = remark;
            }

    public Integer getProjectId() {
            return projectId;
            }

        public void setProjectId(Integer projectId) {
            this.projectId = projectId;
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
        return "ZsTalenteder{" +
                ", id=" + id +
                ", name=" + name +
                ", cantactPhone=" + cantactPhone +
                ", remark=" + remark +
                ", projectId=" + projectId +
                ", creator=" + creator +
                ", status=" + status +
        "}";
        }
        }
