package com.selfboot.chandao.domain;

import com.selfboot.chandao.persist.BaseEntity;

import java.io.Serializable;

public class CdURole extends BaseEntity implements Serializable{

    /** 角色名称 */
    private String name;

    /** 角色类型 */
    private Integer type;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}