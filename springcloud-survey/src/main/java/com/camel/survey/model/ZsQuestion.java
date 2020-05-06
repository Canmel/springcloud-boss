package com.camel.survey.model;

import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.*;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.core.model.SysUser;
import com.camel.survey.annotation.ExcelAnnotation;
import com.camel.survey.enums.ZsStatus;
import com.camel.survey.enums.ZsYesOrNo;
import lombok.Data;

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
 * @date 2019-12-09
 **/
@Data
public class ZsQuestion extends ZsSurveyBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 标题
     */
    @ExcelAnnotation(columnName = "题目", columnIndex = 1)
    private String name;
    /**
     * 序号
     */
    @ExcelAnnotation(columnName = "题号", columnIndex = 0)
    private Integer orderNum;
    /**
     * 问卷ID
     */
    private Integer surveyId;
    /**
     * 类型
     */
    @ExcelAnnotation(columnName = "类型", columnIndex = 2)
    private Integer type;
    /**
     * 最小选择数量
     */
    @ExcelAnnotation(columnName = "最少选择", columnIndex = 4)
    private Integer minSelect;
    /**
     * 最大选择数量
     */
    @ExcelAnnotation(columnName = "最多选择", columnIndex = 3)
    private Integer maxSelect;

    //必答性
    private ZsYesOrNo compulsory;

    /**
     * 选项
     */
    @TableField(exist = false)
    public List<ZsOption> options;

    public ZsQuestion() {
    }

    public static Map<String, List> loadTranslate() {
        List<String> keys = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        keys.add("单选题");values.add(1);
        keys.add("多选题");values.add(2);
        keys.add("问答题");values.add(3);
        Map<String, List> map = new HashMap<>();
        map.put("keys", keys);
        map.put("values", values);
        return map;
    }

    @Override
    public String toString() {
        return "ZsQuestion{" +
                ", id=" + id +
                ", name=" + name +
                ", orderNum=" + orderNum +
                ", surveyId=" + surveyId +
                ", type=" + type +
                "}";
    }
}
