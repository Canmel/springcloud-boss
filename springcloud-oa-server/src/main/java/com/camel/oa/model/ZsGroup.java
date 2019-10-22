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
 * <项目组 服务实现类>
 * @author baily
 * @since 1.0
 * @date 2019-10-22
 **/
public class ZsGroup extends BasePaginationEntity implements Serializable {

private static final long serialVersionUID = 1L;

                    @TableId(value = "id", type = IdType.AUTO)
                private Integer id;
    /**
     * 名称
     */
        private String name;
    /**
     * 状态
     */
        private String status;
    /**
     * 备注
     */
        private String remark;


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

    public String getStatus() {
            return status;
            }

        public void setStatus(String status) {
            this.status = status;
            }

    public String getRemark() {
            return remark;
            }

        public void setRemark(String remark) {
            this.remark = remark;
            }

@Override
public String toString() {
        return "ZsGroup{" +
                ", id=" + id +
                ", name=" + name +
                ", status=" + status +
                ", remark=" + remark +
        "}";
        }
        }
