package com.camel.attendance.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.camel.core.entity.BasePaginationEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

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
@Data
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

    /**
     * 日期
     */
    private Date vacationDay;


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
