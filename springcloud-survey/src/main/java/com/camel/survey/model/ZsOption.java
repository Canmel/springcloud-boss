package com.camel.survey.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import org.springframework.util.ObjectUtils;

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
 * <问题的选项或答案>
 * @author baily
 * @since 1.0
 * @date 2019-12-09
 **/
@Data
public class ZsOption extends ZsSurveyBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    /**
     * 名称，显示作用
     */
    private String name;

    /**
     * 有没有备注
     */
    public Boolean hasRemark;

    /**
     * 序号
     */
    private Integer orderNum;

    /**
     * 下一个问题序号
     */
    private Integer target;

    /**
     * 选项配额
     */
    private Integer configration;

    /**
     * 配额当前值
     */
    private Integer current;

    /**
     * 问题序号
     */
    @TableField(exist = false)
    private Integer questionOrderNum;

    /**
     * 问题
     */
    private Integer questionId;

    @TableField(exist = false)
    private ZsQuestion zsQuestion;

    /**
     * 是否只读
     */
    @TableField(exist = false)
    private Boolean isReadonly;

    public Boolean isFull() {
        if (!ObjectUtils.isEmpty(this.current) && !ObjectUtils.isEmpty(this.configration)) {
            return this.current >= this.configration;
        }
        return false;
    }

    @Override
    public String toString() {
        return "ZsOption{" +
                ", id=" + id +
                ", name=" + name +
                ", orderNum=" + orderNum +
                ", questionId=" + questionId +
                "}";
    }
}
