package com.camel.survey.model;

import com.camel.core.entity.BasePaginationEntity;

/**
 * <p>
 * 
 * </p>
 *
 * @author baily
 * @since 2020-12-02
 */
public class AreaAddress extends BasePaginationEntity {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 拼音
     */
    private String pinyin;
    private Integer areaId;


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

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    @Override
    public String toString() {
        return "AreaAddress{" +
        ", id=" + id +
        ", name=" + name +
        ", pinyin=" + pinyin +
        ", areaId=" + areaId +
        "}";
    }
}
