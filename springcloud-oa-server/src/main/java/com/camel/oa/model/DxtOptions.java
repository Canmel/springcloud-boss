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
 * @date 2019-10-22
 **/
public class DxtOptions extends BasePaginationEntity implements Serializable {

private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
                    @TableId(value = "id", type = IdType.AUTO)
                private Integer id;
    /**
     * 选项
     */
        private String option;
    /**
     * 问题ID
     */
        private Integer questionId;


    public Integer getId() {
            return id;
            }

        public void setId(Integer id) {
            this.id = id;
            }

    public String getOption() {
            return option;
            }

        public void setOption(String option) {
            this.option = option;
            }

    public Integer getQuestionId() {
            return questionId;
            }

        public void setQuestionId(Integer questionId) {
            this.questionId = questionId;
            }

@Override
public String toString() {
        return "DxtOptions{" +
                ", id=" + id +
                ", option=" + option +
                ", questionId=" + questionId +
        "}";
        }
        }
