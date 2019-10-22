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
 * @date 2019-10-22
 **/
public class ZsIndustry extends BasePaginationEntity implements Serializable {

private static final long serialVersionUID = 1L;

    /**
     * ID
     */
                    @TableId(value = "id", type = IdType.AUTO)
                private Integer id;
    /**
     * 名称
     */
        private String name;
    /**
     * 编号
     */
        private String code;
        private Integer status;
        private Integer creator;
        private Date createdAt;


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

    public String getCode() {
            return code;
            }

        public void setCode(String code) {
            this.code = code;
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
        return "ZsIndustry{" +
                ", id=" + id +
                ", name=" + name +
                ", code=" + code +
                ", status=" + status +
                ", creator=" + creator +
                ", createdAt=" + createdAt +
        "}";
        }
        }
