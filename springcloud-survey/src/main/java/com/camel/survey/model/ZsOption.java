package com.camel.survey.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.camel.survey.annotation.ExcelAnnotation;
import com.camel.survey.enums.ZsYesOrNo;
import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @ExcelAnnotation(columnName = "选项", columnIndex = 6)
    private String name;

    /**
     * 名称，显示作用,让前段多个字段，避免双向绑定
     */
    @TableField(exist = false)
    private String text;

    /**
     * 有没有备注
     */
    @ExcelAnnotation(columnName = "是否有备注", columnIndex = 9)
    public Boolean hasRemark;

    /**
     * 序号
     */
    @ExcelAnnotation(columnName = "项号", columnIndex = 5)
    private Integer orderNum;

    /**
     * 下一个问题序号
     */
    @ExcelAnnotation(columnName = "跳转至", columnIndex = 7)
    private Integer target;

    /**
     * 选项配额
     */
    @ExcelAnnotation(columnName = "选项配额", columnIndex = 10)
    private Integer configration;

    /**
     * 不计配额
     */
    @ExcelAnnotation(columnName = "忽略配额", columnIndex = 8)
    private Boolean ignoreNum;

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

    //排他性
    private ZsYesOrNo exclusivity;

    private Double score;

    /**
     * 选项类型
     * 默认为字符串+人工输入
     */
    private Integer otype;

    /**
     * 类型对应的数据ID
     */
    private Integer dataId;

    @ExcelAnnotation(columnName = "题目", columnIndex = 1)
    @TableField(exist = false)
    private String question;

    @TableField(exist = false)
    private ZsQuestion zsQuestion;

    /**
     * 是否只读
     */
    @TableField(exist = false)
    private Boolean isReadonly;

    @TableField(exist = false)
    private int count;

    public Boolean isFull() {
        if (!ObjectUtils.isEmpty(this.current) && !ObjectUtils.isEmpty(this.configration)) {
            return this.current >= this.configration;
        }
        return false;
    }

    public static Map<String, List> loadTranslate() {
        List<String> keys = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        keys.add("是");values.add(0);
        keys.add("否");values.add(1);
        Map<String, List> map = new HashMap<>();
        map.put("keys", keys);
        map.put("values", values);
        return map;
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
