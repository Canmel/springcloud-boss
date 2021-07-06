package com.camel.survey.model;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.core.model.SysUser;
import com.camel.survey.enums.ZsStatus;
import com.camel.survey.enums.ZsYesOrNo;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author baily
 * @since 2020-02-19
 */
@Data
public class ZsSeat extends BasePaginationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 坐席号
     */
    @TableField("seat_num")
    private String seatNum;

    private Integer uid;

    /**
     * 坐席密码
     */
    private String password;

    /**
     * 坐席描述
     */
    private String description;

    @TableField(exist = false)
    private SysUser user;

    @TableField(exist = false)
    private String userName;

    private ZsYesOrNo state;

    private String workNum;

    private Integer surveyId;

    private Date lastSubmit;

    private Integer queueId;

    @TableField(exist = false)
    private ZsQueue queue;

    @TableField(exist = false)
    private String queueName;

    /**
     * 状态
     */
    @TableLogic
    private ZsStatus status;

    @Override
    public String toString() {
        return "ZsSeat{" +
        ", id=" + id +
        ", seatNum=" + seatNum +
        ", password=" + password +
        "}";
    }

    public static List<ZsSeat> parse(JSONArray jsonArray) {
        List<ZsSeat> seats = new ArrayList<>();
        for (Object object: jsonArray) {
            JSONObject jsonObject = (JSONObject) object;
            seats.add(new ZsSeat(jsonObject.getStr("agentnum")));
        }
        return seats;
    }

    public ZsSeat(String seatNum) {
        this.seatNum = seatNum;
    }

    public ZsSeat() {
    }
}
