package com.camel.auth.model;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author baily
 * @since 2021-04-27
 */
@Data
public class SysCompany implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String contacts;
    private String tel;

    @Override
    public String toString() {
        return "SysCompany{" +
        ", id=" + id +
        ", name=" + name +
        ", contacts=" + contacts +
        ", tel=" + tel +
        "}";
    }
}
