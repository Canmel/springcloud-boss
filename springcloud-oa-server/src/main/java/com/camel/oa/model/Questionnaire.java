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
 * <调查问卷 服务实现类>
 * @author baily
 * @since 1.0
 * @date 2019-10-21
 **/
public class Questionnaire extends BasePaginationEntity implements Serializable {

private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
                    @TableId(value = "id", type = IdType.AUTO)
                private Integer id;
    /**
     * 名称
     */
        private String name;
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

    public Integer getStatus() {
            return status;
            }

        public void setStatus(Integer status) {
            this.status = status;
            }

@Override
public String toString() {
        return "Questionnaire{" +
                ", id=" + id +
                ", name=" + name +
                ", status=" + status +
        "}";
        }
        }
