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
public class ZsClue extends BasePaginationEntity implements Serializable {

private static final long serialVersionUID = 1L;

                    @TableId(value = "id", type = IdType.AUTO)
                private Integer id;
    /**
     * 线索名称
     */
        private String name;
    /**
     * 描述
     */
        private String business;
    /**
     * 创建人
     */
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

    public String getBusiness() {
            return business;
            }

        public void setBusiness(String business) {
            this.business = business;
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
        return "ZsClue{" +
                ", id=" + id +
                ", name=" + name +
                ", business=" + business +
                ", creator=" + creator +
                ", createdAt=" + createdAt +
        "}";
        }
        }
