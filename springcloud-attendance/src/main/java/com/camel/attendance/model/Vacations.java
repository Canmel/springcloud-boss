package com.camel.attendance.model;

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
 * <假期表 服务实现类>
 * @author baily
 * @since 1.0
 * @date 2019-11-27
 **/
public class Vacations extends BasePaginationEntity implements Serializable {

private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
                    @TableId(value = "id", type = IdType.AUTO)
                private Integer id;
    /**
     * 年度
     */
        private Integer year;
    /**
     * 节假日名称
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

    public Integer getYear() {
            return year;
            }

        public void setYear(Integer year) {
            this.year = year;
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
        return "Vacations{" +
                ", id=" + id +
                ", year=" + year +
                ", name=" + name +
                ", status=" + status +
        "}";
        }
        }
