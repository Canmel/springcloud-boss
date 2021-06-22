package com.camel.core.enums;

/**
 * @author 259
 * @version 1.0
 * @date 2021/6/22
 */
public enum GradeStatus implements BaseEnum {
    GRADE_01(1, "高级"),
    GRADE_02(2, "中级"),
    GRADE_03(3, "初级"),
    GRADE_04(4, "见习");

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态名称
     */
    private String name;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    GradeStatus(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public Integer getValue() {
        return  getCode();
    }

    /**
     * 根据code获取name
     * @param code 编号
     * @return name
     */
    public static String getValue(Integer code){
        GradeStatus[] values = GradeStatus.values();
        for (GradeStatus gs : values) {
          if(gs.getCode().equals(code)){
              return gs.getName();
          }
        }
        return null;
    }

    @Override
    public String toString() {
        return "GradeStatus{" +
                "code=" + code +
                ", name='" + name + '\'' +
                '}';
    }
}
